package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.impl.CurrencyRepositoryDAOImpl;

import java.util.List;


/**
 * @deprecated
 *
 * CurrencyRepositoryDAO interface for class {@link CurrencyRepositoryDAOImpl}.
 *
 * @author yurii.loienko
 * @version 1.0
 */

@Deprecated
public interface CurrencyRepositoryDAO {

    Currency findCurrencyById(int id);

    List<Currency> findAllCurrencies();

    boolean deleteCurrencyById(int id);

    Integer createCurrency(Currency currency);

    boolean updateCurrency(int id, Currency currency);
}
