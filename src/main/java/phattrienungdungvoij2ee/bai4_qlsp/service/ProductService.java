package phattrienungdungvoij2ee.bai4_qlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import phattrienungdungvoij2ee.bai4_qlsp.model.Product;
import phattrienungdungvoij2ee.bai4_qlsp.repository.ProductRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product get(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void add(Product newProduct) {
        // Không cần tính maxId nữa vì MySQL tự tăng ID
        productRepository.save(newProduct);
    }

    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setName(editProduct.getName());
            find.setPrice(editProduct.getPrice());
            find.setCategory(editProduct.getCategory());
            if (editProduct.getImage() != null) {
                find.setImage(editProduct.getImage());
            }
            productRepository.save(find); // Lưu thay đổi xuống MySQL
        }
    }

    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        if (imageProduct.isEmpty()) return;

        String contentType = imageProduct.getContentType();
        if (contentType != null && !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
        }

        try {
            // Lưu ý: Trong Spring Boot, nên lưu vào folder 'src/main/resources/static/images'
            // để ảnh có thể hiển thị được ngay trên trình duyệt
            Path dirImages = Paths.get("src/main/resources/static/images");
            if (!Files.exists(dirImages)) {
                Files.createDirectories(dirImages);
            }
            
            String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
            Path pathFileUpload = dirImages.resolve(newFileName);
            Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
            
            newProduct.setImage(newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDir, String keyword, Integer categoryId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
                    Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        if (categoryId != null && categoryId > 0) {
            if (keyword != null && !keyword.isEmpty()) {
                return productRepository.findByNameContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable);
            }
            return productRepository.findByCategoryId(categoryId, pageable);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }
        return productRepository.findAll(pageable);
    }
}