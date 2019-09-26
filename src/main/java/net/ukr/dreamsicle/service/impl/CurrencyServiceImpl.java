package net.ukr.dreamsicle.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import net.ukr.dreamsicle.service.CurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyMapper currencyMapper;
    private final CurrencyRepository currencyRepository;

    @Override
    public Page<CurrencyDTO> findAllCurrencies(Pageable pageable) {
        return currencyMapper.toCurrencyDTOs(currencyRepository.findAll(pageable));
    }

    @Override
    public CurrencyDTO findCurrencyById(int id) {
        return currencyRepository.findById(id)
                .map(currencyMapper::toCurrencyDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteCurrencyById(int id) {
        currencyRepository.deleteById(currencyRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new)
                .getId());
    }

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public CurrencyDTO createCurrency(CurrencyDTO currencyDTO) {
        return currencyMapper.toCurrencyDto(currencyRepository.saveAndFlush(currencyMapper.toCurrency(currencyDTO)));
    }

    @Override
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
