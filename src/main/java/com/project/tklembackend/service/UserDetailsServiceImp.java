package com.project.tklembackend.service;

import com.project.tklembackend.model.User;
import com.project.tklembackend.model.UserEntity;
import com.project.tklembackend.repository.ParentRepository;
import com.project.tklembackend.repository.ReceptorRepository;
import com.project.tklembackend.repository.RecieverRepository;
import com.project.tklembackend.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;
    private final ReceptorRepository receptorRepository;
    private final ParentRepository parentRepository;
    private final RecieverRepository recieverRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            return userEntityRepository.findByEmail(username).get();
        }catch (UsernameNotFoundException | NoSuchElementException ex){
            throw new UsernameNotFoundException("Couldn't retrieve the user");
        }
    }

    public User getUserByRole(UserEntity userEntity){
        String email = userEntity.getEmail();
        return switch (userEntity.getRole().getRoleName().name()) {
            case "RECEPTOR" -> receptorRepository.findByEmail(email).get();
            case "RECIEVER" -> recieverRepository.findByEmail(email).get();
            case "PARENT" -> parentRepository.findByEmail(email).get();
            default -> null;
        };
    }

}
