package com.gshy.web.api.controllers;

import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.annotation.Path;
import com.bj58.ycs.tool.webutil.actionresult.ActionResult4JSON;
import com.bj58.ycs.tool.webutil.tools.ParamHelper;
import com.gshy.web.service.entity.AdvanceMoney;
import com.gshy.web.service.enums.ImageTypeEnum;

/**
 * 
 * @author liguoping
 *
 * 2018年5月8日-上午12:03:44
 *
 * @function 垫资
 */
@Path("/advance")
public class AdvanceMoneyController extends BaseController{
	
	@Path("/index")
	public ActionResult index(){
		return ActionResult.view("/");
	}
	
	@Path("/insert")
	public ActionResult insert(AdvanceMoney ad){
		try {
			String[] urls = ParamHelper.getStringArr(beat, "urls");
			long id = advanceMoneyBLL.insert(ad);
			imageBLL.batchInsert(urls,id,ImageTypeEnum.AdvanceMoney.getValue());
			return new ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"success!\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"fail!\"}");
	}
}
