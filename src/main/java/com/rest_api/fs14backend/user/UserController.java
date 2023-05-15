package com.rest_api.fs14backend.user;

import com.rest_api.fs14backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class UserController {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtUtils jwtUtils;

  @GetMapping("/users")
  public List<User> findAll() {
    System.out.println("we are inside users");
    return userRepository.findAll();
  }

  @PostMapping("/signin")
  public String login(@RequestBody AuthRequest authRequest){

    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authRequest.getUsername(),
        authRequest.getPassword()
      )
    );
    
    
    User user = userRepository.findByUsername(authRequest.getUsername());
    //System.out.println("found logged in user : " +user);
    return jwtUtils.generateToken(user);
    
  }

  @PostMapping("/signup")
  public User signup(@RequestBody User user) {

    User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), User.Role.ADMIN);
    userRepository.save(newUser);

    return newUser;
  }

}
