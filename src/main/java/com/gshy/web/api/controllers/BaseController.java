package com.gshy.web.api.controllers;

import com.bj58.wf.mvc.MvcController;
import com.gshy.web.service.bll.AdvanceMoneyBLL;
import com.gshy.web.service.bll.EmployeeBLL;
import com.gshy.web.service.bll.ImageBLL;
import com.gshy.web.service.bll.MortgageBLL;

public class BaseController extends MvcController{
	
	protected static final EmployeeBLL employeeBLL = new EmployeeBLL();
	
	protected static final MortgageBLL mortgageBLL = new MortgageBLL();
	
	protected static final AdvanceMoneyBLL advanceMoneyBLL = new AdvanceMoneyBLL();
	
	protected static final ImageBLL imageBLL = new ImageBLL();
	
}
