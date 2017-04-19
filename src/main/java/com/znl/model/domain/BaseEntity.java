package com.znl.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    /**
     * 创建时间
     */
    @Column(  name="created_at" ,nullable = false ,updatable = false )
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;


    /**
     * 更新日期
     */
    @Column(  name="update_at" ,nullable = false  )
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;




    @PrePersist
    public void prePersist() {
        if(null==createdAt){
            createdAt = new Date();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updateAt = new Date();
    }


    public BaseEntity() {
    }

}
