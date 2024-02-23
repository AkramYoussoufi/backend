package com.project.tklembackend.service;

import com.project.tklembackend.dto.UserDTO;
import com.project.tklembackend.model.Role;
import com.project.tklembackend.model.UserEntity;
import com.project.tklembackend.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class GlobalService {
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity adminDTOtoAdmin(UserDTO requestDto){
        UserEntity admin = new UserEntity();
        if(requestDto.getId() != null){
            admin.setId(requestDto.getId());
        }
        admin.setEmail(requestDto.getEmail());
        Role role = roleRepository.findById(1L).orElseThrow(()->new NoSuchElementException("role admin doesn't exist something went wrong with the autoloader"));
        admin.setRole(role);
        String password = bCryptPasswordEncoder.encode(requestDto.getPassword());
        admin.setPassword(password);
        admin.setEnabled(requestDto.getStatus());

        return admin;
    }

    public UserDTO adminToAdminDTO(UserEntity user){
        UserDTO request = new UserDTO();
        request.setId(user.getId());
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setStatus(user.getEnabled());

        return request;
    }

    public Map<String,String> responseBuilder(String message){
        Map<String,String> response = new HashMap<>();
        response.put("message",message);
        return response;
    }
}
