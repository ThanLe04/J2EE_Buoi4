package phattrienungdungvoij2ee.bai4_qlsp.controller;

import phattrienungdungvoij2ee.bai4_qlsp.model.Product;
import phattrienungdungvoij2ee.bai4_qlsp.service.*;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;

    @GetMapping("")
    public String viewProducts(Model model,
                            @RequestParam(value = "page", defaultValue = "1") int pageNo,
                            @RequestParam(value = "sortField", defaultValue = "name") String sortField,
                            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        
        int pageSize = 5;
        Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir, keyword, categoryId);
        
        model.addAttribute("listProducts", page.getContent());
        model.addAttribute("categories", categoryService.getAll()); // Để hiển thị danh sách trong Dropdown
        model.addAttribute("categoryId", categoryId); // Để giữ trạng thái đã chọn
        
        // Các model attribute khác (currentPage, totalPages,...) giữ nguyên như cũ
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "product/products";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("product") Product newProduct, 
                         BindingResult result,
                         @RequestParam("imageProduct") MultipartFile imageProduct,
                         Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        
        // Xử lý lưu file hình ảnh
        if (!imageProduct.isEmpty()) {
            productService.updateImage(newProduct, imageProduct);
        }
        
        // Lưu sản phẩm vào DB thông qua Service
        productService.add(newProduct);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("product") Product editProduct, 
                       BindingResult result,
                       @RequestParam("imageProduct") MultipartFile imageProduct, 
                       Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        // Nếu có tải ảnh mới lên thì cập nhật, không thì giữ nguyên ảnh cũ
        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        } else {
            // Giữ lại tên ảnh cũ từ database nếu không thay đổi
            Product oldProduct = productService.get(editProduct.getId());
            if (oldProduct != null) {
                editProduct.setImage(oldProduct.getImage());
            }
        }

        productService.update(editProduct);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
    
}