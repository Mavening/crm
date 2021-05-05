package com.lhl.crm.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo {

    private Integer code=200;
    private String msg="success";
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
