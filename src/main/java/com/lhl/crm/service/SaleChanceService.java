package com.lhl.crm.service;

import com.lhl.crm.base.BaseService;
import com.lhl.crm.query.SaleChanceQuery;
import com.lhl.crm.vo.SaleChance;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface SaleChanceService {


    /**
     * 多条件分页查询营销
     * 返回的数据格式必须满足layui中数据表格要求的格式
     * @param saleChanceQuery
     * @return
     */
    Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery);


    /**
     * 添加营销机会
     * 参数校验：
     *      1.customerName：客户名称 非空
     *      2.linkMan：联系人 非空
     *      3.linkPhone：联系号码 非空，手机号码格式正确
     * 设置相关参数默认值
     *      1.createMan：创建人 当前登录用户名
     *      2.assignMan：指派人
     *      如果未设置指派人
     *              state分配状态 0=未分配 1=已分配
     *              指派时间 设置为null
     *              devResult开发状态 (0=未开发 1=开发中 2=开发成功 3=开发失败)  设置为0，未开发
     *
     *      如果设置了指派人
     *              state设置为1
     *              指派时间 系统当前时间
     *              devResult开发状态 (0=未开发 1=开发中 2=开发成功 3=开发失败)  设置为1，开发中
     *              is_valid是否有效 设置为有效
     *              createDate 创建时间 默认系统当前时间
     *              updateDate 修改时间 默认是系统当前时间
     * 执行添加操作，判断受影响的行数
     * @param saleChance
     */
    @Transactional
    void addSaleChance(SaleChance saleChance);
}
