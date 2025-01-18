package com.findar.bookstore.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.findar.bookstore.config.security.CustomerUserDetails;
import com.findar.bookstore.enums.Role;
import com.findar.bookstore.model.audith.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "root_users")
public class Users extends AuditEntity implements CustomerUserDetails {


    @Column( nullable = false, unique = true)
    private String email;

    @Column( unique = true)
    private String userName;

    @JsonIgnore
    private String password;


    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role  role;

    @Getter
    private Boolean  twoFa =false;

    private Boolean active= true;
  //  @JsonIgnoreProperties(value = {"user", "borrows"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Borrowed> borrows = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {

        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getRole() {
        return role.toString();
    }
}
