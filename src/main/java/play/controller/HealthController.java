package play.controller;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.bson.Document;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    private final MongoTemplate mongo;

    public HealthController(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @GetMapping("/mongo")
    public String mongo() {
        mongo.getDb().runCommand(new Document("ping", 1));
        return "MongoDB connected";
    }
}