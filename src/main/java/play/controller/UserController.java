package play.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    public String getAllUsers() {
        return "Get all users";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable String id) {
        return "Get user by id: " + id;
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable String id) {
        return "Update user: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        return "Delete user: " + id;
    }
}