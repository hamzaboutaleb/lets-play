package play.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import play.dto.auth.UserResponse;

@Service
public class UserService {

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return Page.empty(pageable);
    }
}