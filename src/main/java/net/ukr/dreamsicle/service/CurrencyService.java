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

    public List<Currency> allCurrenciesData() {
        return currencyRepositoryDAO.findAllCurrency();
    }

    Currency findCurrencyById(int id) {
        return currencyRepositoryDAO.findCurrencyById(id);
    }

    void deleteCurrencyById(int id) {
        currencyRepositoryDAO.deleteCurrencyById(id);
    }

    void createCurrency(Currency currency) {
        currencyRepositoryDAO.createCurrency(currency);
    }

    void updateCurrency(int id, Currency currency) {
        currencyRepositoryDAO.updateCurrency(id, currency);
    }
}
