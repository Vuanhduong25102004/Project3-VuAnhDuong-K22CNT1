package com.service;

import com.model.VadUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class VadCustomUserDetails implements UserDetails {

    private final VadUser user;

    public VadCustomUserDetails(VadUser user) {
        this.user = user;
    }

    public String getFullname() {
        return user.getFullname();
    }

    // Các phương thức bắt buộc của UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	String role = "ROLE_" + user.getVadrole().name().toUpperCase();
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // hoặc một thuộc tính khác bạn muốn dùng làm username
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
