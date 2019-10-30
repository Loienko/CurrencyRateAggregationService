package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.bank.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankRepository extends MongoRepository<Bank, Long> {
}
