package net.ukr.dreamsicle.service;

import com.google.common.base.Strings;
import com.querydsl.core.BooleanBuilder;
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
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.Optional;

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

    public BankDTO findById(ObjectId id) {
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

        if (Boolean.TRUE.equals(bankRepository.existsBankByBankCode(bankDTO.getBankCode()))) {
            throw new CustomDataAlreadyExistsException(Constants.BANK_CODE_IS_ALREADY_IN_USE);
        }

        if (Boolean.TRUE.equals(bankRepository.existsBankByIban(bankDTO.getIban()))) {
            throw new CustomDataAlreadyExistsException(Constants.USER_IBAN_CODE_IS_ALREADY_IN_USE);
        }

        Bank bank = bankMapper.toBank(bankDTO);

        return bankMapper.toBankDto(bankRepository.save(Bank.builder()
                .bankName(bank.getBankName())
                .bankCode(bank.getBankCode())
                .iban(bank.getIban())
                .state(bank.getState())
                .city(bank.getCity())
                .street(bank.getStreet())
                .products(productService.createProduct(bank.getBankCode(), bank.getProducts()))
                .offices(officeService.createOffice(bank.getBankCode(), bank.getOffices()))
                .atms(atmService.createAtm(bank.getBankCode(), bank.getAtms()))
                .build()));
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public BankDTO update(@Min(1) @Positive ObjectId id, BankUpdateDTO bankUpdateDTO) {
        Bank bank = bankRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

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

    public Page<BankDTO> search(String search, Pageable page) {
        if (Optional.ofNullable(Strings.emptyToNull(search)).isPresent()) {
            return bankMapper.toBankDTOs(bankRepository.findAll(getSearchPredicate(search), page));
        } else {
            return getAll(page);
        }
    }

    private BooleanBuilder getSearchPredicate(String search) {
        QBank bank = QBank.bank;

        BooleanExpression id = bank.id.containsIgnoreCase(search);
        BooleanExpression bankName = bank.bankName.containsIgnoreCase(search);
        BooleanExpression bankCode = bank.bankCode.containsIgnoreCase(search);
        BooleanExpression iban = bank.iban.containsIgnoreCase(search);
        BooleanExpression state = bank.state.containsIgnoreCase(search);
        BooleanExpression city = bank.city.containsIgnoreCase(search);
        BooleanExpression street = bank.street.containsIgnoreCase(search);

        return new BooleanBuilder().andAnyOf(id, bankName, bankCode, iban, state, city, street);
    }

    public Optional<Bank> bankDataByBankCode(BankDTO bankDTO) {
        return bankRepository.findBankByBankCode(bankDTO.getBankCode());
    }
}
