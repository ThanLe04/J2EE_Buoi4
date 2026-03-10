package phattrienungdungvoij2ee.bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phattrienungdungvoij2ee.bai4_qlsp.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByLoginName(String loginName);
}