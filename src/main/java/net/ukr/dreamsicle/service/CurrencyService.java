package net.ukr.dreamsicle.service;

import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.exception.NotFoundException;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepositoryDAO currencyRepositoryDAO;
    private final CurrencyMapper currencyMapper;

    public List<CurrencyDTO> allCurrencies() {
        return currencyMapper.toCurrencyDTOs(currencyRepositoryDAO.findAllCurrencies());
    }

    public CurrencyDTO findCurrencyById(int id) {
        Currency currencyById = currencyRepositoryDAO.findCurrencyById(id);

        if (currencyById == null) {
            throw new NotFoundException();
        }

        return currencyMapper.toCurrencyDto(currencyById);
    }

    public void deleteCurrencyById(int id) {
        Currency currencyById = currencyRepositoryDAO.findCurrencyById(id);

        if (currencyById == null) {
            throw new NotFoundException();
        }

        currencyRepositoryDAO.deleteCurrencyById(id);
    }

    public CurrencyDTO createCurrency(CurrencyDTO currencyDTO) {
        Currency currency = currencyMapper.toCurrency(currencyDTO);
        Integer id = currencyRepositoryDAO.createCurrency(currency);

        if (id == null) {
            throw new NotFoundException();
        }
        Currency currencyById = currencyRepositoryDAO.findCurrencyById(id);

        return currencyMapper.toCurrencyDto(currencyById);
    }

    public CurrencyDTO updateCurrency(int id, CurrencyDTO currencyDTO) {
        Currency currencyById = currencyRepositoryDAO.findCurrencyById(id);

        if (currencyById == null) {
            throw new NotFoundException();
        }

        Currency currency = currencyMapper.toCurrency(currencyDTO);
        currency.setId(id);
        currencyRepositoryDAO.updateCurrency(id, currency);
        return currencyMapper.toCurrencyDto(currencyRepositoryDAO.findCurrencyById(id));
    }
}
