package net.ukr.dreamsicle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.exception.ResourceIsStaleException;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class CurrencyService {

    private final CurrencyRepositoryDAO currencyRepositoryDAO;
    private final CurrencyMapper currencyMapper;

    public List<CurrencyDTO> allCurrencies() {
        return currencyMapper.toCurrencyDTOs(currencyRepositoryDAO.findAllCurrencies());
    }

    public CurrencyDTO findCurrencyById(int id) {
        Currency currencyById = currencyRepositoryDAO.findCurrencyById(id);

        if (currencyById == null) {
            throw new ResourceNotFoundException();
        }

        return currencyMapper.toCurrencyDto(currencyById);
    }

    @Transactional
    public void deleteCurrencyById(int id) {
        Currency currencyById = currencyRepositoryDAO.findCurrencyById(id);

        if (currencyById == null) {
            throw new ResourceNotFoundException();
        }

        boolean isDelete = currencyRepositoryDAO.deleteCurrencyById(id);

        if (!isDelete) {
            throw new ResourceIsStaleException();
        }
    }

    @Transactional
    public CurrencyDTO createCurrency(CurrencyDTO currencyDTO) {
        Currency currency = currencyMapper.toCurrency(currencyDTO);
        Integer id = currencyRepositoryDAO.createCurrency(currency);

        if (id == null) {
            throw new ResourceNotFoundException();
        }
        Currency currencyById = currencyRepositoryDAO.findCurrencyById(id);

        return currencyMapper.toCurrencyDto(currencyById);
    }

    @Transactional
    public CurrencyDTO updateCurrency(int id, CurrencyDTO currencyDTO) {
        Currency currencyById = currencyRepositoryDAO.findCurrencyById(id);

        if (currencyById == null) {
            throw new ResourceNotFoundException();
        }

        Currency currency = currencyMapper.toCurrency(currencyDTO);
        currency.setId(id);
        currency.setVersion(currencyById.getVersion());
        boolean isUpdate = currencyRepositoryDAO.updateCurrency(id, currency);

        if (!isUpdate) {
            throw new ResourceIsStaleException();
        }
        return currencyMapper.toCurrencyDto(currencyRepositoryDAO.findCurrencyById(id));
    }
}
