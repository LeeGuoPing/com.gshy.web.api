package com.bj58.web.api.controllers;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.annotation.POST;
import com.bj58.wf.mvc.annotation.Path;
import com.bj58.ycs.tool.webutil.actionresult.ActionResult4JSON;
import com.bj58.ycs.tool.webutil.tools.ParamHelper;
import com.gshy.web.service.entity.Mortgage;
import com.gshy.web.service.enums.ImageTypeEnum;
import com.gshy.web.service.interceptors.Login;
import com.gshy.web.service.utils.SecurityUtils;

/**
 * 
 * @author liguoping
 *
 * 2018年5月8日-上午12:02:41
 *
 * @function 房抵
 */
@Login
@Path("/mortgage")
public class MortgageController extends BaseController{

	@Path("/index")
	public ActionResult index(){
		return ActionResult.view("/");
	}
	
	@Path("/insert")
	@POST
	public ActionResult insert(Mortgage mortgage){
		try {
			long empId = SecurityUtils.currentUserId(beat);
			String urls = ParamHelper.getString(beat, "urls","");
			String[] urlArry = parseURL(urls);
			System.out.println("insert empId: "+empId);
			if(empId>0){
				mortgage.setCreateEmp(empId);
			}
			mortgage.setCreateTime(new Date());
			long id = mortgageBLL.insert(mortgage);
			imageBLL.batchInsert(urlArry,id,ImageTypeEnum.Mortgage.getValue());
			return new ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"success!\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"fail!\"}");
	}

	private String[] parseURL(String urls) {
		String[] urlArry = new String[10];
		if(StringUtils.isNotBlank(urls)){
			urlArry = urls.split(",");
			
		}
		return urlArry;
	}
	
	
}
