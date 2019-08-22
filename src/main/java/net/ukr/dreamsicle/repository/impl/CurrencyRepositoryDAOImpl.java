package net.ukr.dreamsicle.repository.impl;

import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyRepositoryDAOImpl implements CurrencyRepositoryDAO {

    private final String ID = "id";
    private final String BANK_NAME = "bankName";
    private final String CURRENCY_CODE = "currencyCode";
    private final String PURCHASE_CURRENCY = "purchaseCurrency";
    private final String SALE_OF_CURRENCY = "saleOfCurrency";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final BeanPropertyRowMapper<Currency> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Currency.class);

    @Autowired
    public CurrencyRepositoryDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Currency getFindCurrencyById(int searchIdParam) {
        String sqlQuery = "SELECT * FROM currency WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource(ID, searchIdParam);
        return namedParameterJdbcTemplate.queryForObject(sqlQuery, namedParameters, Currency.class);
    }

    @Override
    public List<Currency> getFindAllCurrency() {
        return namedParameterJdbcTemplate.query("SELECT * FROM currency", beanPropertyRowMapper);
    }

    @Override
    public void getDeleteCurrencyById(int searchIdParam) {
        String sqlQuery = "delete FROM currency WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource(ID, searchIdParam);
        namedParameterJdbcTemplate.queryForObject(sqlQuery, namedParameters, Currency.class);
    }

    @Override
    public void getCreateCurrency(Currency currency) {
        String sqlQuery = "insert into currency (id, bank_name, currency_code,purchase_currency, sale_of_currency) " +
                "values(:id, :bankName, :currencyCode, :purchaseCurrency, :saleOfCurrency)";

        Map<String, Object> currencyParameters = new HashMap<>();
        currencyParameters.put(ID, currency.getId());
        currencyParameters.put(BANK_NAME, currency.getBankName());
        currencyParameters.put(CURRENCY_CODE, currency.getCurrencyCode());
        currencyParameters.put(PURCHASE_CURRENCY, currency.getPurchaseCurrency());
        currencyParameters.put(SALE_OF_CURRENCY, currency.getSaleOfCurrency());

        namedParameterJdbcTemplate.update(sqlQuery, currencyParameters);
    }

    @Override
    public void getUpdateCurrency(int searchIdParam, Currency currency) {
        String sqlQuery = "update currency " +
                "set bank_name = :bankName, currency_code = :currencyCode, purchase_currency = :purchaseCurrency, sale_of_currency = :saleOfCurrency " +
                "where id = :id";

        Map<String, Object> currencyParameters = new HashMap<>();
        currencyParameters.put(BANK_NAME, currency.getBankName());
        currencyParameters.put(CURRENCY_CODE, currency.getCurrencyCode());
        currencyParameters.put(PURCHASE_CURRENCY, currency.getPurchaseCurrency());
        currencyParameters.put(SALE_OF_CURRENCY, currency.getSaleOfCurrency());
        currencyParameters.put(ID, searchIdParam);

        namedParameterJdbcTemplate.update(sqlQuery, currencyParameters);
    }
}