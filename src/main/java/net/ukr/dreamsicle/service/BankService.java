package net.ukr.dreamsicle.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.bank.BankMapper;
import net.ukr.dreamsicle.dto.bank.BankUpdateDTO;
import net.ukr.dreamsicle.exception.CustomDataAlreadyExistsException;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.modelQ.bank.QBank;
import net.ukr.dreamsicle.repository.BankRepository;
import net.ukr.dreamsicle.util.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class BankService {

    private final BankRepository bankRepository;
    private final OfficeService officeService;
    private final AtmService atmService;
    private final ProductService productService;
    private final PartnerService partnerService;
    private final BankMapper bankMapper;

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
            throw new CustomDataAlreadyExistsException(Constants.BANK_IS_ALREADY_IN_USE);
        }

        Bank bank = bankMapper.toBank(bankDTO);
        String bankCode = String.valueOf(100000 + new Random().nextInt(900000));

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

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public BankDTO update(String bankCode, BankUpdateDTO bankUpdateDTO) {
        Bank bank = bankRepository.findBankByBankCode(bankCode).orElseThrow(ResourceNotFoundException::new);

        if (Boolean.TRUE.equals(bankRepository.existsBankByBankName(bankUpdateDTO.getBankName()))
                && !bank.getBankName().equals(bankUpdateDTO.getBankName())) {
            throw new CustomDataAlreadyExistsException(Constants.BANK_IS_ALREADY_IN_USE);
        }

        Bank actualBank = bankMapper.toBank(bankUpdateDTO);

        bank.setBankName(actualBank.getBankName());
        bank.setState(bankUpdateDTO.getState());
        bank.setCity(actualBank.getCity());
        bank.setStreet(bankUpdateDTO.getStreet());

        return bankMapper.toBankDto(bankRepository.save(bank));
    }

    public Page<BankDTO> search(String search, String except, Pageable page) {
        QBank bank = new QBank("bank");
        Predicate predicate;

        if (!Optional.ofNullable(except).isPresent()) {
            if (Optional.ofNullable(search).isPresent()) {
                predicate = getPredicate(search, bank);
            } else {
                return getAll(page);
            }
        } else {
            predicate = getPredicate(search, bank)
                    .and(getSecondPredicate(except, bank));
        }

        return bankMapper.toBankDTOs(bankRepository.findAll(predicate, page));
    }

    private BooleanExpression getPredicate(String search, QBank bank) {
        return bank.bankName.containsIgnoreCase(search)
                .or(bank.bankCode.containsIgnoreCase(search))
                .or(bank.id.containsIgnoreCase(search))
                .or(bank.iban.containsIgnoreCase(search))
                .or(bank.state.containsIgnoreCase(search))
                .or(bank.city.containsIgnoreCase(search))
                .or(bank.street.containsIgnoreCase(search))
                .or(bank.atms.any().id.containsIgnoreCase(search))
                .or(bank.atms.any().name.containsIgnoreCase(search))
                .or(bank.atms.any().state.containsIgnoreCase(search))
                .or(bank.atms.any().city.containsIgnoreCase(search))
                .or(bank.atms.any().street.containsIgnoreCase(search))
                .or(bank.atms.any().workTimes.any().startWork.containsIgnoreCase(search))
                .or(bank.atms.any().workTimes.any().endWork.containsIgnoreCase(search))
                .or(bank.offices.any().id.containsIgnoreCase(search))
                .or(bank.offices.any().name.containsIgnoreCase(search))
                .or(bank.offices.any().state.containsIgnoreCase(search))
                .or(bank.offices.any().city.containsIgnoreCase(search))
                .or(bank.offices.any().street.containsIgnoreCase(search))
                .or(bank.offices.any().workTimes.any().startWork.containsIgnoreCase(search))
                .or(bank.offices.any().workTimes.any().endWork.containsIgnoreCase(search))
                .or(bank.products.any().description.containsIgnoreCase(search));
    }

    private BooleanExpression getSecondPredicate(String except, QBank bank) {
        return bank.bankName.containsIgnoreCase(except)
                .or(bank.bankCode.containsIgnoreCase(except))
                .or(bank.id.containsIgnoreCase(except))
                .or(bank.iban.containsIgnoreCase(except))
                .or(bank.state.containsIgnoreCase(except))
                .or(bank.city.containsIgnoreCase(except))
                .or(bank.street.containsIgnoreCase(except));
    }
}
