package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.office.Office;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfficeRepository extends MongoRepository<Office, String> {
}
