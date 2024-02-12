package com.project.tklembackend.startup;


import com.project.tklembackend.model.Role;
import com.project.tklembackend.model.Roles;
import com.project.tklembackend.model.UserEntity;
import com.project.tklembackend.repository.RoleRepository;
import com.project.tklembackend.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AutoLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.count() == 0){
            for(Roles r : Roles.values()){
                Role role = new Role();
                role.setRoleName(r);
                roleRepository.save(role);
            }
        }
        if(!userEntityRepository.findByEmail("admin@admin.admin").isPresent()){
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail("admin@admin.admin");
            userEntity.setPassword(bCryptPasswordEncoder.encode("admin1234"));
            userEntity.setRole(roleRepository.findById(1L).get());
            userEntity.setEnabled(true);
            userEntityRepository.save(userEntity);
        }
    }
}
