package cn.gotoil.znl.model.repository;

import cn.gotoil.znl.model.domain.Order;
import cn.gotoil.znl.model.domain.PayLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface JPAPayLogRepository extends JpaRepository<PayLog, Integer> {



}
