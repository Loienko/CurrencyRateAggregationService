package net.ukr.dreamsicle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class CurrencyService {

    private final CurrencyMapper currencyMapper;
    private final CurrencyRepository currencyRepository;

    public List<CurrencyDTO> allCurrencies() {
        return currencyMapper.toCurrencyDTOs(currencyRepository.findAll());
    }

    public CurrencyDTO findCurrencyById(int id) {
        Optional<Currency> currencyById = currencyRepository.findById(id);

        if (!currencyById.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return currencyMapper.toCurrencyDto(currencyMapper.unwrapOptional(currencyById));
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteCurrencyById(int id) {
        Optional<Currency> currencyById = currencyRepository.findById(id);

        if (!currencyById.isPresent()) {
            throw new ResourceNotFoundException();
        }

        currencyRepository.deleteById(id);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public CurrencyDTO createCurrency(CurrencyDTO currencyDTO) {
        Currency currency = currencyMapper.toCurrency(currencyDTO);
        Currency saveAndFlush = currencyRepository.saveAndFlush(currency);

        return currencyMapper.toCurrencyDto(saveAndFlush);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public CurrencyDTO updateCurrency(int id, CurrencyDTO currencyDTO) {
        Optional<Currency> currencyUpdateById = currencyRepository.findById(id);

        if (!currencyUpdateById.isPresent()) {
            throw new ResourceNotFoundException();
        }

        Currency currency = currencyMapper.toCurrency(currencyDTO);
        Currency actualCurrency = currencyMapper.unwrapOptional(currencyUpdateById);

        actualCurrency.setBankName(currency.getBankName());
        actualCurrency.setCurrencyCode(currency.getCurrencyCode());
        actualCurrency.setPurchaseCurrency(currency.getPurchaseCurrency());
        actualCurrency.setSaleOfCurrency(currency.getSaleOfCurrency());

        Currency saveAndFlush = currencyRepository.saveAndFlush(actualCurrency);

        return currencyMapper.toCurrencyDto(saveAndFlush);
    }
}
