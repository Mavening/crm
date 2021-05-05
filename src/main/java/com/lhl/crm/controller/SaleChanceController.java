package com.lhl.crm.controller;


import com.lhl.crm.base.BaseController;
import com.lhl.crm.base.ResultInfo;
import com.lhl.crm.query.SaleChanceQuery;
import com.lhl.crm.service.SaleChanceService;
import com.lhl.crm.utils.CookieUtil;
import com.lhl.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/saleChance")
public class SaleChanceController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;

    /**
     * 条件查询营销数据
     * @param saleChanceQuery
     * @return
     */
    @ResponseBody
    @GetMapping("querySaleChanceByParams")
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery){
        Map<String, Object> map = saleChanceService.querySaleChanceByParams(saleChanceQuery);
        return map;
    }

    /**
     * 营销机会管理页面
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "saleChance/sale_chance";
    }

    /**
     * 添加营销机会
     * @param saleChance
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest request){
        //从cookie中获取当前登录用户名
        String userName = CookieUtil.getCookieValue(request,"userName");
        //设置用户名到营销机会对象中
        saleChance.setCreateMan(userName);
        //调用添加方法
        saleChanceService.addSaleChance(saleChance);
        return new ResultInfo(200,"营销机会数据添加成功",null);
    }

    /**
     * 进入添加或修改营销机会数据页面
     * @return
     */
    @GetMapping("/toSaleChancePage")
    public String toSaleChancePage(){
        return "/saleChance/add_update";
    }

    /**
     * 更新营销机会
     * @param saleChance
     * @return
     */
    @ResponseBody
    @PutMapping("/update")
    public ResultInfo updateSaleChance(SaleChance saleChance){
        //调用修改方法
        saleChanceService.updateSaleChance(saleChance);
        return new ResultInfo(200,"营销机会数据更新成功",null);
    }
}
