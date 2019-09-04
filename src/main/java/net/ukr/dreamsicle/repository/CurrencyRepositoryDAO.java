package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.Currency;

import java.util.List;

public interface CurrencyRepositoryDAO {

    Currency findCurrencyById(int id);

    List<Currency> findAllCurrencies();

    void deleteCurrencyById(int id);

    void createCurrency(Currency currency);

    void updateCurrency(int id, Currency currency);
}
