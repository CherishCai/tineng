
package cn.cherish.tineng.dal.repository;

import cn.cherish.tineng.dal.entity.Physical;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PhysicalDAO extends IBaseDAO<Physical, Long> {


    Physical findByUsername(String username);

    Physical findByNickname(String nickname);

    @Query("SELECT p FROM Physical AS p ")
    List<Physical> listAllPaged(Pageable pageable);

}
