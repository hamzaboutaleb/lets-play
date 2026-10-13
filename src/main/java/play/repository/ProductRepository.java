package play.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import play.model.Product;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    Page<Product> findByUserId(String userId, Pageable pageable);

    Optional<Product> findById(String id);

    boolean existsById(String id);
}