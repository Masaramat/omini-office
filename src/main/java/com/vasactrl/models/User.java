package com.vasactrl.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {
    @Id
    private Long id;

    @Column(value = "name")
    @NotBlank(message = "Name cannot be blank")
    @UniqueElements(message = "Name exists")
    private String name;

    @Column(value = "email")
    @NotBlank(message = "Email cannot be blank")
    @UniqueElements(message = "Email exists")
    @Email(message = "Email format incorrect")
    private String email;

    @Column(value = "role")
   private Role role = Role.USER;

    @Column(value = "department_id")
    private Long departmentId ;

    @Column(value = "branch_id")
    private Long branchId;

    @Column(value = "encrypted_password")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return true;
    }
}
