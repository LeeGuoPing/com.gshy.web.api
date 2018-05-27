package com.bj58.web.api.controllers;

import java.util.Date;

import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.annotation.POST;
import com.bj58.wf.mvc.annotation.Path;
import com.bj58.ycs.tool.webutil.actionresult.ActionResult4JSON;
import com.bj58.ycs.tool.webutil.tools.ParamHelper;
import com.gshy.web.service.entity.AdvanceMoney;
import com.gshy.web.service.enums.ImageTypeEnum;
import com.gshy.web.service.interceptors.Login;
import com.gshy.web.service.utils.SecurityUtils;

/**
 * 
 * @author liguoping
 *
 * 2018年5月8日-上午12:03:44
 *
 * @function 垫资
 */
@Login
@Path("/advance")
public class AdvanceMoneyController extends BaseController{
	
	@Path("/index")
	public ActionResult index(){
		return ActionResult.view("/");
	}
	
	@Path("/insert")
	@POST
	public ActionResult insert(AdvanceMoney ad){
		try {
			long empId = SecurityUtils.currentUserId(beat);
			String[] urls = ParamHelper.getStringArr(beat, "urls");
			System.out.println("insert empId: "+empId);
			if(empId>0){
				ad.setCreateEmp(empId);
			}
			ad.setCreateTime(new Date());
			long id = advanceMoneyBLL.insert(ad);
			imageBLL.batchInsert(urls,id,ImageTypeEnum.AdvanceMoney.getValue());
			return new ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"success!\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"fail!\"}");
	}
}
