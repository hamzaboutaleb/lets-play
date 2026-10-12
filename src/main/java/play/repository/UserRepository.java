package play.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import play.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    User findByEmail(String email);
}