package com.lhl.crm.service;

import com.lhl.crm.base.BaseService;
import com.lhl.crm.query.SaleChanceQuery;
import com.lhl.crm.vo.SaleChance;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    /**
     * 更新营销机会
     *      1.参数校验
     *          营销机会ID 非空，数据库中对应的记录存在
     *          1.customerName：客户名称 非空
     *          2.linkMan：联系人 非空
     *          3.linkPhone：联系号码 非空，手机号码格式正确
     *      2.设置相关参数默认值
     *          updateDate更新时间 设置为系统当前时间
     *          assignMan指派人
     *               原始数据未设置
     *                      修改后未设置  不需要操作
     *                      修改后已设置  assignTime指派时间设置为系统时间
     *                          分配状态 改为已分配
     *                          开发状态 改为开发中
     *               原始数据已设置
     *                      修改后未设置  assignTime指派时间设置为null
     *                          分配状态 改为未分配
     *                          开发状态 改为未开发
     *                      修改后已设置 判断修改前后是否是同一个指派人，如果是就不需要操作。如果不是，则需要更新指派时间   assignTime指派时间设置为系统时间
     *                          分配状态 改为已分配
     *                          开发状态 改为开发中
     * 3.执行修改操作
     * @param saleChance
     */
    @Transactional
    void updateSaleChance(SaleChance saleChance);

    /**
     * 根据id查询营销机会
     * @param integer
     * @return
     */
    SaleChance selectByPrimaryKey1(Integer integer);

    /**
     * 删除营销机会
     * @param ids
     */
    @Transactional
    void deleteBatchs(Integer[] ids);

}
