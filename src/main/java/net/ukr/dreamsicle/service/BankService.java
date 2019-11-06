package net.ukr.dreamsicle.service;

import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.bank.BankMapper;
import net.ukr.dreamsicle.exception.CustomDataAlreadyExistsException;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class BankService {
    private final BankRepository bankRepository;
    private final AtmRepository atmRepository;
    private final OfficeRepository officeRepository;
    private final PartnerRepository partnerRepository;
    private final ProductRepository productRepository;
    private final BankMapper bankMapper;

    private final OfficeService officeService;
    private final AtmService atmService;
    private final ProductService productService;
    private final PartnerService partnerService;

    public Page<BankDTO> getAll(Pageable pageable) {
        return bankMapper.toBankDTOs(bankRepository.findAll(pageable));
    }

    public BankDTO findById(String id) {
        return bankRepository
                .findById(id)
                .map(bankMapper::toBankDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public BankDTO create(BankDTO bankDTO) {

        if (Boolean.TRUE.equals(bankRepository.existsBankByBankName(bankDTO.getBankName()))) {
            throw new CustomDataAlreadyExistsException("Bank is already in use");
        }

        Bank bank = bankMapper.toBank(bankDTO);
        String bankCode = String.valueOf(new Random().nextInt(1000000));

        return bankMapper.toBankDto(bankRepository.save(Bank.builder()
                .bankName(bank.getBankName())
                .bankCode(bankCode)
                .iban(UUID.randomUUID().toString().substring(0, 29))
                .state(bank.getState())
                .city(bank.getCity())
                .street(bank.getStreet())
                .partners(partnerService.createPartners(bank.getPartners()))
                .products(productService.createProduct(bankCode, bank.getProducts()))
                .offices(officeService.createOffice(bankCode, bank.getOffices()))
                .atms(atmService.createAtm(bankCode, bank.getAtms()))
                .build()));
    }

    //TODO
    // Will implement later
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public BankDTO update(String bankName, BankDTO bankDTO) {
        Bank bank = bankRepository.findBankByBankName(bankName).orElseThrow(ResourceNotFoundException::new);

        Bank actualBank = bankMapper.toBank(bankDTO);

        bank.setBankName(actualBank.getBankName());

        return bankMapper.toBankDto(bankRepository.save(bank));
    }

    //TODO
    // Will implement later
    public Page<BankDTO> search(Pageable page) {
        return null;
    }

    //TODO
    // Just for test app
    public void delete() {
        bankRepository.deleteAll();
        atmRepository.deleteAll();
        partnerRepository.deleteAll();
        productRepository.deleteAll();
        officeRepository.deleteAll();
    }
}
