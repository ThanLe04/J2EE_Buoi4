package phattrienungdungvoij2ee.bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phattrienungdungvoij2ee.bai4_qlsp.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // JpaRepository<Kiểu_Entity, Kiểu_Dữ_Liệu_Của_Id>
    // Integer tương ứng với kiểu 'int id' trong class Category của bạn
}