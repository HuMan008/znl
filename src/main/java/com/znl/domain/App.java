package com.znl.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.znl.web.annotation.ThirdValidation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * APP接入应用信息
 */
@Entity
@Table(name = "dk_app")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Getter
@Setter
public class App extends  BaseEntity   {

    /**
     * 主键
     */
    @Id
    @Column(name = "appid",columnDefinition = "char(32)  COMMENT '主键'",unique = true,nullable = false)
    private String appID;

    /**
     * 名称
     */
    @Column(length = 50,name="appname",columnDefinition = "char(50)  COMMENT '名称'" ,nullable = false )
    private String name;


    /**
     * 密钥
     */
    @Column( columnDefinition = "char(40)  COMMENT '密钥'" ,nullable = false )
    private String appkey;

    /**
     * 状态
     */
    @Column(  nullable = false )
    private Byte state;

    /**
     * 备注
     */
    @Column(length = 4000   )
    private String remark;

    /**
     * 到期日期
     */
    @Column(  name="expire_date" ,nullable = false  )
    private Date expireDate;




    public App() {

    }

}
