package hr.algebra.tabletopshop.controllers;

import com.google.gson.Gson;
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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private CustomSpringEventPublisher customSpringEventPublisher;
    
    private UserRepositoryMongo userRepositoryMongo;
    private AuthenticationManager authenticationManager;
    private RoleRepositoryMongo roleRepositoryMongo;
    private PasswordEncoder passwordEncoder;
    
    
    @GetMapping("/login.html")
    public String openLoginPage(Model model) {
        customSpringEventPublisher.publishCustomEvent("Anonymous authentication page opened!");
        model.addAttribute("loginRequest", new UserLoginRequest());
        model.addAttribute("registerRequest", new UserRegisterRequest());
        model.addAttribute("userRegistered", new RegisterSuccessDto(false));
        return "login";
    }

//    @PostMapping("/newUser.html")
//    public String saveNewUser(Model model, @ModelAttribute @Valid User user, @ModelAttribute RegisterSuccessDto userRegistered, BindingResult bindingResult) {
//        model.addAttribute("user", user);
//
//        String duplicateError = userValidationService.validateDuplicateUser(user, userRepositoryMongo.findAll());
//
//        if(!duplicateError.isEmpty()) {
//            ObjectError error = new ObjectError("registerError", duplicateError);
//            bindingResult.addError(error);
//        }
//
//        if(bindingResult.hasErrors()){
//            Objects.requireNonNull(userRegistered).setUserRegistered(false);
//        } else {
//            Objects.requireNonNull(userRegistered).setUserRegistered(true);
//            userRepositoryMongo.save(user);
//        }
//        model.addAttribute("userRegistered", userRegistered);
//        return "redirect:/auth/login.html";
//    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(Model model, @ModelAttribute @Valid @RequestBody UserLoginRequest loginRequest) {
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
    public ModelAndView registerUser(@ModelAttribute @Valid @RequestBody UserRegisterRequest registerRequest) {
        ModelAndView mav = new ModelAndView();
        RegisterSuccessDto registerSuccessDto = new RegisterSuccessDto(false);
        
        if (userRepositoryMongo.existsByUsername(registerRequest.getUsername())) {
            registerSuccessDto.setSuccess(false);
            MessageResponse msg = new MessageResponse("Username is already taken");
            ResponseEntity<MessageResponse> res = ResponseEntity.badRequest().body(msg);
            
            mav.addObject("registerRequest", registerRequest);
            mav.addObject("response", res);
            mav.addObject("errorMessage", msg.getMessage());
            mav.addObject("userRegistered", registerSuccessDto.getSuccess());
            
            System.out.println("1 register req: " + registerRequest);
            System.out.println("1 res: " + res);
            System.out.println("1 res.body: " + res.getBody());
            System.out.println("1 msg.getmsg: " + msg.getMessage());
            System.out.println("1 userRegistered: " + registerSuccessDto.getSuccess());
            
            mav.setViewName("redirect:/auth/login.html");
            
            return mav;
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
        
        Integer newUserId = ((Number) userRepositoryMongo.count()).intValue() + 1;
        user.setId(newUserId);
        user.setRoles(roles);
        userRepositoryMongo.save(user);
        
        registerSuccessDto.setSuccess(true);
        
        MessageResponse messageResponse = new MessageResponse("User registered successfully");
        ResponseEntity<MessageResponse> res = ResponseEntity.ok(messageResponse);
        mav.addObject("response", res);
        mav.addObject("userRegistered", registerSuccessDto.getSuccess());
        mav.addObject("errorMessage", messageResponse.getMessage());
        mav.setViewName("redirect:/auth/login.html");
        
        System.out.println("2 register req: " + registerRequest);
        System.out.println("2 res: " + res);
        System.out.println("2 res.body: " + res.getBody());
        System.out.println("2 msg.getmsg: " + messageResponse.getMessage());
        System.out.println("2 userRegistered: " + registerSuccessDto.getSuccess());
        
        return mav;
    }
    
}
