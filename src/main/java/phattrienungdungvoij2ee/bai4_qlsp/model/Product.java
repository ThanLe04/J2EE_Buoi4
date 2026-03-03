package phattrienungdungvoij2ee.bai4_qlsp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor
@Entity // Đánh dấu đây là một thực thể JPA
@Table(name = "products") // Tên bảng trong MySQL
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Column(nullable = false)
    private String name;

    @Length(min = 0, max = 200, message = "Tên hình ảnh không quá 200 kí tự")
    private String image;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1, message = "Giá sản phẩm không được nhỏ hơn 1")
    @Max(value = 9999999, message = "Giá sản phẩm không được lớn hơn 9999999")
    private long price;

    @ManyToOne // Thiết lập mối quan hệ N-1 với Category
    @JoinColumn(name = "category_id", nullable = false) // Tên cột khóa ngoại trong bảng products
    private Category category;
}