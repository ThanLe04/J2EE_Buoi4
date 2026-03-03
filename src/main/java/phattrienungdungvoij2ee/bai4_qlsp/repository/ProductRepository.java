package phattrienungdungvoij2ee.bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phattrienungdungvoij2ee.bai4_qlsp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Bạn có thể thêm các hàm tìm kiếm tùy chỉnh ở đây nếu cần
    // Ví dụ: List<Product> findByNameContaining(String name);
}