package top.iotos.synApi.upstreamApi.chinaMobile.oneLink.ecV5;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import top.iotos.synApi.utils.http.UrlUtil;
import top.iotos.synApi.upstreamApi.config.RedisUtil;
import top.iotos.synApi.utils.http.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 移动 ecv5 API 接口
 */
public class EcV5Api {

	private RedisUtil redisUtil;
	public EcV5Api(){};

	//构造赋值
	public EcV5Api(Map<String, Object> initMap, String template){
		this.initMap = initMap;
		redisUtil = new RedisUtil();
		serverIp = initMap.get("url").toString();
		appId = initMap.get("parameter_one").toString();
		password = initMap.get("parameter_tow").toString();
		this.template = template;
		String R_token = null;
		try {
			R_token = (String) redisUtil.redisTemplate.opsForValue().get(appId+":"+password);
		}catch (Exception e){
		}
		if(R_token!=null && R_token!=""){
			token = R_token;
		}else{
			SetToken(appId,password);
		}
		pMap = new HashMap<>();
		pMap.put("token", token);
    }
	
	//请求地址
	protected static String serverIp = "";
	//appId
	protected static String appId = null;
	//秘钥
	protected static String password = null;
	//模板类型
	public static String template = null;
	// token
	public static String token = null;
	//请求参数
	public static HashMap<String,Object> pMap = null;

	public static Map<String, Object> initMap = null;

	/**
	 * 获取 token
	 * @param appid
	 * @param pwd
	 */
	public  void SetToken(String appid, String pwd) {
		String method = serverIp + "/get/token";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", appid);
		params.put("password", pwd);
		params.put("transid", appId + new SimpleDateFormat("YYYYMMDDHHMMSS").format(new Date()));

		String reqUrl = UrlUtil.getUrl(method, params);
		String res = HttpUtil.get(reqUrl);
		if(res!=null && res.length()>0){
			JSONObject json = JSON.parseObject(res);
			Object status = json.get("status");
			if (status != null && status.toString().equals("0")) {
				Map<String, String> map = ((List<Map<String, String>>) json.get("result")).get(0);
				token = map.get("token");
				redisUtil.redisTemplate.opsForValue().set(appId+":"+password, token, 30, TimeUnit.MINUTES);
			}
		}
	}

	/**
	 *  检查返回数据为 token 失效 时 重新 获取 token
	 * @param str
	 * @param map
	 * @param url
	 * @return
	 */
	public  String  repeat (String str, Map<String,Object> map, String url){
		if (str != null) {
			JSONObject rmap = JSONObject.parseObject(str);
			//{"status":"12021","message":"TOKEN不存在或已过期，请重新获取","result":[]}
			if (rmap.get("status").toString().equals("12021")) {
				// 重置 token , 再次执行
				SetToken(appId, password);
				map.put("token", token);
				str = HttpUtil.post(url,JSON.toJSONString(map));
			}
		}
		return str;
	}

	/**
	 * 数据发送
	 * @param url
	 * @param map
	 * @return
	 */
	public String send(String url, Map<String,Object> map){
		String str = null;
		str = HttpUtil.post(url, JSON.toJSONString(map));
		return str;
	}

	/**
	 * 公共请求
	 * @param functionNm
	 * @param pMap
	 * @return
	 */
	public String commonRequest(String functionNm,Map<String,Object> pMap) {
		pMap.put("transid", appId + new SimpleDateFormat("YYYYMMDDHHMMSS").format(new Date()));
		String url = serverIp + functionNm;
		String str = send(url,pMap);
		return repeat(str,pMap,url);
	}


}