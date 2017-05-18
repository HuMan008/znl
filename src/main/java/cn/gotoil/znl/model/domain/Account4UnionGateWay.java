package cn.gotoil.znl.model.domain;

import cn.gotoil.bill.ApplicationContextProvider;
import cn.gotoil.znl.adapter.PayConfigTarget;
import cn.gotoil.znl.model.enums.EnumStatus;
import cn.gotoil.znl.model.repository.JPAAccount4UnionGateWayRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by think on 2017/5/16.
 * 通联网关支付
 */
@Component
@Entity
@Table(name = "dk_account_for_union_gateway")
@Getter
@Setter
public class Account4UnionGateWay extends BaseAccount  {


    @Autowired
    @Transient
    private JPAAccount4UnionGateWayRepository jpaAccount4UnionGateWayRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private  Integer  id;


    /**
     * 账户 昵称
     */
    @Column( name="name",columnDefinition = "char(20)  " ,nullable = true )
    private String name;

    @Column( name="merchantId",columnDefinition = "char(15)  " ,nullable = true )
    private String merchantId;

    @Column( name="merchantKey",columnDefinition = "char(32)  " ,nullable = true )
    private String merchantKey;

    @Column(name = "status",nullable = true)
    private byte status;

    @Override
    public Account4UnionGateWay getPayConfig(int payAccountId) {
        return ApplicationContextProvider.getApplicationContext().getBean(JPAAccount4UnionGateWayRepository.class).findOne(payAccountId);
    }

    @Override
    public Account4UnionGateWay getConfig() {
        return this;
    }
}
