package com.auth.statefull.cookies_auth.Controllers;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.statefull.cookies_auth.Dto.LoginRequest;
import com.auth.statefull.cookies_auth.Dto.RegisterRequest;
import com.auth.statefull.cookies_auth.Dto.UserSessionDto;
import com.auth.statefull.cookies_auth.Entity.Role;
import com.auth.statefull.cookies_auth.Entity.User;
import com.auth.statefull.cookies_auth.Repository.RoleRepository;
import com.auth.statefull.cookies_auth.Repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/auth")
public class AuthController {
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    public final PasswordEncoder passwordEncoder;
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginForCookie(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if(user.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }


        String userRoles = user.get().getRoles().stream().map(Role::getName).collect(Collectors.joining(","));

        session.setAttribute("USER_ID", user.get().getId());
        session.setAttribute("USERNAME", user.get().getUsername());
        session.setAttribute("ROLE", userRoles);
        
        return ResponseEntity.status(200).body("Login exitoso. Sesión creada.");
    }
    
    @PutMapping("/logout")
    public ResponseEntity<?> logoutController(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("Sesión cerrada exitosamente.");
       
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateController(HttpSession session, HttpServletRequest request) {
        Long userId = (Long) session.getAttribute("USER_ID");

        if(userId == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cookie de sesion invalida");
        }

        UserSessionDto user = new UserSessionDto(
            userId,
            (String) session.getAttribute("USERNAME"),
            (String) session.getAttribute("ROLE")
        );
        
        String hostname;

        try{
            hostname = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e){
            hostname= "unknown";
        }
        return ResponseEntity.ok(user);

    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerController(@RequestBody RegisterRequest registerRequest, HttpSession session) {
        Optional<User> userOptional =  userRepository.findByUsername(registerRequest.getUsername());

        if(userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario no està disponible");

        }

        List<Role> rolesUserRequest = registerRequest.getRoles();
        List<Role> rolesDb= new ArrayList<>();

        for(Role roleRequest : rolesUserRequest){
            Optional<Role> roleOptional = roleRepository.findById(roleRequest.getId());
            if(roleOptional.isEmpty()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El rol ingresado no existe...");

            }
            rolesDb.add(roleOptional.get());
        }

        
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        User userDB= new User();
        userDB.setUsername(registerRequest.getUsername());
        userDB.setPassword(hashedPassword);
        userDB.setRoles(rolesDb);
        userDB.setEnabled(true);

        userRepository.save(userDB);

        return ResponseEntity.ok(userDB);

    }
    

}

