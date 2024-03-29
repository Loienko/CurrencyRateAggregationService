package net.ukr.dreamsicle.controller;

import net.ukr.dreamsicle.dto.currency.CurrencyDTO;
import net.ukr.dreamsicle.model.currency.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import net.ukr.dreamsicle.repository.RoleRepository;
import net.ukr.dreamsicle.repository.UserRepository;
import net.ukr.dreamsicle.service.UserService;
import net.ukr.dreamsicle.util.RestPageImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static net.ukr.dreamsicle.util.currency.CurrencyProvider.ID;
import static net.ukr.dreamsicle.util.currency.CurrencyProvider.*;
import static net.ukr.dreamsicle.util.HttpHeaderProvider.getHeader;
import static net.ukr.dreamsicle.util.user.UserProvider.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CurrencyControllerIntegrationsTestIT {
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
    @Autowired
    private RestTemplate restTemplate;
    @LocalServerPort
    private int port;

    @Before
    public void initDb() {
        roleRepository.save(ROLE_ADMIN);
        roleRepository.save(ROLE_USER);

        userRepository.save(getUserIntegrationTestForCreateToken(ID, USERNAME_ADMIN, EMAIL_ADMIN, ROLE_ADMIN));
        userRepository.save(getUserIntegrationTestForCreateToken(ID + 1, USERNAME_USER, EMAIL_USER, ROLE_USER));

        TOKEN_ADMIN = userService.login(getUsernameAndPasswordIntegrationTest(USERNAME_ADMIN, PASSWORD_WITHOUT_ENCODE)).replace(CAUTION, "");
        TOKEN_USER = userService.login(getUsernameAndPasswordIntegrationTest(USERNAME_USER, PASSWORD_WITHOUT_ENCODE)).replace(CAUTION, "");
    }

    private String getRootUrl() {
        return HTTP_LOCALHOST + port;
    }

    @Test
    public void testGetAllCurrenciesReturnStatus200Ok() {
        currencyRepository.saveAndFlush(getCurrencyProvider());

        ResponseEntity<RestPageImpl<CurrencyDTO>> response = restTemplate.exchange(getRootUrl() + CURRENCIES, GET, null, new ParameterizedTypeReference<RestPageImpl<CurrencyDTO>>() {
        });

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getContent().size());
        assertEquals(ID, response.getBody().getContent().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findCurrencyByIdReturnStatus200Ok() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, GET, new HttpEntity<>(currency), Currency.class, currency.getId());
        Currency body = response.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getId());
        assertEquals(currency.getBankName(), Objects.requireNonNull(body).getBankName());
        assertEquals(currency.getCurrencyCode(), Objects.requireNonNull(response.getBody()).getCurrencyCode());
        assertEquals(currency.getPurchaseCurrency(), response.getBody().getPurchaseCurrency());
        assertEquals(currency.getSaleOfCurrency(), response.getBody().getSaleOfCurrency());
    }

    @Test
    public void findCurrencyByIdReturnStatus404NotFound() {
        HttpEntity<Currency> entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, GET, entity, Currency.class, Integer.MAX_VALUE);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void createCurrencyReturnStatus201Created() {
        Currency currency = getCurrencyProvider();

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, getHeader(currency, TOKEN_ADMIN), Currency.class);
        Currency body = response.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getId());
        assertEquals(currency.getBankName(), Objects.requireNonNull(body).getBankName());
        assertEquals(currency.getCurrencyCode(), Objects.requireNonNull(body).getCurrencyCode());
        assertEquals(currency.getPurchaseCurrency(), Objects.requireNonNull(body).getPurchaseCurrency());
        assertEquals(currency.getSaleOfCurrency(), Objects.requireNonNull(body).getSaleOfCurrency());
    }

    @Test
    public void createCurrencyNotInputBankNameReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider(null, CURRENCY_CODE, PURCHASE_CURRENCY, SALE_OF_CURRENCY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, getHeader(currency, TOKEN_ADMIN), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please fill the bank name"));
    }

    @Test
    public void createCurrencyNotValidBankNameReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider("1", CURRENCY_CODE, PURCHASE_CURRENCY, SALE_OF_CURRENCY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, getHeader(currency, TOKEN_ADMIN), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for bank name"));
    }

    @Test
    public void createCurrencyNotValidCurrencyCodeReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider(BANK_NAME, "US", PURCHASE_CURRENCY, SALE_OF_CURRENCY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, getHeader(currency, TOKEN_ADMIN), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for currency code"));
    }

    @Test
    public void createCurrencyNotValidPurchaseCurrencyReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, ".00", SALE_OF_CURRENCY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, getHeader(currency, TOKEN_ADMIN), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for purchase currency"));
    }

    @Test
    public void createCurrencyNotInputPurchaseCurrencyReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, null, SALE_OF_CURRENCY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, getHeader(currency, TOKEN_ADMIN), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please fill the purchase currency"));
    }

    @Test
    public void createCurrencyNotValidSaleOfCurrencyReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, PURCHASE_CURRENCY, ".15");

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, getHeader(currency, TOKEN_ADMIN), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for sale of currency"));
    }

    @Test
    public void createCurrencyNotInputSaleOfCurrencyReturnStatus400BadRequest() {
        Currency currency = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, PURCHASE_CURRENCY, null);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, getHeader(currency, TOKEN_ADMIN), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please fill the sale of currency"));
    }

    @Test
    public void createCurrencyReturnStatus401Unauthorized() {
        Currency currency = getCurrencyProvider();

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, getHeader(currency, null), Currency.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void createCurrencyReturnStatus403Forbidden() {
        Currency currency = getCurrencyProvider();

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES, POST, getHeader(currency, TOKEN_USER), Currency.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void updateCurrencyReturnStatus202Accepted() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());
        Currency currencyForUpdate = getCurrencyProvider();

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, TOKEN_ADMIN), Currency.class, currency.getId());
        Currency body = response.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getId());
        assertEquals(currency.getId(), Objects.requireNonNull(body).getId());
        assertEquals(currencyForUpdate.getBankName(), Objects.requireNonNull(body).getBankName());
        assertEquals(currencyForUpdate.getPurchaseCurrency(), Objects.requireNonNull(body).getPurchaseCurrency());
        assertEquals(currencyForUpdate.getSaleOfCurrency(), Objects.requireNonNull(body).getSaleOfCurrency());
    }

    @Test
    public void updateCurrencyNotInputBankNameReturnStatus400BadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());
        Currency currencyForUpdate = getCurrencyNotValidDataProvider(null, CURRENCY_CODE, PURCHASE_CURRENCY, SALE_OF_CURRENCY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, TOKEN_ADMIN), String.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please fill the bank name"));
    }

    @Test
    public void updateCurrencyNotValidBankNameReturnStatus400BadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());
        Currency currencyForUpdate = getCurrencyNotValidDataProvider("1", CURRENCY_CODE, PURCHASE_CURRENCY, SALE_OF_CURRENCY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, TOKEN_ADMIN), String.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for bank name"));
    }

    @Test
    public void updateCurrencyNotValidCurrencyCodeReturnStatus400BadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());
        Currency currencyForUpdate = getCurrencyNotValidDataProvider(BANK_NAME, "US1", PURCHASE_CURRENCY, SALE_OF_CURRENCY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, TOKEN_ADMIN), String.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for currency code"));
    }

    @Test
    public void updateCurrencyNotValidPurchaseCurrencyReturnStatus400BadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());
        Currency currencyForUpdate = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, ".00", SALE_OF_CURRENCY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, TOKEN_ADMIN), String.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for purchase currency"));
    }

    @Test
    public void updateCurrencyNotInputPurchaseCurrencyReturnStatus400BadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());
        Currency currencyForUpdate = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, null, SALE_OF_CURRENCY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, TOKEN_ADMIN), String.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please fill the purchase currency"));
    }

    @Test
    public void updateCurrencyNotValidSaleOfCurrencyReturnStatus400BadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());
        Currency currencyForUpdate = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, PURCHASE_CURRENCY, ".15");

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, TOKEN_ADMIN), String.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for sale of currency"));
    }

    @Test
    public void updateCurrencyNotInputSaleOfCurrencyReturnStatus400BadRequest() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());
        Currency currencyForUpdate = getCurrencyNotValidDataProvider(BANK_NAME, CURRENCY_CODE, PURCHASE_CURRENCY, null);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, TOKEN_ADMIN), String.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please fill the sale of currency"));
    }

    @Test
    public void updateCurrencyReturnStatus404NotFound() {
        Currency currencyForUpdate = getCurrencyProvider();

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, TOKEN_ADMIN), Currency.class, Integer.MAX_VALUE);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void updateCurrencyReturnStatus401Unauthorized() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());
        Currency currencyForUpdate = getCurrencyProvider();

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, null), Currency.class, currency.getId());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void updateCurrencyReturnStatus403Forbidden() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());
        Currency currencyForUpdate = getCurrencyProvider();

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, PUT, getHeader(currencyForUpdate, TOKEN_USER), Currency.class, currency.getId());

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void deleteCurrencyReturnStatus204NoContent() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, DELETE, getHeader(currency, TOKEN_ADMIN), Currency.class, currency.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteCurrencyReturnStatus404NotFound() {
        HttpEntity<Object> entity = getHeader(null, TOKEN_ADMIN);
        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, DELETE, entity, Currency.class, Integer.MAX_VALUE);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteCurrencyReturnStatus401Unauthorized() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, DELETE, getHeader(currency, null), Currency.class, currency.getId());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void deleteCurrencyReturnStatus403Forbidden() {
        Currency currency = currencyRepository.saveAndFlush(getCurrencyProvider());

        ResponseEntity<Currency> response = testRestTemplate.exchange(getRootUrl() + CURRENCIES + BY_ID, DELETE, getHeader(currency, TOKEN_USER), Currency.class, currency.getId());

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
