package com.lhl.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhl.crm.base.BaseService;
import com.lhl.crm.dao.SaleChanceMapper;
import com.lhl.crm.enums.DevResult;
import com.lhl.crm.enums.StateStatus;
import com.lhl.crm.query.SaleChanceQuery;
import com.lhl.crm.service.SaleChanceService;
import com.lhl.crm.utils.AssertUtil;
import com.lhl.crm.utils.PhoneUtil;
import com.lhl.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleChanceServiceImpl  extends BaseService<SaleChanceService,Integer> implements SaleChanceService{

    @Autowired
    private SaleChanceMapper saleChanceMapper;


    @Override
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery){
        Map<String,Object> map = new HashMap<>();
        //开启分页
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getLimit());
        //得到对应的分页对象
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));
        //设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @Override
    public void addSaleChance(SaleChance saleChance) {
        Date date = new Date();
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getCustomerName()),"客户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkMan()), "联系人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkPhone()), "联系人电话不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(saleChance.getLinkPhone()),"联系人手机号码格式不正确");
        saleChance.setIsValid(1);
        saleChance.setCreateDate(date);
        saleChance.setUpdateDate(date);
        //判断是否设置了指派人
        if(StringUtils.isBlank(saleChance.getAssignMan())){
            //如果为空表示未设置指派人
            saleChance.setAssignTime(null);
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        }else{
            //如果不为空表示设置指派人
            saleChance.setAssignTime(date);
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }
        //执行添加操作，判断受影响行数
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance)==0,"添加失败");
    }
}
