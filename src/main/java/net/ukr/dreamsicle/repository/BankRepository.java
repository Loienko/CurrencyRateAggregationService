package net.ukr.dreamsicle.repository;

import com.querydsl.core.types.Predicate;
import net.ukr.dreamsicle.model.bank.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface BankRepository extends MongoRepository<Bank, String>, QuerydslPredicateExecutor<Bank> {
    Optional<Bank> findBankByBankCode(String bankCode);

    boolean existsBankByBankName(String bankName);

    Page<Bank> findAll(Predicate predicate, Pageable page);
}
