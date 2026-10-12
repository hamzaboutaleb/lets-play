package play.service;

import org.springframework.stereotype.Service;
import play.dto.auth.AuthResponse;
import play.dto.auth.LoginRequest;
import play.dto.auth.RegisterRequest;

@Service
public class AuthService {

    public AuthResponse register(RegisterRequest request) {
        return null;
    }

    public AuthResponse login(LoginRequest request) {
        return null;
    }
}