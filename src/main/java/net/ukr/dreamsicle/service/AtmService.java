package net.ukr.dreamsicle.service;

import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.dto.atm.AtmMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.repository.AtmRepository;
import net.ukr.dreamsicle.repository.BankRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class AtmService {

    private final AtmRepository atmRepository;
    private final AtmMapper atmMapper;
    private final BankRepository bankRepository;

    public Page<AtmDTO> getAll(Pageable pageable) {
        return atmMapper.toAtmDTOs(atmRepository.findAll(pageable));
    }

    public AtmDTO findById(String id) {
        return atmRepository
                .findById(id)
                .map(atmMapper::toAtmDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public AtmDTO create(String bankCode, AtmDTO atmDTO) {
        Bank bank = bankRepository.findBankByBankCode(bankCode).orElseThrow(ResourceNotFoundException::new);
        ATM atm = atmMapper.toAtm(atmDTO);

        Objects.requireNonNull(bank.getAtms()).add(atm);
        bankRepository.save(bank);

        return atmMapper.toAtmDto(saveAtm(bank.getBankCode(), atm));
    }

    private ATM saveAtm(String bankCode, ATM atm) {
        return atmRepository.save(ATM.builder()
                .bankCode(bankCode)
                .name(atm.getName())
                .city(atm.getCity())
                .state(atm.getState())
                .street(atm.getStreet())
                .workTimes(atm.getWorkTimes())
                .build());
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public AtmDTO update(String id, AtmDTO atmDTO) {
        ATM atmById = atmRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        ATM actualAtm = atmMapper.toAtm(atmDTO);
        actualAtm.setId(id);

        Bank bank = bankRepository.findBankByBankCode(atmById.getBankCode()).orElseThrow(ResourceNotFoundException::new);
        Objects.requireNonNull(bank.getAtms()).stream()
                .filter(atm -> atm.getId().equals(id))
                .forEach(atm -> {
                    atm.setName(actualAtm.getName());
                    atm.setState(actualAtm.getState());
                    atm.setCity(actualAtm.getCity());
                    atm.setStreet(actualAtm.getStreet());
                    atm.setWorkTimes(actualAtm.getWorkTimes());
                });
        bankRepository.save(bank);

        return atmMapper.toAtmDto(atmRepository.save(actualAtm));
    }

    public List<ATM> createAtm(String bankCode, List<ATM> listAtm) {
        return listAtm.stream()
                .map(atm -> saveAtm(bankCode, atm))
                .collect(Collectors.toList());
    }
}
