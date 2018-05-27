package com.bj58.web.api.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bj58.web.api.utils.APIConfig;
import com.bj58.web.api.utils.HttpServletRequestUtils;
import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.annotation.Path;
import com.bj58.wf.mvc.client.RequestFile;
import com.bj58.wf.mvc.client.UploadRequest;
import com.bj58.ycs.tool.webutil.actionresult.ActionResult4JSON;
import com.darengong.tools.util.net.http.HttpClient;
import com.darengong.tools.util.net.http.HttpResponse;
import com.gshy.web.service.interceptors.Login;

@Login
@Path("/file")
public class FileController extends BaseController {

	@Path("/accesstoken")
	public ActionResult accessToken() {
		try {
			String accesstoken = "";
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APIConfig.appid
					+ "&secret=" + APIConfig.appsecret;
			HttpResponse response = HttpClient.getInstance().get(url);
			String content = response.getContent();
			JSONObject jsonObject = JSONObject.parseObject(content);
			accesstoken = jsonObject.getString("access_token");
			if (StringUtils.isNotBlank(accesstoken)) {
				return new ActionResult4JSON("{\"ret\":\"1\",\"data\":\"" + accesstoken + "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"error!\"}");
	}

	/*
	 * @Path("/upload") public ActionResult upload(){ try { String accessToken =
	 * ParamHelper.getString(beat, "accessToken", ""); String mediaId =
	 * ParamHelper.getString(beat, "mediaId", ""); if
	 * (StringUtils.isBlank(accessToken) || StringUtils.isBlank(mediaId)) {
	 * log.info("accessToken: " + accessToken + ", " + accessToken); return new
	 * ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"参数错误!\"}"); }
	 * 
	 * String dateFile = new SimpleDateFormat("yyyyMMdd").format(new Date());
	 * String picPath = "/opt/web/static.haoyejinfu.com/"+dateFile+"/"; String
	 * picName = UUID.randomUUID().toString().replace("-", "");
	 * FileUtils.saveImageToDisk(accessToken, mediaId, picName, picPath); String
	 * picUrl = "https://www.haoyejinfu.com/static/img"+picName;
	 * log.info(picUrl); log.info(picPath); return new
	 * ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"success!\",\"data\":\""+
	 * picUrl+"\"}"); } catch (Exception e) { e.printStackTrace(); } return new
	 * ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"上传失败!\"}"); }
	 */

	@Path("/upload")
	public ActionResult upload() {

		try {
			// 解析请求的内容提取文件数据
			RequestFile fileItem = getRequestFile();
			log.info("request params:" + JSON.toJSONString(HttpServletRequestUtils.getParamsMap(beat.getRequest())));
			if (fileItem == null) {
				return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"success!\",\"data\":\"未检测到上传的文件\"}");
			}
			byte[] data = fileItem.getBytes();
			System.out.println("传过来的长度:   "+data.length);
			String dateFile = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String picPath = "/opt/web/static.haoyejinfu.com/img/" + dateFile + "/";
			String picName = UUID.randomUUID().toString().replace("-", "")+".jpg";
			saveImageToDisk(data, picName, picPath);
			String picUrl = "https://www.haoyejinfu.com/static/img/"+dateFile+"/" + picName;
			log.info(picUrl);
			log.info(picPath);
			return new ActionResult4JSON("{\"ret\":\"1\",\"msg\":\"success!\",\"data\":\"" + picUrl + "\"}");
		} catch (Exception ex) {
			return new ActionResult4JSON("{\"ret\":\"-1\",\"msg\":\"success!\",\"data\":\"errpr\"}");
		}
	}

	/**
	 * 2018年4月13日
	 * 
	 * @throws FileUploadException
	 */
	private RequestFile getRequestFile() {

		HttpServletRequest request = beat.getRequest();
		if (request instanceof UploadRequest) {
			UploadRequest uploadRequest = (UploadRequest) request;
			return uploadRequest.getFile("file");
		}
		return null;
	}

	public static void saveImageToDisk(byte[] buf, String picName, String picPath) throws Exception {
		InputStream inputStream = new ByteArrayInputStream(buf);
		byte[] data = new byte[10240];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			File file = new File(picPath);
			if (!file.exists()) {
				file.setWritable(true, false);
				file.mkdirs();
			}
			File pic = new File(picPath + picName);
			if (!pic.exists()) {
				pic.setWritable(true, false);
				file.createNewFile();
			}

			fileOutputStream = new FileOutputStream(picPath + picName);
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
