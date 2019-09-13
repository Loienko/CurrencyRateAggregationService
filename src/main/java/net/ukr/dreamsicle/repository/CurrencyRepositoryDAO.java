package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.Currency;

import java.util.List;

public interface CurrencyRepositoryDAO {

    Currency findCurrencyById(int id);

    List<Currency> findAllCurrencies();

    Integer deleteCurrencyById(int id);

    Integer createCurrency(Currency currency);

    Integer updateCurrency(int id, Currency currency);
}
