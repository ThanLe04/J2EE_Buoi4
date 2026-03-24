package phattrienungdungvoij2ee.bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdungvoij2ee.bai4_qlsp.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}