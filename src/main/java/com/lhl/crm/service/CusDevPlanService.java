package com.lhl.crm.service;

import com.lhl.crm.query.CusDevPlanQuery;
import com.lhl.crm.vo.CusDevPlan;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface CusDevPlanService {

    /**
     * 多条件查询客户开发计划
     * @param cusDevPlanQuery
     * @return
     */
    Map<String,Object> queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery);

    /**
     * 添加客户开发计划
     * 参数校验：
     *      1.营销机会id 非空，存在
     *      2.计划项内容 非空
     *      3.计划时间 非空
     * 设置参数默认值
     *      是否有效 默认有效
     *      创建时间 系统当前时间
     *      修改时间 系统当前时间
     * 执行添加操作，判断受影响的行数
     * @param cusDevPlan
     */
    @Transactional
    void addCusDevPlan(CusDevPlan cusDevPlan);

    /**
     * 更新客户开发计划
     * 参数校验：
     *     1.计划项id 非空，存在
     *     2.营销机会id 非空
     *     3.计划时间 非空
     *     4.计划项内容 非空
     * 设置参数默认值
     *     修改时间 系统当前时间
     * 执行更新操作，判断受影响的行数
     * @param cusDevPlan
     */
    @Transactional
    void updateCusDevPlan(CusDevPlan cusDevPlan);

    CusDevPlan selectByPrimaryKey(Integer id);

    /**
     * 删除计划项数据方法
     * 1.判断ID是否为空，且数据存在
     * 2.修改isValid属性
     * 3.执行更新操作
     * @param id
     */
    void deleteCusDevPlan(Integer id);

    /**
     * 更新营销机会开发状态
     * @param id
     * @param devResult
     */
    void updateSaleChanceDevResult(Integer id, Integer devResult);
}
