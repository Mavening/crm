package com.lhl.crm.query;

import com.lhl.crm.base.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery extends BaseQuery {

    //用户名
    private String userName;
    //邮箱
    private String email;
    //手机号码
    private String phone;
}
