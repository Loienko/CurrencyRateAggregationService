package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.CurrencyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurrencyService {

    Page<CurrencyDTO> findAllCurrencies(Pageable pageable);

    CurrencyDTO findCurrencyById(int id);

    void deleteCurrencyById(int id);

    CurrencyDTO createCurrency(CurrencyDTO currencyDTO);

    CurrencyDTO updateCurrency(int id, CurrencyDTO currencyDTO);
}
