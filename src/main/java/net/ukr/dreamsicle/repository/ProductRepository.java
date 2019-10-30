package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Long> {
}
