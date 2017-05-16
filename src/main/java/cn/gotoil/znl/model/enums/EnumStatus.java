package cn.gotoil.znl.model.enums;

import cn.gotoil.znl.web.message.Combobox;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2017/5/15.
 */
@Getter
@AllArgsConstructor
public enum EnumStatus {

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





    /**
     *  转换类型
     *  @return
     */
    public static EnumStatus getNameByCode(byte code) {

        for ( EnumStatus en :  EnumStatus.values()) {
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

        for( EnumStatus type:  EnumStatus.values()){
            Combobox box = new Combobox();
            box.setCode( type.getCode()+"" );
            box.setName( type.getName() );
            list.add( box );
        }
        return list;
    }

}
