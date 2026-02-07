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
    public String create(@Valid Product newProduct, BindingResult result,
                         @RequestParam("imageProduct") MultipartFile imageProduct,
                         @RequestParam("category.id") int categoryId,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        
        productService.updateImage(newProduct, imageProduct);
        
        Category selectedCategory = categoryService.get(categoryId);
        newProduct.setCategory(selectedCategory);
        
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
    public String edit(@Valid Product editProduct, 
                       BindingResult result,
                       @RequestParam("imageProduct") MultipartFile imageProduct, 
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        }

        productService.update(editProduct); // Cập nhật thông tin sản phẩm
        return "redirect:/products";
    }
}