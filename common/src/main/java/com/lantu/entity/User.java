package com.lantu.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 用户
 */
@Data
public class User implements UserDetails {
    private Long id;
    private String name;
    private String phone;
    private String telephone;
    private String address;
    private boolean enabled;
    private String username;
    private String password;
    private String userface;
    private String remark;

    // 用户的权限集合
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //
    @Override
    public String getPassword() {
        return password;
    }
    //
    @Override
    public String getUsername() {
        return username;
    }

    // 账户是否没有过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否没有被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否没有过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 状态
    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
