package phattrienungdungvoij2ee.bai4_qlsp.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Giống trong slide: dùng login_name thay vì username
    @Column(name = "login_name", nullable = false, unique = true)
    private String loginName;

    @Column(nullable = false)
    private String password;

    // Tạo bảng trung gian account_role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "account_role",
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}