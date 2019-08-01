package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

    @Query(value = "SELECT max(purchase_currency) from currency where currency_code=:curr", nativeQuery = true)
    String findMaxPurchaseCurrency(String curr);

    @Query(value = "SELECT min(sale_of_currency) from currency where currency_code=:curr", nativeQuery = true)
    String findMinSaleOfCurrency(String curr);


    @Query(value = "select bank_name from currency where currency_code=:curr AND purchase_currency=:max", nativeQuery = true)
    String findByBankNameForPurchaseCurrency(String curr, String max);


    @Query(value = "select bank_name from currency where currency_code=:curr AND sale_of_currency=:min", nativeQuery = true)
    String findByBankNameForSaleOfCurrency(String curr, String min);
}
