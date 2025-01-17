package com.furkan.spring_security_jwt.contoller;

import com.furkan.spring_security_jwt.dto.AuthRequest;
import com.furkan.spring_security_jwt.dto.CreateUserRequest;
import com.furkan.spring_security_jwt.model.User;
import com.furkan.spring_security_jwt.service.JwtService;
import com.furkan.spring_security_jwt.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService service;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public UserController(UserService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Hello World! this is FOLSDEV";
    }

    @PostMapping("/addNewUser")
    public User addUser(@RequestBody CreateUserRequest request) {
        return service.createUser(request);
    }

    //Bu tarz kısımları normalde ayrı bir service ve controllerda (AuthController..) açıp orada yapmalıyız
    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }

    @GetMapping("/user")
    public String getUserString() {
        return "This is USER!";
    }

    @GetMapping("/admin")
    public String getAdminString() {
        return "This is ADMIN!";
    }
}