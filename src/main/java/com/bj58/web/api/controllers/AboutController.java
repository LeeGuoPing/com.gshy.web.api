package com.bj58.web.api.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.bj58.web.api.vo.HistoryList;
import com.bj58.web.api.vo.HistorySearchVO;
import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.annotation.POST;
import com.bj58.wf.mvc.annotation.Path;
import com.bj58.ycs.tool.webutil.actionresult.ActionResult4JSON;
import com.bj58.ycs.tool.webutil.tools.DateTool;
import com.gshy.web.service.entity.AdvanceMoney;
import com.gshy.web.service.entity.Mortgage;
import com.gshy.web.service.query.AdvanceMoneyQuery;
import com.gshy.web.service.query.MortgageQuery;
import com.gshy.web.service.utils.SecurityUtils;

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
	 * @param type 1:房抵 2:垫资
	 * @return
	 */
	@Path("/history")
	@POST
	public ActionResult history(){
		try {
			long uid = SecurityUtils.currentUserId(beat);
			HistorySearchVO vo = new HistorySearchVO();
			vo.setEmpId(uid);
			String json = "";
			AdvanceMoneyQuery advanceMoneyQuery = vo.parseToAdvanceMoneyQuery();
			List<AdvanceMoney> advanceMoneys = advanceMoneyBLL.getByQuery(advanceMoneyQuery);
//			json = JSON.toJSONString(advanceMoneys);
	
			MortgageQuery mortgageQuery = vo.parseToMortgageQuery();
			List<Mortgage> mortgages = mortgageBLL.getByQuery(mortgageQuery);
//			json = JSON.toJSONString(mortgages);
			
			List<HistoryList> list = new ArrayList<HistoryList>();
			
			if(advanceMoneys!=null &&advanceMoneys.size()>0){
				for (AdvanceMoney advanceMoney : advanceMoneys) {
					HistoryList historyList = new HistoryList();
					historyList.setAuditStatus(advanceMoney.getAuditState());
					Date createTime = advanceMoney.getCreateTime();
					if(createTime!=null)
					historyList.setCreateTime(DateTool.getInstance().format(createTime, "yyyy-MM-dd HH:mm:ss"));
					historyList.setType(2);
					list.add(historyList);
				}
			}
			
			if(mortgages!=null &&mortgages.size()>0){
				for (Mortgage mortgage : mortgages) {
					HistoryList historyList = new HistoryList();
					historyList.setAuditStatus(mortgage.getAuditState());
					Date createTime = mortgage.getCreateTime();
					historyList.setCreateTime(DateTool.getInstance().format(createTime, "yyyy-MM-dd HH:mm:ss"));
					historyList.setType(1);
					list.add(historyList);
				}
			}
			
			json = JSON.toJSONString(list);
			
			return new ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"success!\",\"data\":"+json+"}");
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
