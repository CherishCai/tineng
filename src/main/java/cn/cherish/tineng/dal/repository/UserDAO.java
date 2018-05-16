
package cn.cherish.tineng.dal.repository;

import cn.cherish.tineng.dal.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDAO extends IBaseDAO<User,Long> {

    User findByUsername(String username);

    @Query("SELECT u FROM User AS u ")
    List<User> listAllPaged(Pageable pageable);



}
