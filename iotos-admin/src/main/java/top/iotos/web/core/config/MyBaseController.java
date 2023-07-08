package top.iotos.web.core.config;

import com.alibaba.fastjson2.JSON;
import top.iotos.common.config.LanguageConvert;
import top.iotos.common.constant.HttpStatus;
import top.iotos.common.core.controller.BaseController;
import top.iotos.common.core.domain.entity.SysUser;
import top.iotos.common.core.domain.model.LoginUser;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.common.utils.ServletUtils;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.common.utils.ip.IpUtils;
import top.iotos.common.utils.spring.SpringUtils;
import top.iotos.synApi.utils.iotos.web.AesEncryptUtil;
import top.iotos.framework.web.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;


public class MyBaseController extends BaseController
{
    @Resource
    private CardInfoMapper cardInfoMapper;


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String RetunnIfStr(boolean bool,String sMsg,String eMsg,Object obj){
        if(bool){
            return RetunnSuccess(obj,sMsg);
        }else {
            return RetunnError(eMsg);
        }
    }

    /**
     * 判断返回
     * @param bool
     * @param sMsg
     * @param eMsg
     * @param obj
     * @return
     */
    public Map<String, Object> RetunnIf(boolean bool,String sMsg,String eMsg,Object obj)
    {
        if(bool){
            return  RSuccess(obj,sMsg);
        }else {
            return  RError(eMsg);
        }
    }





    public HashMap<String, Object> getParameter(String pwdStr){
        return MQAide.getParameter(pwdStr);
    }


    /**
     * 追加当前账号 子级企业id
     * @param pwdStr
     * @return
     */
    public HashMap<String, Object> getParameterAddPermissions(String pwdStr){
        HashMap<String, Object> parameter = getParameter(pwdStr);
        SysUser user = getUser();
        String deptId = user.getDeptId().toString();
        if(!deptId.equals("100")){//非 总部进行权限过滤
            Map<String, Object> findMap =  new HashMap<String, Object>();
            findMap.put("deptId",deptId);
            String arrStr = cardInfoMapper.queryChildrenAreaInfo(findMap);
            if(arrStr!=null){
                String dept_idArr[] = arrStr.split(",");
                if(dept_idArr!=null){
                    List<String> find_dept_id = new ArrayList<>();
                    for (int i = 0; i < dept_idArr.length; i++) {
                        String f_dept_id = dept_idArr[i]!=null && dept_idArr[i].length()>0?dept_idArr[i]:null;
                        if(f_dept_id!=null){
                            find_dept_id.add(f_dept_id);
                        }
                    }
                    if(find_dept_id.size()>0){
                        parameter.put("dept_id",find_dept_id);
                    }
                }
            }
        }
        parameter.put("user",user);
        return parameter;
    }





    public SysUser getUser(){
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        return loginUser.getUser();
    }

    public SysUser getUser(String token){
        LoginUser loginUser =  SpringUtils.getBean(TokenService.class).getLoginUser(token);
        return loginUser.getUser();
    }

    public void setLoginLgCode(String languageCode){
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        loginUser.getUser().setLanguageCode(languageCode);
        SpringUtils.getBean(TokenService.class).setLoginUser(loginUser);
    }


    public String getDeptID(){
        return getUser().getDeptId().toString();
    }
    public String getDeptID(String token){
        return getUser(token).getDeptId().toString();
    }

    public String getCodeMsg(String code){
        return  LanguageConvert.getMessage(getLocale(null),code);
    }
    public String getCodeMsg(String token,String code){
        return  LanguageConvert.getMessage(getLocale(token),code);
    }

