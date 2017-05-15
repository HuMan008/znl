package cn.gotoil.znl.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

/**
 *  操作日志
 */
@Entity
@Table(name = "dk_opt_log")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Getter
@Setter
public class OptLog  extends  BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private  Integer  ID;

    /**
     *   接入应用id
     */
    @Column( name="appid",length = 32 ,nullable = false )
    private String appID;

    /**
     *   操作描述
     */
    @Column( name="descp",length = 50  ,nullable = false )
    private String descp;

    /**
     *   操作内容
     */
    @Column( name="content",length = 4000  ,nullable = false )
    private String content;


    public OptLog() {
    }
}
