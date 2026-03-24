package phattrienungdungvoij2ee.bai4_qlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phattrienungdungvoij2ee.bai4_qlsp.model.*;
import phattrienungdungvoij2ee.bai4_qlsp.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductService productService; // Service bạn đã có sẵn

    @Transactional
    public void placeOrder(Order order, CartService cartService) {
        // Tạo danh sách chi tiết hóa đơn
        List<OrderDetail> details = new ArrayList<>();
        
        for (CartItem item : cartService.getItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order); // Liên kết detail với order
            
            // Lấy thực thể Product từ DB
            Product product = productService.get(item.getProductId());
            detail.setProduct(product);
            
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getPrice());
            
            details.add(detail);
        }
        
        order.setOrderDetails(details);
        order.setTotalAmount(cartService.getAmount());
        
        // Lưu xuống DB (Cascade sẽ tự động lưu các OrderDetail)
        orderRepository.save(order);
        
        // Đặt hàng xong thì xóa giỏ hàng
        cartService.clear();
    }
}