package com.rest_api.fs14backend.user;

import com.rest_api.fs14backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    //System.out.println("we are inside users");
    return userRepository.findAll();
  }

  @PostMapping("/signin")
  public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception{
    User user = userRepository.findByUsername(authRequest.getUsername());
    if (user == null) {
      //System.out.println(HttpStatus.NOT_FOUND)
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found! Please sign up!");
    } else {
      if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong Username or Password!");
      }
      try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        return ResponseEntity.ok(jwtUtils.generateToken(user));
      } catch (Exception e) {
        return new ResponseEntity<>("Failed to sign in: " + e.getMessage(), HttpStatus.FORBIDDEN);
      }

    }

  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody User user) throws Exception{

    if(userRepository.findByUsername(user.getUsername())!=null){
      // return HttpStatus.CONFLICT.toString();
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Username " + user.getUsername() + " already exists! Choose a different username");
    } else {
      try {
        User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), User.Role.USER); // change to ADMIN for admin
        userRepository.save(newUser);
        // return HttpStatus.CREATED.toString();
        return ResponseEntity.ok("Registered succesfully! Please signin to continue.");
      } catch (Exception e) {
        return new ResponseEntity<>("Failed to create user: " + e.getMessage(), HttpStatus.FORBIDDEN);
      }
    }

    //return newUser;
  }

}
