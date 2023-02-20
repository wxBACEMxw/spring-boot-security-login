package com.spring.security.login.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.spring.security.login.models.ERole;
import com.spring.security.login.models.Role;
import com.spring.security.login.models.User;
import com.spring.security.login.payload.request.LoginRequest;
import com.spring.security.login.payload.request.SignupRequest;
import com.spring.security.login.payload.response.MessageResponse;
import com.spring.security.login.payload.response.UserInfoResponse;
import com.spring.security.login.repository.RoleRepository;
import com.spring.security.login.repository.UserRepository;
import com.spring.security.login.security.jwt.JwtUtils;
import org.slf4j.Logger;

import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.login.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(),
                                   userDetails.getUsername(),
                                   userDetails.getEmail(),
                                    userDetails.getFullName(),
                                    userDetails.getCity(),
                                    userDetails.getTown(),
                                    userDetails.getStreetName(),
                                    userDetails.getPostAddress(),
                                    roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()),
                         signUpRequest.getFullName(),
                         signUpRequest.getCity(),
                         signUpRequest.getTown(),
                         signUpRequest.getStreetName(),
                         signUpRequest.getPostAddress());

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
      strRoles.forEach(role -> {

       switch (role){
         case "FIAgent":
           Role FIAgentRole = roleRepository.findByName(ERole.ROLE_FIAGENT)
                   .orElseThrow(()->new RuntimeException("Error: FIAgent not found"));
           roles.add(FIAgentRole);
           break;
         case "customer":
           Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                   .orElseThrow(()->new RuntimeException("Error: customer Role not found"));
           roles.add(customerRole);
           break;
       }
      });

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok().body(
           user
    );
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }
}
