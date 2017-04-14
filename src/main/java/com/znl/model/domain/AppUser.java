package com.znl.model.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * App user
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/1/19.11:51
 */
/*@Document(collection="t_users")
@Data     */
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AppUser {

    private String id;
    private String appId;
    private String password;
    private String realName;
    private String nickName;

    private String gender;
    private String avatar;
    private String signature;

    private String wxOpenId;
    private String wxUnionId;

    private String aliUserId;

    private String unionUserId;

    private String birthday;
    private String address;

    private String createDate;
    private String updateDate;
    private String lastLoginDate;
    private Boolean disabled;
    //禁用次数
    private int lockTimes;
    //禁言小时
    private int lockHour;

    private String phone;
    private Boolean isPhoneActive;
    private String email;
    private Boolean isEmailActive;

}