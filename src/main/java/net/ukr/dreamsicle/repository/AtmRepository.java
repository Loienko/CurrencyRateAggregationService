package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.atm.ATM;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AtmRepository extends MongoRepository<ATM, Long> {
}
