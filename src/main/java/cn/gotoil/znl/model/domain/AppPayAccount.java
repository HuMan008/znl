package cn.gotoil.znl.model.domain;

/**
 * Created by wh on 2017/5/11.
 */

import cn.gotoil.znl.web.message.Combobox;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * APP接入应用 的 各种不同 支付 类型的 支付账户信息
 */
@Entity
@Table(name = "dk_app_pay_account")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Getter
@Setter
public class AppPayAccount  extends  BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer  ID;

    /**
     * 支付方式[哪张 收款账户表]
     */
    @Column( name="pay_type",length = 15 ,nullable = false )
    private String payType;

    /**
     * 支付 收款 账户ID
     */
    @Column( name="pay_account_id"  ,nullable = false )
    private Integer  payAccountID;

    /**
     * 接入应用ID
     */
    @Column(length = 32, name="appid"  ,nullable = false )
    private String  appID;

    /**
     * 当前接入的APP应用 这个收款 账户的状态
     */
    @Column( name="status"  ,nullable = false )
    private Byte status;

    public AppPayAccount() {
    }



    public enum PayTypeEnum {

        /**
         *  支付宝-SDK
         */
        Zhifubao_SDK( "ZFB_SDK","支付宝-SDK",AccountForZhifubaoSDK.class),


        /**
         *  支付宝-wap
         */
        Zhifubao_WAP( "ZFB_WAP","支付宝-wap",AccountForZhifubaoWAP.class),


        ;

        private final String code;
        private final String name;
        private final Class table;


        PayTypeEnum(String code,String name,Class table){
            this.code = code;
            this.name = name;
            this.table = table;
        }


        /**
         *  转换类型
         *  @return
         */
        public static PayTypeEnum getNameByCode(String code) {
            if(null == code){
                return  null;
            }
            for (  PayTypeEnum en :   PayTypeEnum.values()) {
                if ( en.getCode().equals( code ) ) {
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

            for(  PayTypeEnum type:   PayTypeEnum.values()){
                Combobox box = new Combobox();
                box.setCode( type.getCode()+"" );
                box.setName( type.getName() );
                list.add( box );
            }
            return list;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public Class getTable() {
            return table;
        }
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
            for (  StateEnum en :   StateEnum.values()) {
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

            for(  StateEnum type:   StateEnum.values()){
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
