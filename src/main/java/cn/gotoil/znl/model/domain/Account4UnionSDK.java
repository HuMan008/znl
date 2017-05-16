package cn.gotoil.znl.model.domain;

import cn.gotoil.znl.adapter.PayConfigTarget;
import cn.gotoil.znl.adapter.listener.AccountAdaptee;
import cn.gotoil.znl.model.enums.EnumStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by think on 2017/5/16.
 */
@Component
@Entity
@Table(name = "dk_account_for_union_sdk")
@Getter
@Setter
public class Account4UnionSDK  extends BaseAccount implements PayConfigTarget {

    public static String notifyUrl;

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
    public Account4UnionSDK getPayConfig(String appId) {
        Account4UnionSDK account4UnionSDK = new Account4UnionSDK();
        account4UnionSDK.setId(1);
        account4UnionSDK.setName("测试通联SDK配置");
        account4UnionSDK.setStatus(EnumStatus.Enable.getCode());
        account4UnionSDK.setMerchantId("008500179950010");
        account4UnionSDK.setMerchantKey("086a172d073d991a8a4e3fb8bb0101ad");
        return account4UnionSDK;
    }
}
