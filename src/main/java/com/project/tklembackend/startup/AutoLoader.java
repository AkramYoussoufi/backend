package com.project.tklembackend.startup;


import com.project.tklembackend.model.Role;
import com.project.tklembackend.model.Roles;
import com.project.tklembackend.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AutoLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.count() == 0){
            for(Roles r : Roles.values()){
                Role role = new Role();
                role.setRoleName(r);
                roleRepository.save(role);
            }
        }
    }
}
