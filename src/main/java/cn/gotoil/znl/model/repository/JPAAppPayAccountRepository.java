package cn.gotoil.znl.model.repository;

import cn.gotoil.znl.model.domain.AppPayAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface JPAAppPayAccountRepository extends JpaRepository<AppPayAccount, Integer> {


    AppPayAccount findByAppIDAndPayType( String appID,String payType );

}
