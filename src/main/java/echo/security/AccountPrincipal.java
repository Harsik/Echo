package echo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import echo.model.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email; // email 넣을까 말까 고민중 어차피 userdetails에 있는 username이 id가 되어 email을 대체하게 될텐데

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public AccountPrincipal(Long id, String username, String email, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    
    public static AccountPrincipal create(Account account) { // 생성된 유저 객체들을 map으로 변환
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new AccountPrincipal(account.getId(), account.getEmail(), account.getEmail(), account.getPassword(), authorities);
    }

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    //     List<GrantedAuthority> authorities = new ArrayList<>();
    //     authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    //     return authorities;
    // }
     
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AccountPrincipal that = (AccountPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}