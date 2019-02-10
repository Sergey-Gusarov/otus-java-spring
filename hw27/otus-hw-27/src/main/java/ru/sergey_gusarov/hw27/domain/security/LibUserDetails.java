package ru.sergey_gusarov.hw27.domain.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class LibUserDetails implements UserDetails {

    private LibUser libUser;

    public LibUserDetails(LibUser libUser) {
        this.libUser = libUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPassword() {
        return libUser.getPassword();
    }

    @Override
    public String getUsername() {
        return libUser.getUserName();
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
