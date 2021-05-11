package com.lhl.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhl.crm.base.BaseService;
import com.lhl.crm.dao.CusDevPlanMapper;
import com.lhl.crm.dao.SaleChanceMapper;
import com.lhl.crm.query.CusDevPlanQuery;
import com.lhl.crm.service.CusDevPlanService;
import com.lhl.crm.utils.AssertUtil;
import com.lhl.crm.vo.CusDevPlan;
import com.lhl.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class CusDevServiceImpl extends BaseService implements CusDevPlanService {

    @Autowired
    private CusDevPlanMapper cusDevPlanMapper;

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    @Override
    public Map<String,Object> queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery){
        Map<String,Object> map = new HashMap<>();
        //开启分页
        PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getLimit());
        //得到对应的分页对象
        PageInfo<CusDevPlan> pageInfo = new PageInfo<CusDevPlan>(cusDevPlanMapper.selectByParams(cusDevPlanQuery));
        //设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @Override
    public void addCusDevPlan(CusDevPlan cusDevPlan) {
        //参数校验
        AssertUtil.isTrue(null==cusDevPlan.getSaleChanceId()||null==saleChanceMapper.selectByPrimaryKey(cusDevPlan.getSaleChanceId()),"营销机会数据不存在");
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"营销计划项内容不能为空");
        AssertUtil.isTrue(null==cusDevPlan.getPlanDate(),"计划时间不能为空");

        Date date = new Date();
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(date);
        cusDevPlan.setUpdateDate(date);

        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan)==0,"计划项数据添加失败");
    }

    @Override
    public void updateCusDevPlan(CusDevPlan cusDevPlan) {
        AssertUtil.isTrue(null==cusDevPlan.getId()||null==cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getId()),"数据异常，请重试");
        AssertUtil.isTrue(null==cusDevPlan.getSaleChanceId(),"营销机会数据不存在");
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"营销计划项内容不能为空");
        AssertUtil.isTrue(null==cusDevPlan.getPlanDate(),"计划时间不能为空");

        Date date = new Date();
        cusDevPlan.setCreateDate(date);
        cusDevPlan.setUpdateDate(date);
        AssertUtil.isTrue( cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)==0,"计划项数据更新失败");

    }

    @Override
    public CusDevPlan selectByPrimaryKey(Integer id) throws DataAccessException {
        return cusDevPlanMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteCusDevPlan(Integer id) {
        //1.判断ID是否为空，且数据存在
        AssertUtil.isTrue(null==id,"待删除数据不存在");
        //通过ID查询计划项对象
        CusDevPlan cusDevPlan = cusDevPlanMapper.selectByPrimaryKey(id);
        //设置
        cusDevPlan.setIsValid(0);
        cusDevPlan.setUpdateDate(new Date());
        //判断受影响的行数
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)==0, "计划项数据删除失败");
    }

    @Override
    public void updateSaleChanceDevResult(Integer id, Integer devResult) {
        //判断ID是否为空
        AssertUtil.isTrue(null==id,"待更新记录不存在");
        //通过id查询营销机会数据
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        //判断对象是否为空
        AssertUtil.isTrue(null==saleChance,"待更新记录不存在");
        //设置开发状态
        saleChance.setDevResult(devResult);
        //执行更新操作
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)!=1,"开发状态更新失败");
    }
}
