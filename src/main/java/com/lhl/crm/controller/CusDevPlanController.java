package com.lhl.crm.controller;

import com.lhl.crm.base.BaseController;
import com.lhl.crm.base.ResultInfo;
import com.lhl.crm.query.CusDevPlanQuery;
import com.lhl.crm.service.CusDevPlanService;
import com.lhl.crm.service.SaleChanceService;
import com.lhl.crm.vo.CusDevPlan;
import com.lhl.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/cus_dev_plan")
public class CusDevPlanController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;

    @Autowired
    private CusDevPlanService cusDevService;

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


    /**
     * 多条件查询客户开发计划
     * @param cusDevPlanQuery
     * @return
     */
    @ResponseBody
    @GetMapping("/queryCusDevPlanByParams")
    public Map<String,Object> querySaleChanceByParams(CusDevPlanQuery cusDevPlanQuery){
        System.out.println(cusDevPlanQuery.getSaleChanceId()+"------------------");
       return cusDevService.queryCusDevPlanByParams(cusDevPlanQuery);
    }

    /**
     * 添加计划项数据
     * @param cusDevPlan
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public ResultInfo addCusDevPlan(CusDevPlan cusDevPlan){
        cusDevService.addCusDevPlan(cusDevPlan);
        return new ResultInfo(200,"添加计划项成功",null);
    }


    /**
     * 更新计划项数据
     * @param cusDevPlan
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan){
        cusDevService.addCusDevPlan(cusDevPlan);
        return new ResultInfo(200,"更新计划项成功",null);
    }

    /**
     * 删除计划项数据
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/delete")
    public ResultInfo deleteCusDevPlan(Integer id){
        cusDevService.deleteCusDevPlan(id);
        return new ResultInfo(200,"删除计划项成功",null);
    }

    /**
     * 进入添加或修改计划项
     * @return
     */
    @GetMapping("/toAddOrUpdateCusDevPlanPage")
    public String toAddOrUpdateCusDevPlanPage(HttpServletRequest request, Integer sId,Integer id){
        //将营销机会ID设置到请求域中，给计划项页面获取
        request.setAttribute("sId",sId);
        CusDevPlan cusDevPlan = (CusDevPlan) cusDevService.selectByPrimaryKey(id);
        System.out.println(cusDevPlan);
        //将计划项添加到请求域中
        request.setAttribute("cusDevPlan", cusDevPlan);
        return "cusDevPlan/add_update";
    }

    /**
     * 更新营销机会的开发状态
     * @param id
     * @param devResult
     * @return
     */
    @ResponseBody
    @PutMapping("updateSaleChanceDevResult")
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        cusDevService.updateSaleChanceDevResult(id,devResult);
        return new ResultInfo(200,"success",null);
    }
}
