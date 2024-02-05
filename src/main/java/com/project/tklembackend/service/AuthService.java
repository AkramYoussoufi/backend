package com.project.tklembackend.service;


import com.project.tklembackend.dto.RegisterRequest;
import com.project.tklembackend.dto.SignupRequest;
import com.project.tklembackend.model.Role;
import com.project.tklembackend.model.User;
import com.project.tklembackend.model.UserEntity;
import com.project.tklembackend.repository.RoleRepository;
import com.project.tklembackend.repository.UserEntityRepository;
import jakarta.mail.AuthenticationFailedException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.management.InstanceAlreadyExistsException;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
@AllArgsConstructor
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserEntityRepository userEntityRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JWTService jwtService;

    @Transactional
    public void signIn(RegisterRequest registerRequest) throws InstanceAlreadyExistsException {
        UserEntity user = new UserEntity();
            if(userEntityRepository.findByEmail(registerRequest.getEmail()).isEmpty()){
                user.setName(registerRequest.getName());
                user.setEmail(registerRequest.getEmail());
                user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
                Role role = roleRepository.findById(4L).orElseThrow(()->new NoSuchElementException("Role doesn't exist"));
                user.setRole(role);

                userEntityRepository.save(user);
            }else{
                throw new InstanceAlreadyExistsException("the user you trying to save already exist: "+registerRequest.getEmail());
            }
    }

    public String authenticateUser(SignupRequest signupRequest) throws AuthenticationFailedException {
        UserEntity user = userEntityRepository.findByEmail(signupRequest.getEmail()).orElseThrow(
                    () -> new NoSuchElementException("User not found for email: " + signupRequest.getEmail())
        );
        UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(user.getUsername());
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, signupRequest.getPassword(), userDetails.getAuthorities());
        Authentication authentication = authenticationProvider.authenticate(authToken);
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getEmail(),user.getRole().getRoleName());
        }
        throw new AuthenticationFailedException("authentication failed");
    }

    public Optional<User> getCurrentAuthenticatedUser() {
        return Optional.of((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
