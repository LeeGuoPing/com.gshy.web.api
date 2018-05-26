package com.bj58.web.api.vo;

import com.gshy.web.service.query.AdvanceMoneyQuery;
import com.gshy.web.service.query.AdvanceMoneyQuery.AdvanceMoneyQueryBuilder;
import com.gshy.web.service.query.MortgageQuery;
import com.gshy.web.service.query.MortgageQuery.MortgageQueryBuilder;

public class HistorySearchVO {
	private int type;
	
	private long empId;
	
	private int page;
	
	private int pageSize;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public AdvanceMoneyQuery parseToAdvanceMoneyQuery() {
		AdvanceMoneyQueryBuilder builder = AdvanceMoneyQueryBuilder.builder();
		builder.addCreateEmp(empId);
		if(page<=0){
			page=1;
		}
		if(pageSize<=0){
			pageSize=20;
		}
		builder.setPage(page);
		builder.setPageSize(pageSize);
		return builder.build();
	}
	
	public MortgageQuery parseToMortgageQuery() {
		MortgageQueryBuilder builder = MortgageQueryBuilder.builder();
		builder.addCreateEmp(empId);
		if(page<=0){
			page=1;
		}
		if(pageSize<=0){
			pageSize=20;
		}
		builder.setPage(page);
		builder.setPageSize(pageSize);
		return builder.build();
	}
	
	
}
