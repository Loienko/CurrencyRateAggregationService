package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.bank.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BankRepository extends MongoRepository<Bank, String> {
    Optional<Bank> findBankByBankCode(String bankCode);

    boolean existsBankByBankName(String bankName);
}
