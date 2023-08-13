package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.domain.dto.RegisterSuccessDto;
import hr.algebra.tabletopshop.domain.users.Role;
import hr.algebra.tabletopshop.domain.users.RoleEnum;
import hr.algebra.tabletopshop.domain.users.User;
import hr.algebra.tabletopshop.payload.MessageResponse;
import hr.algebra.tabletopshop.payload.UserInfoResponse;
import hr.algebra.tabletopshop.payload.UserLoginRequest;
import hr.algebra.tabletopshop.payload.UserRegisterRequest;
import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.repository.mongodb.RoleRepositoryMongo;
import hr.algebra.tabletopshop.repository.mongodb.UserRepositoryMongo;
import hr.algebra.tabletopshop.service.UserDetailsImpl;
import hr.algebra.tabletopshop.service.UserValidationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private UserValidationService userValidationService;
    private CustomSpringEventPublisher customSpringEventPublisher;
    
    private UserRepositoryMongo userRepositoryMongo;
    private AuthenticationManager authenticationManager;
    private RoleRepositoryMongo roleRepositoryMongo;
    private PasswordEncoder passwordEncoder;
    
    
    @GetMapping("/login.html")
    public String openLoginPage(Model model, @ModelAttribute RegisterSuccessDto userRegistered) {
        customSpringEventPublisher.publishCustomEvent("Anonymous authentication page opened!");
        model.addAttribute("loginRequest", new UserLoginRequest());
        model.addAttribute("registerRequest", new UserRegisterRequest());
//        model.addAttribute("user", new User());
        model.addAttribute("userRegistered", new RegisterSuccessDto(false));
        return "login";
    }
    
    @PostMapping("/newUser.html")
    public String saveNewUser(Model model, @ModelAttribute @Valid User user, @ModelAttribute RegisterSuccessDto userRegistered, BindingResult bindingResult) {
        model.addAttribute("user", user);
        
        String duplicateError = userValidationService.validateDuplicateUser(user, userRepositoryMongo.findAll());
        
        if(!duplicateError.isEmpty()) {
            ObjectError error = new ObjectError("registerError", duplicateError);
            bindingResult.addError(error);
        }
        
        if(bindingResult.hasErrors()){
            Objects.requireNonNull(userRegistered).setUserRegistered(false);
        } else {
            Objects.requireNonNull(userRegistered).setUserRegistered(true);
            userRepositoryMongo.save(user);
        }
        model.addAttribute("userRegistered", userRegistered);
        return "redirect:/auth/login.html";
    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(Model model,@ModelAttribute @Valid @RequestBody UserLoginRequest loginRequest) {
        model.addAttribute("loginRequest", loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok()
                .body(new UserInfoResponse(userDetails.getId(),
                                           userDetails.getUsername(),
                                           roles));
    }
    
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(Model model, @ModelAttribute @Valid @RequestBody UserRegisterRequest registerRequest) {
        model.addAttribute("registerRequest", registerRequest);
        
        if (userRepositoryMongo.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        
        // Create new user's account
        User user = new User(registerRequest.getUsername(),
                             passwordEncoder.encode(registerRequest.getPassword()));
        
        Set<String> strRoles = registerRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        
        if (strRoles == null) {
            Role userRole = roleRepositoryMongo.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepositoryMongo.findByName(RoleEnum.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepositoryMongo.findByName(RoleEnum.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        
        Integer newItemId = ((Number)userRepositoryMongo.count()).intValue() + 1;
        user.setId(newItemId);
        user.setRoles(roles);
        userRepositoryMongo.save(user);
        
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    
}
