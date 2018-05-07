package com.gshy.web.api.controllers;

import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.annotation.Path;

/**
 * 
 * @author liguoping
 *
 * 2018年5月8日-上午12:04:21
 *
 * @function 关于我们
 */
@Path("/about")
public class AboutController extends BaseController {
	
	@Path("/index")
	public ActionResult index(){
		return ActionResult.view("/");
	}
	
	@Path("/history")
	public ActionResult history(){
		return ActionResult.view("/");
	}
	
	@Path("/me")
	public ActionResult me(){
		return null;
	}
	
	@Path("/use")
	public ActionResult use(){
		return null;
	}
}
