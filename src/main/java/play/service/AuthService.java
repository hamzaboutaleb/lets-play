package play.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import play.dto.auth.AuthResponse;
import play.dto.auth.LoginRequest;
import play.dto.auth.RegisterRequest;
import play.dto.auth.UserResponse;
import play.exception.EmailAlreadyExistsException;
import play.exception.NameAlreadyTakenException;
import play.model.Role;
import play.model.User;
import play.repository.UserRepository;
import play.security.CustomUserDetails;
import play.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        if (userRepository.existsByName(request.getName())) {
            throw new NameAlreadyTakenException(request.getName());
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User saved = userRepository.save(user);

        CustomUserDetails userDetails = new CustomUserDetails(saved);
        String token = jwtService.generateToken(userDetails);

        UserResponse userResponse = new UserResponse(
            saved.getId(),
            saved.getName(),
            saved.getEmail(),
            saved.getRole().name()
        );

        return new AuthResponse(token, "Bearer", userResponse);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
            request.getEmail(), request.getPassword()
        );
        Authentication authenticated = authenticationManager.authenticate(authToken);

        CustomUserDetails userDetails = (CustomUserDetails) authenticated.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        UserResponse userResponse = new UserResponse(
            userDetails.getUser().getId(),
            userDetails.getUser().getName(),
            userDetails.getUser().getEmail(),
            userDetails.getUser().getRole().name()
        );

        return new AuthResponse(token, "Bearer", userResponse);
    }
}