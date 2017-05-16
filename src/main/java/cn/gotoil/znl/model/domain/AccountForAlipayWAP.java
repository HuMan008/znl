package cn.gotoil.znl.model.domain;

/**
 * Created by wh on 2017/5/11.
 */

import cn.gotoil.znl.web.message.Combobox;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台 支付宝 收款账号
 */
@Entity
@Table(name = "dk_account_for_zhifubao_wap")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Getter
@Setter
public class AccountForAlipayWAP extends  BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private  Integer  id;


    /**
     * 账户 昵称
     */
    @Column( name="name",columnDefinition = "char(30)  " ,nullable = false )
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
    @Column( name="private_key",length = 2000 , nullable = false )
    private String privateKey;

    /**
     * 账户状态
     */
    @Column( name="status"  ,nullable = false )
    private Byte status;

    public AccountForAlipayWAP() {
    }

    public enum StateEnum {

        /**
         *  启用中
         */
        Enable((byte)1,"启用中"),
        /**
         *  已禁用
         */
        Disable((byte)-1 ,"已禁用"),
        ;

        private final byte code;
        private final String name;


        StateEnum(Byte code,String name){
            this.code = code;
            this.name = name;
        }


        /**
         *  转换类型
         *  @return
         */
        public static StateEnum getNameByCode(Byte code) {
            if(null == code){
                return  null;
            }
            for ( StateEnum en :  StateEnum.values()) {
                if ( en.getCode() == code ) {
                    return en;
                }
            }
            return null;
        }

        public static List<Combobox> getEnumList(){

            List<Combobox> list = new ArrayList<Combobox>();

            Combobox boxNone = new Combobox();
            boxNone.setCode("");
            boxNone.setName( "--请选择--");
            list.add( boxNone );

            for( StateEnum type:  StateEnum.values()){
                Combobox box = new Combobox();
                box.setCode( type.getCode()+"" );
                box.setName( type.getName() );
                list.add( box );
            }
            return list;
        }

        public byte getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
