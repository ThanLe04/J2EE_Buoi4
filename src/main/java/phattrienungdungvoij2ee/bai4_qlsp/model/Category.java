package phattrienungdungvoij2ee.bai4_qlsp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor
@Entity // Đánh dấu đây là một thực thể JPA
@Table(name = "categories") // Tên bảng trong MySQL
public class Category {
    
    @Id // Đánh dấu là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng (Auto Increment)
    private int id;
    
    @NotBlank(message = "Tên danh mục không được để trống")
    @Column(nullable = false)
    private String name;
}