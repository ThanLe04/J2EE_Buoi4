package phattrienungdungvoij2ee.bai4_qlsp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import phattrienungdungvoij2ee.bai4_qlsp.model.CartItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@SessionScope // Đánh dấu service này tồn tại theo session của từng user
public class CartService {
    
    // Dùng HashMap để lưu giỏ hàng, Key là ProductID
    private Map<Integer, CartItem> map = new HashMap<>();

    public void add(CartItem item) {
        CartItem existedItem = map.get(item.getProductId());
        if (existedItem != null) {
            // Nếu đã có trong giỏ, tăng số lượng
            existedItem.setQuantity(existedItem.getQuantity() + item.getQuantity());
        } else {
            // Chưa có thì thêm mới
            map.put(item.getProductId(), item);
        }
    }

    public void remove(int productId) {
        map.remove(productId);
    }

    public void update(int productId, int quantity) {
        CartItem item = map.get(productId);
        if (item != null) {
            item.setQuantity(quantity);
        }
    }

    public void clear() {
        map.clear();
    }

    public Collection<CartItem> getItems() {
        return map.values();
    }

    public int getCount() {
        return map.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public double getAmount() {
        return map.values().stream().mapToDouble(CartItem::getAmount).sum();
    }
}