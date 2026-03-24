package phattrienungdungvoij2ee.bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phattrienungdungvoij2ee.bai4_qlsp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndCategoryId(String name, Integer categoryId, Pageable pageable);
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
}