package com.ufc.easydesk.service;

import com.ufc.easydesk.model.Role;
import com.ufc.easydesk.model.Usuario;
import com.ufc.easydesk.model.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MyUserDetailsTest {

    private Usuario usuario;
    private MyUserDetails myUserDetails;

    @BeforeEach
    void setUp() {
        usuario = Mockito.mock(Usuario.class);
        Mockito.when(usuario.getEmail()).thenReturn("test@example.com");
        Mockito.when(usuario.getSenha()).thenReturn("password");
        Role role = new Role();
        role.setNome(RoleName.ROLE_ADMIN);
        Mockito.when(usuario.getRoles()).thenReturn(Set.of(role));
        myUserDetails = new MyUserDetails(usuario);
    }

    @Test
    void getAuthorities() {
        Set<String> authorities = myUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        assertTrue(authorities.contains("ROLE_ADMIN"));
    }

    @Test
    void getPassword() {
        assertEquals("password", myUserDetails.getPassword());
    }

    @Test
    void getUsername() {
        assertEquals("test@example.com", myUserDetails.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(myUserDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(myUserDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(myUserDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(myUserDetails.isEnabled());
    }
}