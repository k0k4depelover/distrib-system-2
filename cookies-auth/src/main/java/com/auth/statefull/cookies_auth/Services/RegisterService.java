package com.auth.statefull.cookies_auth.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.statefull.cookies_auth.Config.Exceptions.InvalidRoleException;
import com.auth.statefull.cookies_auth.Config.Exceptions.RoleNotFoundException;
import com.auth.statefull.cookies_auth.Config.Exceptions.UsernameTakenException;
import com.auth.statefull.cookies_auth.Dto.RegisterRequest;
import com.auth.statefull.cookies_auth.Entity.Role;
import com.auth.statefull.cookies_auth.Entity.User;
import com.auth.statefull.cookies_auth.Repository.RoleRepository;
import com.auth.statefull.cookies_auth.Repository.UserRepository;

@Service
public class RegisterService implements IRegisterService {

    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    public final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        Optional<User> userOptional = userRepository.findByUsername(registerRequest.getUsername());

        if (userOptional.isPresent()) {
            throw new UsernameTakenException("El nombre de usuario no está disponible");
        }

        List<String> rolesUserRequest = registerRequest.getRoles();
        List<Role> rolesDb = new ArrayList<>();

        for (String roleRequest : rolesUserRequest) {
            if (roleRequest.isBlank()) {
                throw new InvalidRoleException("Petición de rol inválida");
            }
            Role role = roleRepository.findByName(roleRequest)
                    .orElseThrow(() -> new RoleNotFoundException("El rol ingresado no existe: " + roleRequest));
            rolesDb.add(role);
        }

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        User userDB = new User();
        userDB.setUsername(registerRequest.getUsername());
        userDB.setPassword(hashedPassword);
        userDB.setRoles(rolesDb);
        userDB.setEnabled(true);

        return userRepository.save(userDB);
    }
}