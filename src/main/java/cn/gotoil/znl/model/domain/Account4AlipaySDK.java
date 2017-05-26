package cn.gotoil.znl.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by think on 2017/5/23.
 */
@Entity
@Table(name = "dk_account_for_zhifubao_sdk")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Getter
@Setter
public class Account4AlipaySDK extends BaseAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private  Integer  id;


    /**
     * 账户 昵称
     */
    @Column( name="name",columnDefinition = "char(20)  " ,nullable = false )
    private String name;

    /**
     * APPID
     */
    @Column( name="app_id",columnDefinition = "char(32)  " ,nullable = false )
    private String appID;

    /**
     * 公钥
     */
    @Column( name="public_key",length = 400,nullable = false )
    private String publicKey;

    /**
     * 私钥
     */
    @Column( name="private_key",length = 2000,nullable = false )
    private String privateKey;

    /**
     * 账户状态
     */
    @Column( name="status"  ,nullable = false )
    private byte status;




    @Override
    public BaseAccount getConfig() {
        return this;
    }

    @Override
    public BaseAccount getPayConfig(int payAccountId) {
        return null;
    }
}