    public Locale getLocale(String token){
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        String lc = getLoginLgCode(token);
        if(lc.equalsIgnoreCase("zh-hans") || lc.equalsIgnoreCase("zh-cn")
                || lc.equalsIgnoreCase("zh-sg")|| lc.equalsIgnoreCase("zh-my")
                || lc.equalsIgnoreCase("zh-Hans-CN")|| lc.equalsIgnoreCase("zh-Hans-HK")){
            locale = Locale.SIMPLIFIED_CHINESE;
        } else if (lc.equalsIgnoreCase("zh-hant") || lc.equalsIgnoreCase("zh-hk")
                || lc.equalsIgnoreCase("zh-tw")|| lc.equalsIgnoreCase("zh-mo")) {
            locale = Locale.TRADITIONAL_CHINESE;
        }else{
            try {
                String lcArr[] = lc.split("-");
                if(lcArr.length>1){
                    String lCode = lcArr[0]+"-"+lcArr[1];
                    if(lCode.equalsIgnoreCase("zh-Hans")){
                        locale = Locale.SIMPLIFIED_CHINESE;
                    }else if(lCode.equalsIgnoreCase("zh-Hant")){
                        locale = Locale.TRADITIONAL_CHINESE;
                    }
                }else {
                    locale = Locale.ENGLISH;
                }
            }catch (Exception e){
                locale = Locale.ENGLISH;
            }
        }
        return locale;
    }



    public String getLoginLgCode(String token){
        if(token!=null){
            return getUser(token).getLanguageCode();//获取语言编码
        }else {
            return getUser().getLanguageCode();//获取语言编码
        }
    }





    /**
     * 成功返回
     * @param obj
     * @param msg
     * @return
     */
    public Map<String, Object> RSuccess(Object obj,String msg)
    {
        String deptId = getDeptID();
        msg = msg!=null?msg:"common.success";
        Map<String, Object> rMap =  new HashMap<String, Object>();
        rMap.put("code",HttpStatus.SUCCESS);
        msg = deptId.equals("100")?msg:"common.success";//分为内部返回和 外部返回
        rMap.put("msg",getCodeMsg(msg));
        rMap.put("deptId",deptId);
        rMap.put("data",obj);
        return rMap;
    }

    public Map<String, Object> RSuccess(Object obj,String token,String msg)
    {
        String deptId = getDeptID(token);
        msg = msg!=null?msg:"common.success";
        Map<String, Object> rMap =  new HashMap<String, Object>();
        rMap.put("code",HttpStatus.SUCCESS);
        msg = deptId.equals("100")?msg:"common.success";//分为内部返回和 外部返回
        rMap.put("msg",getCodeMsg(token,msg));
        rMap.put("deptId",deptId);
        rMap.put("data",obj);
        return rMap;
    }

    /**
     * my 成功返回函数
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected String RetunnSuccess(Object obj,String msg)
    {
        try {
            return AesEncryptUtil.encrypt(JSON.toJSONString(RSuccess(obj,msg)));
        }catch (Exception e){
            System.err.println(e);
            return getCodeMsg("common.encryErr");
        }
    }

    protected String RetunnSuccess(Object obj,String token,String msg)
    {
        try {
            return AesEncryptUtil.encrypt(JSON.toJSONString(RSuccess(obj,token,msg)));
        }catch (Exception e){
            System.err.println(e);
            return getCodeMsg("common.encryErr");
        }
    }



    /**
     * 失败返回
     * @param msg
     * @return
     */
    protected Map<String ,Object> RError(String msg)
    {
        String deptId = getDeptID();
        Map<String ,Object> rMap = new HashMap<>();
        msg = deptId.equals("100")?msg:"common.failed";//分为内部返回和 外部返回
        rMap.put("code",HttpStatus.ERROR);
        rMap.put("msg",msg);
        rMap.put("deptId",deptId);
        return rMap;
    }



    protected String RetunnError(String msg)
    {
        try {
            return AesEncryptUtil.encrypt(JSON.toJSONString(RError(msg)));
        }catch (Exception e){
            System.err.println(e);
            return getCodeMsg("common.encryErr");
        }
    }



    public String getIP(){
        return IpUtils.getIpAddr(ServletUtils.getRequest());
    }

}
