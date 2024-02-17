package com.project.tklembackend.service.admin;

import com.project.tklembackend.dto.UserDTO;
import com.project.tklembackend.model.*;
import com.project.tklembackend.repository.RoleRepository;
import com.project.tklembackend.repository.UserEntityRepository;
import com.project.tklembackend.service.AuthService;
import com.project.tklembackend.service.GlobalService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class AdminService {

    private final UserEntityRepository userEntityRepository;
    private final GlobalService globalService;
    private final AuthService authService;


    @Transactional
    public void addAdmin(UserDTO userDTO) {
        UserEntity user = globalService.adminDTOtoAdmin(userDTO);
        userEntityRepository.save(user);
    }

    @Transactional
    public void editAdmin(UserDTO userDTO) {
        UserEntity user = globalService.adminDTOtoAdmin(userDTO);
        userEntityRepository.save(user);
    }

    public void deleteAdmin(Long id) {
        if(Objects.equals(userEntityRepository.findByEmail("admin@admin.admin").get().getId(), id) || Objects.equals(authService.getCurrentAuthenticatedUser().get().getId(), id)){
            throw new RequestRejectedException("You cannot delete your account or main Admin account");
        }
        userEntityRepository.deleteById(id);
    }



    public List<UserDTO> getAllAdmin() {
        return userEntityRepository.findAll().stream().filter(
                userEntity -> {
                    return userEntity.getRole().getRoleName() == Roles.ADMIN;
                }
        ).map(globalService::adminToAdminDTO).toList();
    }
}
