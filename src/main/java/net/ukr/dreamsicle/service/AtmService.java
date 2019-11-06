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
    public AtmDTO create(String bankName, AtmDTO atmDTO) {
        Bank bank = bankRepository.findBankByBankName(bankName).orElseThrow(ResourceNotFoundException::new);

        return atmMapper.toAtmDto(saveAtm(bank.getBankCode(), atmMapper.toAtm(atmDTO)));
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

    //TODO
    // Will implement later
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public AtmDTO update(String id, AtmDTO atmDTO) {
        ATM atmById = atmRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        ATM actualAtm = atmMapper.toAtm(atmDTO);

        atmById.setName(actualAtm.getName());
        atmById.setCity(actualAtm.getCity());
        atmById.setState(actualAtm.getState());
        atmById.setStreet(actualAtm.getStreet());
        atmById.setWorkTimes(actualAtm.getWorkTimes());

        return atmMapper.toAtmDto(atmRepository.save(atmById));
    }

    public List<ATM> createAtm(String bankCode, List<ATM> listAtm) {
        return listAtm.stream()
                .map(atm -> saveAtm(bankCode, atm))
                .collect(Collectors.toList());
    }
}
