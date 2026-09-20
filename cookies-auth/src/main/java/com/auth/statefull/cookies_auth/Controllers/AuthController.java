package com.auth.statefull.cookies_auth.Controllers;

import java.net.InetAddress;
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
import com.auth.statefull.cookies_auth.Services.IRegisterService;
import com.auth.statefull.cookies_auth.Services.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/auth")
public class AuthController {
    public final PasswordEncoder passwordEncoder;
    public final LoginService loginService;
    public final IRegisterService registerService;
    public AuthController(PasswordEncoder passwordEncoder, IRegisterService registerService, LoginService loginService) {
        this.passwordEncoder = passwordEncoder;
        this.registerService = registerService;
        this.loginService= loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginForCookie(@RequestBody LoginRequest loginRequest, HttpSession session) {

        User user= loginService.login(loginRequest);

        String userRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));

        session.setAttribute("USER_ID", user.getId());
        session.setAttribute("USERNAME", user.getUsername());
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
    
    @Valid 
    @PostMapping("/register")
    public ResponseEntity<?> registerController(@Valid @RequestBody RegisterRequest registerRequest, HttpSession session) {
        User user = registerService.registerUser(registerRequest);
        return ResponseEntity.ok(user);
    }
    

}

