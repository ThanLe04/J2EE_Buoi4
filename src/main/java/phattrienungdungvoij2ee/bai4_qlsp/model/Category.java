package phattrienungdungvoij2ee.bai4_qlsp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Category {
    private int id;
    
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
}