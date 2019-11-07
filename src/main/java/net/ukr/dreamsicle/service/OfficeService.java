package net.ukr.dreamsicle.service;

import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.dto.office.OfficeDTO;
import net.ukr.dreamsicle.dto.office.OfficeMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.model.office.Office;
import net.ukr.dreamsicle.repository.BankRepository;
import net.ukr.dreamsicle.repository.OfficeRepository;
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
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final OfficeMapper officeMapper;
    private final BankRepository bankRepository;
    private final BankUpdateDataService bankUpdateDataService;

    @Lock(LockModeType.PESSIMISTIC_READ)
    public Page<OfficeDTO> getAll(Pageable pageable) {
        return officeMapper.toOfficeDTOs(officeRepository.findAll(pageable));
    }

    @Lock(LockModeType.PESSIMISTIC_READ)
    public OfficeDTO findById(String id) {
        return officeRepository
                .findById(id)
                .map(officeMapper::toOfficeDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public OfficeDTO create(String bankCode, OfficeDTO officeDTO) {
        Bank actualBank = bankRepository.findBankByBankCode(bankCode).orElseThrow(ResourceNotFoundException::new);

        Office office = saveOffice(actualBank.getBankCode(), officeMapper.toOffice(officeDTO));

        bankUpdateDataService.addDataToBank(bankCode, office);

        return officeMapper.toOfficeDto(office);
    }

    private Office saveOffice(String bankCode, Office office) {
        return officeRepository.save(Office.builder()
                .bankCode(bankCode)
                .name(office.getName())
                .city(office.getCity())
                .state(office.getState())
                .street(office.getStreet())
                .workTimes(office.getWorkTimes())
                .build());
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public OfficeDTO update(String id, OfficeDTO officeDTO) {
        Office officeById = officeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        Office actualOffice = officeMapper.toOffice(officeDTO);

        officeById.setName(actualOffice.getName());
        officeById.setCity(actualOffice.getCity());
        officeById.setState(actualOffice.getState());
        officeById.setStreet(actualOffice.getStreet());
        officeById.setWorkTimes(actualOffice.getWorkTimes());

        bankUpdateDataService.updateDateToBank(id, officeById.getBankCode(), actualOffice);

        return officeMapper.toOfficeDto(officeRepository.save(officeById));
    }

    public List<Office> createOffice(String bankCode, List<Office> listOffice) {
        return listOffice.stream()
                .map(office -> saveOffice(bankCode, office))
                .collect(Collectors.toList());
    }
}
