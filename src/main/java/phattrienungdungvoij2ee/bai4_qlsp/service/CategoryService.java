package phattrienungdungvoij2ee.bai4_qlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phattrienungdungvoij2ee.bai4_qlsp.model.Category;
import phattrienungdungvoij2ee.bai4_qlsp.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll(); // Lấy tất cả từ MySQL
    }

    public Category get(int id) {
        return categoryRepository.findById(id).orElse(null); // Tìm theo ID trong MySQL
    }
}