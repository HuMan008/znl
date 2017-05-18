package cn.gotoil.znl.model.repository;

import cn.gotoil.znl.model.domain.Account4UnionWechatJs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by think on 2017/5/17.
 */
@Repository
public interface JPAAccount4UnionWechatJsRepository extends JpaRepository<Account4UnionWechatJs, Integer> {
}
