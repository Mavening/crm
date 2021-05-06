package com.lhl.crm.controller;

import com.lhl.crm.base.BaseController;
import com.lhl.crm.service.CusDevService;
import com.lhl.crm.service.SaleChanceService;
import com.lhl.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cus_dev_plan")
public class CusDevPlanController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;

    @Autowired
    private CusDevService cusDevService;

    /**
     * 进入客户开发计划页面
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "cusDevPlan/cus_dev_plan";
    }

    /**
     * 打开计划项开发与详情页面
     * @param id
     * @return
     */
    @GetMapping("/toCusDevPlanPage")
    public String toCusDevPlanPage(String id, HttpServletRequest request){
        //通过id查询营销机会对象
        SaleChance saleChance = saleChanceService.selectByPrimaryKey1(Integer.valueOf(id));
        //将对象设置到请求域中
        request.setAttribute("saleChance",saleChance);
        return "cusDevPlan/cus_dev_plan_data";
    }
}
