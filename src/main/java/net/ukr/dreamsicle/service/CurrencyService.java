package net.ukr.dreamsicle.service;

import lombok.Lombok;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

/**
 * REST controller for work with {@link Currency} data (findAll, findById, create, update, delete)
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class CurrencyService {

    private final CurrencyMapper currencyMapper;
    private final CurrencyRepository currencyRepository;

    /**
     * Returns a {@link Page} of currencies meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of currencies
     */
    public Page<CurrencyDTO> findAllCurrencies(Pageable pageable) {
        return currencyMapper.toCurrencyDTOs(currencyRepository.findAll(pageable));
    }

    /**
     * Retrieves an currency by its id.
     *
     * @param id
     * @return the currency with the given id
     * @throws ResourceNotFoundException if {@code id} is not found
     */
    public CurrencyDTO findCurrencyById(long id) {
        return currencyRepository.findById(id)
                .map(currencyMapper::toCurrencyDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Deletes the currency with the given id. Throws ResourceNotFoundException.class if none found the currency by id
     *
     * @param id
     * @return deleted currency by id
     * @throws ResourceNotFoundException if {@code id} is not found
     */
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteCurrencyById(long id) {
        currencyRepository.deleteById(
                currencyRepository.findById(id)
                        .orElseThrow(ResourceNotFoundException::new)
                        .getId());
    }

    /**
     * Create currency and flushes changes instantly.
     *
     * @param currencyDTO
     * @return the saved currency
     */
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public CurrencyDTO createCurrency(CurrencyDTO currencyDTO) {
        return currencyMapper.toCurrencyDto(currencyRepository.saveAndFlush(currencyMapper.toCurrency(currencyDTO)));
    }

    /**
     * The method update currency by id.
     *
     * @param id
     * @param currencyDTO
     * @return the updated currency
     * @throws ResourceNotFoundException if {@code id} is not found
     */
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public CurrencyDTO updateCurrency(long id, CurrencyDTO currencyDTO) {
        Currency currencyUpdateById = currencyRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        Currency actualCurrency = currencyMapper.toCurrency(currencyDTO);

        currencyUpdateById.setBankName(actualCurrency.getBankName());
        currencyUpdateById.setCurrencyCode(actualCurrency.getCurrencyCode());
        currencyUpdateById.setPurchaseCurrency(actualCurrency.getPurchaseCurrency());
        currencyUpdateById.setSaleOfCurrency(actualCurrency.getSaleOfCurrency());

        return currencyMapper.toCurrencyDto(currencyRepository.saveAndFlush(currencyUpdateById));
    }
}
