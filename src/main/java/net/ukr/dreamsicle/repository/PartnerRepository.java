package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.partners.Partner;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartnerRepository extends MongoRepository<Partner, ObjectId> {
}
