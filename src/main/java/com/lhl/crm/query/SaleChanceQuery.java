package com.lhl.crm.query;

import com.lhl.crm.base.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 营销机会的查询类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleChanceQuery extends BaseQuery {

    //客户名
    private String customerName;
    //创建人
    private String createName;
    //分配状态 0表示未分配 1表示已分配
    private Integer state;

    //客户开发计划
    //开发状态
    private String devResult;
    //分配人
    private String assignMan;
}
