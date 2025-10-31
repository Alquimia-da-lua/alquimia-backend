package com.alquimia.backend.model;

import com.alquimia.backend.enums.RoleUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdUsuario;

    @Column(nullable = false)
    private String nmUsuario;

    @Column(unique = true, nullable = false)
    private String emailUsuario;

    @Column(nullable = false)
    private String senhaUsuario;

    @Enumerated(EnumType.STRING)
    private RoleUsuario roleUsuario;

    @Column(nullable = false)
    private String nuTelefone;

    @Column(columnDefinition = "boolean default true")
    private boolean isAtivo;

    @Column(unique = true)
    private String nuCpf;

    // metodos user details
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roleUsuario == RoleUsuario.FUNCIONARIO) {
            return List.of(new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }
    }

    @Override
    public String getPassword() {
        return senhaUsuario;
    }

    @Override
    public String getUsername() {
        return emailUsuario;
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
        return isAtivo;
    }
}