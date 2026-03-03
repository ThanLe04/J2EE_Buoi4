package phattrienungdungvoij2ee.bai4_qlsp.controller;

import phattrienungdungvoij2ee.bai4_qlsp.model.Category;
import phattrienungdungvoij2ee.bai4_qlsp.model.Product;
import phattrienungdungvoij2ee.bai4_qlsp.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
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

    // Bổ sung thêm tính năng xóa nếu bạn muốn hoàn thiện CRUD
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}