package com.gshy.web.api.controllers;

import com.alibaba.fastjson.JSON;
import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.annotation.GET;
import com.bj58.wf.mvc.annotation.Path;
import com.bj58.ycs.tool.webutil.actionresult.ActionResult4JSON;
import com.bj58.ycs.tool.webutil.tools.ParamHelper;
import com.gshy.web.service.entity.Employee;
import com.gshy.web.service.utils.SecurityUtils;

@Path("/login")
public class LoginController extends BaseController{
	
	@Path("/fast")
	@GET
	public ActionResult fastLogin(){
		try {
			String email = ParamHelper.getString(beat, "email", "");
			String password = ParamHelper.getString(beat, "password", "");
			Employee emp = employeeBLL.getByEmail(email);
			if (emp == null) {
				return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"用户名或密码错误!\"}");
			}
			String dataPassword = emp.getPassword();
			if (password.equals(dataPassword)) {
				return new ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"登陆成功!\"}");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"用户名或密码错误!\"}");
	}
	
	@Path("/islogin")
	public ActionResult isLogin(){
		try {
			long currentUserId = SecurityUtils.currentUserId(beat);
			if(currentUserId>0){
				return new ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"已登录!\"}"); 				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"未登录!\"}"); 	
	}
	
	@Path("/userinfo")
	public ActionResult userInfo(){
		try {
			Employee employee = SecurityUtils.currentUserInfo(beat);
			if(employee!=null){
				return new ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"success!\",\"data\":\""+JSON.toJSONString(employee)+"\"}"); 				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"fail\"}"); 	
	}
}
