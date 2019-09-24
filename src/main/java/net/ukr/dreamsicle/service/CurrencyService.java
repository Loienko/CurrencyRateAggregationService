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
        return currencyRepository.findById(id)
                .map(currencyMapper::toCurrencyDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteCurrencyById(int id) {
        currencyRepository.deleteById(currencyRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new)
                .getId());
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public CurrencyDTO createCurrency(CurrencyDTO currencyDTO) {
        return currencyMapper.toCurrencyDto(currencyRepository.saveAndFlush(currencyMapper.toCurrency(currencyDTO)));
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public CurrencyDTO updateCurrency(int id, CurrencyDTO currencyDTO) {
        Currency currencyUpdateById = currencyRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        Currency actualCurrency = currencyMapper.toCurrency(currencyDTO);

        currencyUpdateById.setBankName(actualCurrency.getBankName());
        currencyUpdateById.setCurrencyCode(actualCurrency.getCurrencyCode());
        currencyUpdateById.setPurchaseCurrency(actualCurrency.getPurchaseCurrency());
        currencyUpdateById.setSaleOfCurrency(actualCurrency.getSaleOfCurrency());

        return currencyMapper.toCurrencyDto(currencyRepository.saveAndFlush(currencyUpdateById));
    }
}
