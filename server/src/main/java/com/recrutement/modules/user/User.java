package com.recrutement.modules.user;

import com.recrutement.modules.base.BaseEntity;
import com.recrutement.modules.documents.Document;
import com.recrutement.modules.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "username")
    @NotBlank(message = "Username is required")
    private String username;
    @Column(name = "email")
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @Column(name = "password")
    @NotBlank(message = "password is required")
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    @Column(name = "is_activated", columnDefinition = "boolean default false")
    private Boolean activated = false;
    @Column(name = "activated_at")
    private Date activatedAt;
    @Column(name = "phone")
    private String phone;
    @Column(name = "mfa_enabled", columnDefinition = "boolean default false")
    private Boolean MFAEnabled = false;
    @OneToOne(orphanRemoval = true)
    private Document avatar;
    @OneToOne(orphanRemoval = true)
    private Document cover;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
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
        return this.isEnabled;
    }
}
