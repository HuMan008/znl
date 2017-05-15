package cn.gotoil.znl.model.repository;

import cn.gotoil.znl.model.domain.App;
import cn.gotoil.znl.model.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface JPAOrderRepository extends JpaRepository<Order, String> {



}
