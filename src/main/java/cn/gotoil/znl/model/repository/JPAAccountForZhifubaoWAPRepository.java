package cn.gotoil.znl.model.repository;

import cn.gotoil.znl.model.domain.AccountForZhifubaoSDK;
import cn.gotoil.znl.model.domain.AccountForZhifubaoWAP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface JPAAccountForZhifubaoWAPRepository extends JpaRepository<AccountForZhifubaoWAP, Integer> {


    Page<AccountForZhifubaoWAP> findAll(Specification<AccountForZhifubaoWAP> condition, Pageable pageable);

}
