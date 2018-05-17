package com.gshy.web.api.controllers;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.alibaba.fastjson.JSONObject;
import com.bj58.wf.mvc.MvcController;
import com.darengong.tools.util.net.http.HttpClient;
import com.darengong.tools.util.net.http.HttpResponse;
import com.gshy.web.api.utils.QuartzSchedulerFactory;
import com.gshy.web.service.bll.AdvanceMoneyBLL;
import com.gshy.web.service.bll.EmployeeBLL;
import com.gshy.web.service.bll.ImageBLL;
import com.gshy.web.service.bll.MortgageBLL;

public class BaseController extends MvcController{
	
	protected static final EmployeeBLL employeeBLL = new EmployeeBLL();
	
	protected static final MortgageBLL mortgageBLL = new MortgageBLL();
	
	protected static final AdvanceMoneyBLL advanceMoneyBLL = new AdvanceMoneyBLL();
	
	protected static final ImageBLL imageBLL = new ImageBLL();
	
	/*** parameters ***/
	protected static String accesstoken = "";
	
	private static final String appid = "";
	
	private static final String appsecret = "";
	
	static{
		try {
//			getAccessToken();
//			RegularlySelectData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void RegularlySelectData() throws SchedulerException {
		Scheduler selectDataJobScheduler = QuartzSchedulerFactory.getScheduler();
		JobDetail selectDataJob = JobBuilder.newJob(SelectDataJob.class).withIdentity("selectDataJob", "selectData")
				.build();
		Trigger selectDataJobTrigger = TriggerBuilder.newTrigger().withIdentity("selectDataJobTrigger", "selectData")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 */2 * * ?")).build();
		selectDataJobScheduler.scheduleJob(selectDataJob, selectDataJobTrigger);
		selectDataJobScheduler.start();
	}

	public static class SelectDataJob implements Job {
		@Override
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			try {
				System.out.println("============ RegularlySelectData ============ 本次执行开始，当前时间：" + LocalDate.now() + LocalTime.now());
				getAccessToken();
				System.out.println("============ RegularlySelectData ============ 本次执行结束，当前时间：" + LocalDate.now() + LocalTime.now());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void getAccessToken() throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
		HttpResponse response = HttpClient.getInstance().get(url);
		String content = response.getContent();
		JSONObject jsonObject = JSONObject.parseObject(content);
		String access_token = jsonObject.getString("access_token");
		if(StringUtils.isNotBlank(access_token)){
			accesstoken = access_token;
		}
	}
}
