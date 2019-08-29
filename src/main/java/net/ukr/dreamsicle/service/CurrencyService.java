package net.ukr.dreamsicle.service;

import lombok.extern.java.Log;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class CurrencyService {

    private final CurrencyRepositoryDAO currencyRepositoryDAO;

    @Autowired
    public CurrencyService(CurrencyRepositoryDAO currencyRepositoryDAO) {
        this.currencyRepositoryDAO = currencyRepositoryDAO;
    }

    public List<Currency> getAllCurrenciesData() {
        return currencyRepositoryDAO.getFindAllCurrency();
    }

    public Currency getFindCurrencyById(int id) {
        return currencyRepositoryDAO.getFindCurrencyById(id);
    }

    public void getDeleteCurrencyById(int id) {
        currencyRepositoryDAO.getDeleteCurrencyById(id);
    }

    public void getCreateCurrency(Currency currency) {
        currencyRepositoryDAO.getCreateCurrency(currency);
    }

    public void getUpdateCurrency(int id, Currency currency) {
        currencyRepositoryDAO.getUpdateCurrency(id, currency);
    }
}
