package phattrienungdungvoij2ee.bai4_qlsp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Khai báo chuẩn mã hóa Bcrypt như trong slide
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cấu hình các đường dẫn và form đăng nhập
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Cho phép ai cũng vào được trang chủ và css/js/images
                .requestMatchers("/", "/images/**", "/css/**").permitAll()
                
                // Các thao tác Thêm/Sửa/Xóa chỉ dành cho ADMIN
                .requestMatchers("/products/create", "/products/edit/**", "/products/delete/**").hasRole("ADMIN")
                
                // Xem danh sách thì USER hay ADMIN đều được
                .requestMatchers("/products").hasAnyRole("USER", "ADMIN")
                
                // Các đường dẫn khác bắt buộc phải đăng nhập
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Sử dụng form đăng nhập mặc định của Spring
                .defaultSuccessUrl("/products", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}