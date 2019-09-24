package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.Currency;

import java.util.List;


/**
 * @deprecated
 */

@Deprecated
public interface CurrencyRepositoryDAO {

    Currency findCurrencyById(int id);

    List<Currency> findAllCurrencies();

    boolean deleteCurrencyById(int id);

    Integer createCurrency(Currency currency);

    boolean updateCurrency(int id, Currency currency);
}
