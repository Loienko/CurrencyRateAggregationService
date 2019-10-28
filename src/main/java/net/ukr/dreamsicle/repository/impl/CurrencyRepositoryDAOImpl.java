package net.ukr.dreamsicle.repository.impl;

import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.currency.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class for connection with DB uses Spring JDBCTemplate technology
 *
 * @author yurii.loienko
 * @version 1.0
 * @deprecated
 */
@Repository
@Deprecated
public class CurrencyRepositoryDAOImpl implements CurrencyRepositoryDAO {

    private static final String ID = "id";
    private static final String BANK_NAME = "bankName";
    private static final String CURRENCY_CODE = "currencyCode";
    private static final String PURCHASE_CURRENCY = "purchaseCurrency";
    private static final String SALE_OF_CURRENCY = "saleOfCurrency";
    private final GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    private final BeanPropertyRowMapper<Currency> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Currency.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CurrencyRepositoryDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Currency> findAllCurrencies() {
        return namedParameterJdbcTemplate.query("SELECT * FROM currency", beanPropertyRowMapper);
    }

    @Override
    public Currency findCurrencyById(int id) {
        String sqlQuery = "SELECT * FROM currency WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource(ID, id);

        try {
            return namedParameterJdbcTemplate.queryForObject(sqlQuery, namedParameters, beanPropertyRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public boolean deleteCurrencyById(int id) {
        String sqlQuery = "delete FROM currency WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource(ID, id);
        return verificationOfSuccess(namedParameterJdbcTemplate.update(sqlQuery, namedParameters, generatedKeyHolder));
    }

    @Override
    public Integer createCurrency(Currency currency) {
        String sqlQuery = "insert into currency (bank_name, currency_code, purchase_currency, sale_of_currency) " +
                "values(:bankName, :currencyCode, :purchaseCurrency, :saleOfCurrency)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource.addValue(BANK_NAME, currency.getBankName());
        mapSqlParameterSource.addValue(CURRENCY_CODE, currency.getCurrencyCode());
        mapSqlParameterSource.addValue(PURCHASE_CURRENCY, currency.getPurchaseCurrency());
        mapSqlParameterSource.addValue(SALE_OF_CURRENCY, currency.getSaleOfCurrency());

        namedParameterJdbcTemplate.update(sqlQuery, mapSqlParameterSource, generatedKeyHolder);
        return (Integer) generatedKeyHolder.getKeys().get(ID);
    }

    @Override
    public boolean updateCurrency(int id, Currency currency) {
        String sqlQuery = "update currency " +
                "set bank_name = :bankName, currency_code = :currencyCode, purchase_currency = :purchaseCurrency, sale_of_currency = :saleOfCurrency";

        MapSqlParameterSource currencyParameters = new MapSqlParameterSource();
        currencyParameters.addValue(BANK_NAME, currency.getBankName());
        currencyParameters.addValue(CURRENCY_CODE, currency.getCurrencyCode());
        currencyParameters.addValue(PURCHASE_CURRENCY, currency.getPurchaseCurrency());
        currencyParameters.addValue(SALE_OF_CURRENCY, currency.getSaleOfCurrency());
        currencyParameters.addValue(ID, id);

        return verificationOfSuccess(namedParameterJdbcTemplate.update(sqlQuery, currencyParameters, generatedKeyHolder));
    }

    private boolean verificationOfSuccess(int update) {
        return update == 1;
    }
}
