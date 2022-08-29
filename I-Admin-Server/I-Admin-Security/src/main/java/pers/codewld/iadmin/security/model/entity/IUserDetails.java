package pers.codewld.iadmin.security.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 用户信息 实体类
 * <p>在UserDetails之上增加了id、roleIDList
 * <p>用户实体类应该继承此类
 */
@Getter
@Setter
public class IUserDetails implements UserDetails {

    private String id;

    private String username;

    private String password;

    private List<String> roleIdList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
