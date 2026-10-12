package play.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import play.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Optional<User> findByEmail(String email);
}