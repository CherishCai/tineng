
package cn.cherish.tineng.dal.repository;

import cn.cherish.tineng.dal.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomerDAO extends IBaseDAO<Customer,Long> {

    Customer findByUsername(String username);

    @Query("SELECT stu FROM Customer AS stu ")
    List<Customer> listAllPaged(Pageable pageable);

}
