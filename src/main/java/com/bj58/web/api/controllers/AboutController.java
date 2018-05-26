package com.bj58.web.api.controllers;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.bj58.web.api.vo.HistorySearchVO;
import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.annotation.POST;
import com.bj58.wf.mvc.annotation.Path;
import com.bj58.ycs.tool.webutil.actionresult.ActionResult4JSON;
import com.gshy.web.service.entity.AdvanceMoney;
import com.gshy.web.service.entity.Mortgage;
import com.gshy.web.service.query.AdvanceMoneyQuery;
import com.gshy.web.service.query.MortgageQuery;

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
	/**
	 * 
	 * @param type 1:垫资 2:房抵
	 * @return
	 */
	@Path("/history")
	@POST
	public ActionResult history(HistorySearchVO vo){
		try {
			int type = vo.getType();
			String json = "";
			if (type == 1) {
				AdvanceMoneyQuery advanceMoneyQuery = vo.parseToAdvanceMoneyQuery();
				List<AdvanceMoney> advanceMoneys = advanceMoneyBLL.getByQuery(advanceMoneyQuery);
				json = JSON.toJSONString(advanceMoneys);
			} else if (type == 2) {
				MortgageQuery mortgageQuery = vo.parseToMortgageQuery();
				List<Mortgage> mortgages = mortgageBLL.getByQuery(mortgageQuery);
				json = JSON.toJSONString(mortgages);
			}
			return new ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"success!\",\"data\":\""+json+"\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"fail\"}");
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
