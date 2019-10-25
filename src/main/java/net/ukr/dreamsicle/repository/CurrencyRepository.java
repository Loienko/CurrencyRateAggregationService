package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link Currency}.
 *
 * @author yurii.loienko
 * @version 1.0
 */
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
