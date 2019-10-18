package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import net.ukr.dreamsicle.repository.RoleRepository;
import net.ukr.dreamsicle.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static net.ukr.dreamsicle.util.CurrencyProvider.ID;
import static net.ukr.dreamsicle.util.CurrencyProvider.*;
import static net.ukr.dreamsicle.util.UserProvider.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CurrencyControllerIntegrationsTest {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    @Before
    public void initDb() {
        roleRepository.save(ROLE_ADMIN);
        roleRepository.save(ROLE_USER);

        userRepository.save(getUserProviderForIntegrationTest(ID, USERNAME_ADMIN, EMAIL_ADMIN, ROLE_ADMIN));
        userRepository.save(getUserProviderForIntegrationTest(ID + 1, USERNAME_USER, EMAIL_USER, ROLE_USER));

        TOKEN_ADMIN = userService.authenticateUser(getUsernameAndPasswordIntegrationTest(USERNAME_ADMIN, PASSWORD_WITHOUT_ENCODE)).replace(CAUTION, "");
        TOKEN_USER = userService.authenticateUser(getUsernameAndPasswordIntegrationTest(USERNAME_USER, PASSWORD_WITHOUT_ENCODE)).replace(CAUTION, "");
    }

    private String getRootUrl() {
        return HTTP_LOCALHOST + port;
    }

    @Test
    public void testGetAllCurrenciesReturnStatus200Ok() {
        currencyRepository.saveAndFlush(getCurrencyProvider(ID));
        currencyRepository.saveAndFlush(getCurrencyProvider(ID + 1));

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, GET, null, Currency.class);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findCurrencyByIdReturnStatus200Ok() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));

        HttpEntity<Currency> entity = new HttpEntity<>(currency);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, GET, entity, Currency.class, currency.getId());
        Currency body = response.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currency.getBankName(), Objects.requireNonNull(body).getBankName());
        assertEquals(currency.getCurrencyCode(), Objects.requireNonNull(response.getBody()).getCurrencyCode());
        assertEquals(currency.getPurchaseCurrency(), response.getBody().getPurchaseCurrency());
        assertEquals(currency.getSaleOfCurrency(), response.getBody().getSaleOfCurrency());
    }

    @Test
    public void findCurrencyByIdReturnStatus404NotFound() {
        HttpEntity<Currency> entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, GET, entity, Currency.class, ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createCurrencyReturnStatus200Ok() {
        Currency currency = getCurrencyProvider(ID);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, entity, Currency.class);
        Currency body = response.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(currency.getBankName(), Objects.requireNonNull(body).getBankName());
        assertEquals(currency.getCurrencyCode(), Objects.requireNonNull(body).getCurrencyCode());
        assertEquals(currency.getPurchaseCurrency(), Objects.requireNonNull(body).getPurchaseCurrency());
        assertEquals(currency.getSaleOfCurrency(), Objects.requireNonNull(body).getSaleOfCurrency());
    }

    @Test
    public void createCurrencyNotValidDataReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider("", "", "", "");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, entity, Currency.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createCurrencyNotValidBankNameReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider("", CURRENCY_CODE, PURCHASE_CURRENCY, SALE_OF_CURRENCY);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, entity, Currency.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createCurrencyNotValidCurrencyCodeReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider(BANK_NAME, "US", PURCHASE_CURRENCY, SALE_OF_CURRENCY);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, entity, Currency.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createCurrencyNotValidPurchaseCurrencyReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, ".00", SALE_OF_CURRENCY);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, entity, Currency.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createCurrencyNotValidSaleOfCurrencyReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, PURCHASE_CURRENCY, ".15");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, entity, Currency.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createCurrencyReturnStatus401Unauthorized() {
        Currency currency = getCurrencyProvider(ID);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, null);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, entity, Currency.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void createCurrencyReturnStatus403AccessDenied() {
        Currency currency = getCurrencyProvider(ID);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_USER);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, entity, Currency.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void updateCurrencyReturnStatus200Ok() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));
        Currency currencyForUpdate = getCurrencyProvider(ID + 1);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currencyForUpdate, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, entity, Currency.class, currency.getId());
        Currency body = response.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(currency.getId(), Objects.requireNonNull(body).getId());
        assertEquals(currencyForUpdate.getBankName(), Objects.requireNonNull(body).getBankName());
        assertEquals(currencyForUpdate.getPurchaseCurrency(), Objects.requireNonNull(body).getPurchaseCurrency());
        assertEquals(currencyForUpdate.getSaleOfCurrency(), Objects.requireNonNull(body).getSaleOfCurrency());
    }

    @Test
    public void updateCurrencyNotValidAllDataReturnStatus400OkBadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));
        Currency currencyForUpdate = getCurrencyNotValidDataProvider("", "", "", "");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currencyForUpdate, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, entity, Currency.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateCurrencyNotValidBankNameReturnStatus400OkBadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));
        Currency currencyForUpdate = getCurrencyNotValidDataProvider("", CURRENCY_CODE, PURCHASE_CURRENCY, SALE_OF_CURRENCY);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currencyForUpdate, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, entity, Currency.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateCurrencyNotValidCurrencyCodeReturnStatus400OkBadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));
        Currency currencyForUpdate = getCurrencyNotValidDataProvider(BANK_NAME, "US1", PURCHASE_CURRENCY, SALE_OF_CURRENCY);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currencyForUpdate, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, entity, Currency.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateCurrencyNotValidPurchaseCurrencyReturnStatus400OkBadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));
        Currency currencyForUpdate = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, ".00", SALE_OF_CURRENCY);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currencyForUpdate, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, entity, Currency.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateCurrencyNotValidSaleOfCurrencyReturnStatus400OkBadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));
        Currency currencyForUpdate = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, PURCHASE_CURRENCY, ".15");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currencyForUpdate, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, entity, Currency.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateCurrencyReturnStatus404NotFound() {
        Currency currencyForUpdate = getCurrencyProvider(ID);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currencyForUpdate, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, entity, Currency.class, currencyForUpdate.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateCurrencyReturnStatus401Unauthorized() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));
        Currency currencyForUpdate = getCurrencyProvider(ID + 1);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, null);
        HttpEntity<Object> entity = new HttpEntity<>(currencyForUpdate, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, entity, Currency.class, currency.getId());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void updateCurrencyReturnStatus403AccessDenied() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));
        Currency currencyForUpdate = getCurrencyProvider(ID + 1);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_USER);
        HttpEntity<Object> entity = new HttpEntity<>(currencyForUpdate, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, entity, Currency.class, currency.getId());

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void deleteCurrencyReturnStatus200Ok() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, DELETE, entity, Currency.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteCurrencyReturnStatus404NotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ADMIN);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, DELETE, entity, Currency.class, ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteCurrencyReturnStatus401Unauthorized() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, null);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, DELETE, entity, Currency.class, currency.getId());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void deleteCurrencyReturnStatus403AccessDenied() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider(ID));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, TOKEN_USER);
        HttpEntity<Object> entity = new HttpEntity<>(currency, headers);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, DELETE, entity, Currency.class, currency.getId());

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
