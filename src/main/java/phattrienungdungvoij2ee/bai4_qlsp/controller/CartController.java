package phattrienungdungvoij2ee.bai4_qlsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import phattrienungdungvoij2ee.bai4_qlsp.model.*;
import phattrienungdungvoij2ee.bai4_qlsp.service.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired private CartService cartService;
    @Autowired private ProductService productService;
    @Autowired private OrderService orderService;

    // Xem giỏ hàng
    @GetMapping("")
    public String viewCart(Model model) {
        model.addAttribute("CART_ITEMS", cartService.getItems());
        model.addAttribute("TOTAL", cartService.getAmount());
        return "cart/index"; // Trả về trang hiển thị giỏ hàng
    }

    // Thêm sản phẩm vào giỏ
    @GetMapping("/add/{id}")
    public String addCart(@PathVariable int id) {
        Product product = productService.get(id);
        if (product != null) {
            CartItem item = new CartItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                1, // Số lượng mặc định là 1
                product.getImage()
            );
            cartService.add(item);
        }
        return "redirect:/cart"; // Chuyển hướng về trang giỏ hàng
    }

    // Xóa sản phẩm khỏi giỏ
    @GetMapping("/remove/{id}")
    public String removeCart(@PathVariable int id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

    // Cập nhật số lượng
    @PostMapping("/update")
    public String updateCart(@RequestParam("id") int id, @RequestParam("qty") int qty) {
        cartService.update(id, qty);
        return "redirect:/cart";
    }

    // Hiển thị form thanh toán
    @GetMapping("/checkout")
    public String checkout(Model model) {
        if (cartService.getCount() == 0) {
            return "redirect:/cart"; // Giỏ hàng trống không cho thanh toán
        }
        model.addAttribute("order", new Order());
        model.addAttribute("TOTAL", cartService.getAmount());
        return "cart/checkout";
    }

    // Xử lý lưu hóa đơn
    @PostMapping("/checkout")
    public String processCheckout(@ModelAttribute("order") Order order) {
        if (cartService.getCount() > 0) {
            orderService.placeOrder(order, cartService);
        }
        return "redirect:/products"; // Thành công thì quay về trang sản phẩm
    }
}