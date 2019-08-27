package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.Currency;

import java.util.List;

public interface CurrencyRepositoryDAO {
    Currency getFindCurrencyById(int id);

    List<Currency> getFindAllCurrency();

    void getDeleteCurrencyById(int id);

    void getCreateCurrency(Currency currency);

    void getUpdateCurrency(int id, Currency currency);

}
