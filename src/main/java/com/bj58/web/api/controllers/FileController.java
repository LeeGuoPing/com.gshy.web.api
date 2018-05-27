package com.bj58.web.api.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.annotation.Path;
import com.bj58.ycs.tool.webutil.actionresult.ActionResult4JSON;
import com.bj58.ycs.tool.webutil.tools.ParamHelper;
import com.gshy.web.service.utils.FileUtils;

@Path("/file")
public class FileController extends BaseController{
	
	
	
	@Path("/accesstoken")
	public ActionResult accessToken(){
		if(StringUtils.isNotBlank(accesstoken)){
			return new ActionResult4JSON("{\"ret\":\"1\",\"data\":\""+accesstoken+"\"}"); 
		}else{
			return new ActionResult4JSON("{\"ret\":\"1\",\"data\":\"error\"}"); 
		}
	}
	

	@Path("/upload")
	public ActionResult upload(){
		try {
			String accessToken = ParamHelper.getString(beat, "accessToken", "");
			String mediaId = ParamHelper.getString(beat, "mediaId", "");
			if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(mediaId)) {
				log.info("accessToken: " + accessToken + ", " + accessToken);
				return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"参数错误!\"}");
			}
			
			String dateFile = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String picPath = "/opt/web/static.haoyejinfu.com/"+dateFile+"/";
			String picName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
			FileUtils.saveImageToDisk(accessToken, mediaId, picName, picPath);
			String picUrl = "https://www.haoyejinfu.com/static/img"+picName;
			log.info(picUrl);
			log.info(picPath);
			return new ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"success!\",\"data\":\""+picUrl+"\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"上传失败!\"}");
	}
}
