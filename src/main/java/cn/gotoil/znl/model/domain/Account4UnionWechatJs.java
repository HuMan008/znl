package cn.gotoil.znl.model.domain;

import cn.gotoil.znl.adapter.PayConfigTarget;
import cn.gotoil.znl.adapter.listener.AccountAdaptee;
import cn.gotoil.znl.model.enums.EnumStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by think on 2017/5/15.
 */
@Component
@Entity
@Table(name = "dk_account_for_union_wechantjs")
@Getter
@Setter
public class Account4UnionWechatJs<T> extends BaseAccount  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private  Integer  id;


    /**
     * 账户 昵称
     */
    @Column( name="name",columnDefinition = "char(20)  " ,nullable = true )
    private String name;

    //242690089996452 syb_cusid
    @Column( name="unionUserId",columnDefinition = "char(15)  " ,nullable = true )
    private String unionUserId ;

//    00007511 syb_appid
    @Column( name="unionAppId",columnDefinition = "char(15)  " ,nullable = true )
    private String unionAppId ;

//    syb_appkey
    @Column( name="unionAppKey",columnDefinition = "char(50)  " ,nullable = true )
    private String unionAppKey ;

    //    wx30d2a05655df7746
    @Column( name="wechatAppId",columnDefinition = "char(18)  " ,nullable = true )
    private String wechatAppId ;

    @Column( name="wechatAppKey",columnDefinition = "char(32)  " ,nullable = true )
    private String wechatAppKey ;

    @Column(name = "status",nullable = true)
    private byte status;

@Override
    public Account4UnionWechatJs getPayConfig(int payAccountId) {
        Account4UnionWechatJs account4UnionWechatJs = new Account4UnionWechatJs();
        account4UnionWechatJs.setId(1);
        account4UnionWechatJs.setName("测试通联微信支付1");

        account4UnionWechatJs.setUnionUserId("242690089996452");
        account4UnionWechatJs.setUnionAppId("00007511");
        account4UnionWechatJs.setUnionAppKey("transguotongsecret");
        account4UnionWechatJs.setWechatAppId("wx30d2a05655df7746");
        account4UnionWechatJs.setWechatAppKey("b13bd0576973d0b8850e8ca7384ea683");

        account4UnionWechatJs.setStatus(EnumStatus.Enable.getCode());
        return account4UnionWechatJs;
    }

    @Override
    public Account4UnionWechatJs getConfig() {
        return this;
    }
}
