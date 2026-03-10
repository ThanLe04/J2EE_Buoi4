package phattrienungdungvoij2ee.bai4_qlsp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import phattrienungdungvoij2ee.bai4_qlsp.model.Account;
import phattrienungdungvoij2ee.bai4_qlsp.repository.AccountRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm user trong database theo login_name
        Account account = accountRepository.findByLoginName(username);
        
        if (account == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản: " + username);
        }

        // Chuyển đổi Set<Role> của bạn thành tập hợp các quyền (GrantedAuthority) của Spring
        Set<GrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // Trả về đối tượng User của Spring Security
        return new org.springframework.security.core.userdetails.User(
                account.getLoginName(),
                account.getPassword(),
                authorities
        );
    }
}