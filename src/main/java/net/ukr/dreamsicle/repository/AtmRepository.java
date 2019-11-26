package net.ukr.dreamsicle.repository;

import com.querydsl.core.types.Predicate;
import net.ukr.dreamsicle.model.atm.ATM;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AtmRepository extends MongoRepository<ATM, ObjectId>, QuerydslPredicateExecutor<ATM> {

    Page<ATM> findAll(Predicate predicate, Pageable page);
}
