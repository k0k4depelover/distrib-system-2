package com.auth.statefull.cookies_auth.Services;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.statefull.cookies_auth.Config.Exceptions.IllegalLoginException;
import com.auth.statefull.cookies_auth.Dto.LoginRequest;
import com.auth.statefull.cookies_auth.Entity.User;

import com.auth.statefull.cookies_auth.Repository.UserRepository;

@Service 
public class LoginService implements ILoginService {

    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }
    @Override
    public User login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if(user.isEmpty()){
            throw new IllegalLoginException("El usuario o contraseñas no coinciden");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())){
            throw new IllegalLoginException("El usuario o contraseñas no coinciden");
        }
        return user.get();
    }
    
}
