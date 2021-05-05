package com.lhl.crm.model;

import com.lhl.crm.vo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String userId;
    private String userName;
    private String trueName;


}
