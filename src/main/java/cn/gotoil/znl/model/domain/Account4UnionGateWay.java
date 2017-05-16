package cn.gotoil.znl.model.domain;

import cn.gotoil.znl.adapter.PayConfigTarget;
import cn.gotoil.znl.model.enums.EnumStatus;
import lombok.Getter;
import lombok.Setter;
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
    public Account4UnionGateWay getPayConfig(String appId) {
        Account4UnionGateWay account4UnionGateWay = new Account4UnionGateWay();
        account4UnionGateWay.setId(1);
        account4UnionGateWay.setName("通联网关支付");
        account4UnionGateWay.setMerchantId("008500189990304");
        account4UnionGateWay.setMerchantKey("fcc3c40d59c191b3e91f47a03c010900");
        account4UnionGateWay.setStatus(EnumStatus.Enable.getCode());
        return account4UnionGateWay;
    }
}
