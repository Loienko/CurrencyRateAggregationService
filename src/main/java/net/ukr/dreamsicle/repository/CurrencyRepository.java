package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
