package net.ukr.dreamsicle.service;

import lombok.extern.java.Log;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
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
        return currencyRepositoryDAO.findAllCurrencies();
    }

    public Currency findCurrencyById(int id) {
        Currency currencyById = currencyRepositoryDAO.findCurrencyById(id);

        if (currencyById == null) {
            throw new ResourceNotFoundException();
        }

        return currencyById;
    }

    public void deleteCurrencyById(int id) {
        currencyRepositoryDAO.deleteCurrencyById(id);
    }

    public Integer createCurrency(Currency currency) {
        return currencyRepositoryDAO.createCurrency(currency);
    }

    public void updateCurrency(int id, Currency currency) {
        currencyRepositoryDAO.updateCurrency(id, currency);
    }
}
