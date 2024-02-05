package com.project.tklembackend.model;

import com.project.tklembackend.repository.ParentRepository;
import com.project.tklembackend.repository.ReceptorRepository;
import com.project.tklembackend.repository.RecieverRepository;
import com.project.tklembackend.repository.UserEntityRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public  class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique=true)
    @Email
    private String email;
    private String password;
    @OneToOne
    private Role role;
    @OneToMany
    private List<Parent> parentList = new ArrayList<>();
    @OneToMany
    private List<Receptor> receptorList = new ArrayList<>();
    @OneToMany
    private List<Reciever> recieverList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(role);
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
