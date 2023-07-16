package top.iotos.web.controller.iotos.connect;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.iotos.common.annotation.Log;
import top.iotos.common.core.domain.entity.SysUser;
import top.iotos.common.enums.BusinessType;
import top.iotos.synApi.utils.sync.ApiProcessor;
import top.iotos.system.service.iotos.card.ICardService;
import top.iotos.web.core.config.MyBaseController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 卡列表
 * @author iotos.top
 */
@RestController
@RequestMapping("/iotos/card")
public class CardController extends MyBaseController
{
    @Resource
    private ICardService iCardService;
    @Resource
    private ApiProcessor apiProcessor;


    @PreAuthorize("@ss.hasPermi('iotos:card:list')")
    @PostMapping(value = "/list", produces = { "application/json;charset=UTF-8" })
    public String list(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameterAddPermissions(pwdStr);
            return RetunnSuccess(iCardService.getList(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/card/list  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }




    @PreAuthorize("@ss.hasPermi('iotos:card:find')")
    @PostMapping(value = "/find", produces = { "application/json;charset=utf-8" })
    public String find(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameterAddPermissions(pwdStr);
            return RetunnSuccess(iCardService.find(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/card/find  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }




    @Log(title = "卡列表", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('iotos:card:add')")
    @PostMapping(value = "/save", produces = { "application/json;charset=utf-8" })
    public Map<String, Object> save(MultipartFile file) {
        try {
            return RetunnIf(iCardService.save(file, getUser()), "common.cmdSuccess","common.cmdFailed",null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/save  <br/> ip = {} <br/> e = {} <br/>", getIP(), e.getCause().toString());
        }
        return RError(null);
    }

    @Log(title = "卡列表", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('iotos:card:edit')")
    @PostMapping(value = "/edit", produces = {"application/json;charset=utf-8"})
    public Map<String, Object> edit(MultipartFile file,@RequestParam Map<String, String> map) {
        try {
            HashMap<String, Object> parameter = getParameter(map.get("pwdStr"));
            SysUser user = getUser();
            parameter.put("user", user);
            parameter.put("deptId", user.getDeptId());
            return RetunnIf(iCardService.update(file, user,parameter), "common.cmdSuccess","common.cmdFailed",null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/save  <br/> ip = {} <br/> e = {} <br/>", getIP(), e.getCause().toString());
        }
        return RError(null);
    }


    @Log(title = "卡列表", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('iotos:card:del')")
    @PostMapping(value = "/delete", produces = {"application/json;charset=utf-8"})
    public String delete (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameterAddPermissions(pwdStr);
            return RetunnIfStr(iCardService.delete(parameter),null,null,null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/delRoute  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    @Log(title = "卡列表-划卡", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('iotos:card:divideCard')")
    @PostMapping(value = "/divideCard", produces = {"application/json;charset=utf-8"})
    public String divideCard (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameterAddPermissions(pwdStr);
            return RetunnIfStr(iCardService.divideCard(parameter),"common.cmdSuccess","common.cmdFailed",null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/divideCard  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }



    @Log(title = "卡列表-划卡撤回", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('iotos:card:rollbackDivid')")
    @PostMapping(value = "/rollbackDivid", produces = {"application/json;charset=utf-8"})
    public String rollbackDivid (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameterAddPermissions(pwdStr);
            return RetunnIfStr(iCardService.rollbackDivid(parameter),"common.cmdSuccess","common.cmdFailed",null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/rollbackDivid  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    @PreAuthorize("@ss.hasPermi('iotos:card:export')")
    @PostMapping(value = "/export", produces = {"application/json;charset=utf-8"})
    public String export (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameterAddPermissions(pwdStr);
            return RetunnIfStr(iCardService.export(parameter),"common.cmdSuccess","common.cmdFailed",null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/export  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 查询卡列表分组
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:getGrouping')")
    @PostMapping(value = "/getGrouping", produces = {"application/json;charset=utf-8"})
    public String getGrouping (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameterAddPermissions(pwdStr);
            return RetunnSuccess(iCardService.getGrouping(parameter),null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/getGrouping  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }

    /**
     * 发送一键同步信息
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:synInfo')")
    @PostMapping(value = "/synInfo", produces = {"application/json;charset=utf-8"})
    public String synInfo (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.synInfo(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/synInfo  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 同步用量
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:synFlow')")
    @PostMapping(value = "/synFlow", produces = {"application/json;charset=utf-8"})
    public String synFlow (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.synFlow(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/synFlow  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    @PreAuthorize("@ss.hasPermi('iotos:card:synStatus')")
    @PostMapping(value = "/synStatus", produces = {"application/json;charset=utf-8"})
    public String synStatus (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.synStatus(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/synStatus  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    @PreAuthorize("@ss.hasPermi('iotos:card:synRealName')")
    @PostMapping(value = "/synRealName", produces = {"application/json;charset=utf-8"})
    public String synRealName (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.synRealName(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/synRealName  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    @PreAuthorize("@ss.hasPermi('iotos:card:synSession')")
    @PostMapping(value = "/synSession", produces = {"application/json;charset=utf-8"})
    public String synSession (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.synSession(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/synSession  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    @PreAuthorize("@ss.hasPermi('iotos:card:synActivateDate')")
    @PostMapping(value = "/synActivateDate", produces = {"application/json;charset=utf-8"})
    public String synActivateDate (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.synActivateDate(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/synActivateDate  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    @PreAuthorize("@ss.hasPermi('iotos:card:synImei')")
    @PostMapping(value = "/synImei", produces = {"application/json;charset=utf-8"})
    public String synImei (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.synImei(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/synImei  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * API 业务办理 （停复机、断开网、网络重置、灵活变更状态）
     * @param file
     * @param map
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:businessHandling')")
    @PostMapping(value = "/businessHandling", produces = {"application/json;charset=utf-8"})
    public Map<String, Object> businessHandling(MultipartFile file,@RequestParam Map<String, String> map) {
        try {
            HashMap<String, Object> parameter = getParameter(map.get("pwdStr"));
            SysUser user = getUser();
            parameter.put("user", user);
            parameter.put("deptId", user.getDeptId());
            return RetunnIf(iCardService.businessHandling(file, user,parameter), "common.cmdSuccess","common.cmdFailed",null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/businessHandling  <br/> ip = {} <br/> e = {} <br/>", getIP(), e.getCause().toString());
        }
        return RError(null);
    }


    /**
     * 查询API业务办理记录
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:getApiBusinessList')")
    @PostMapping(value = "/getApiBusinessList", produces = {"application/json;charset=utf-8"})
    public String getApiBusinessList(@RequestBody String pwdStr) {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            return RetunnSuccess(iCardService.getApiBusinessList(parameter), null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/businessHandling  <br/> ip = {} <br/> e = {} <br/>", getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 卡号编辑 [简要信息编辑]
     * @return
     */
    @Log(title = "卡列表", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('iotos:card:editCardPublic')")
    @PostMapping(value = "/editCardPublic", produces = {"application/json;charset=utf-8"})
    public String editCardPublic(@RequestBody String pwdStr) {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            return RetunnIfStr(iCardService.editCardPublic(parameter), "common.cmdSuccess","common.cmdFailed",null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/editCardPublic  <br/> ip = {} <br/> e = {} <br/>", getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }




    /**
     * 同步返回数据解析通用返回
     * @param rMap
     * @return
     */
    private String synCommonReturn(Map<String, Object> rMap){
        String status = rMap.get("status").toString();

        if(status.equals("200")){
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("retuenList",rMap.get("retuenList"));
            retMap.put("template",rMap.get("template"));
            return RetunnSuccess(retMap,"common.cmdSuccess");
        }else {
            String msg = rMap.get("msg").toString();
            return RetunnError(msg);
        }
    }

    /**
     * 查询 session记录
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:synSession')")
    @PostMapping(value = "/querySession", produces = {"application/json;charset=utf-8"})
    public String querySession (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameterAddPermissions(pwdStr);
            return RetunnSuccess(iCardService.querySession(parameter),null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/querySession  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }

    /**
     * 导出历史会话记录
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:cardSession:export')")
    @PostMapping(value = "/exportSession", produces = {"application/json;charset=utf-8"})
    public String exportSession (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameterAddPermissions(pwdStr);
            return RetunnIfStr(iCardService.exportSession(parameter),"common.cmdSuccess","common.cmdFailed",null);

        } catch (Exception e) {
            logger.error("<br/> /iotos/card/exportSession  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 获取卡号简要信息
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:list')")
    @PostMapping(value = "/getCard", produces = {"application/json;charset=utf-8"})
    public String getCard (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameterAddPermissions(pwdStr);
            return RetunnSuccess(iCardService.getCard(parameter),null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/getCard  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 冻结状态查询
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:queryFStatus')")
    @PostMapping(value = "/queryFStatus", produces = {"application/json;charset=utf-8"})
    public String queryFStatus (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.querySimManageStopRestartStatus(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/queryFStatus  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }

    /**
     * 资费详情实时查询
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:queryApiTariff')")
    @PostMapping(value = "/queryApiTariff", produces = {"application/json;charset=utf-8"})
    public String queryApiTariff (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.queryOfferingsDetail(iccid,null,parameter);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/queryApiTariff  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }

    /**
     * 单卡停机原因查询
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:querySimStopReason')")
    @PostMapping(value = "/querySimStopReason", produces = {"application/json;charset=utf-8"})
    public String querySimStopReason (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.querySimStopReason(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/querySimStopReason  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 单卡通信功能开通查询
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:querySimProduct')")
    @PostMapping(value = "/querySimProduct", produces = {"application/json;charset=utf-8"})
    public String querySimProduct (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.querySimCommunicationFunctionStatus(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/querySimProduct  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }

    /**
     * 物联卡区域限制状态查询
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:querySimRLStatus')")
    @PostMapping(value = "/querySimRLStatus", produces = {"application/json;charset=utf-8"})
    public String querySimRLStatus (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.regionLimitStatus(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/querySimRLStatus  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }

    /**
     * 物联卡区域限制区域查询
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:querySimRLArea')")
    @PostMapping(value = "/querySimRLArea", produces = {"application/json;charset=utf-8"})
    public String querySimRLArea (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.querySimRegionLimitArea(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/querySimRLArea  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }

    /**
     * 单卡开关机状态实时查询
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:queryOnOffStatus')")
    @PostMapping(value = "/queryOnOffStatus", produces = {"application/json;charset=utf-8"})
    public String queryOnOffStatus (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.queryOnOffStatus(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/queryOnOffStatus  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 物联卡机卡分离状态查询
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:queryCardBindStatus')")
    @PostMapping(value = "/queryCardBindStatus", produces = {"application/json;charset=utf-8"})
    public String queryCardBindStatus (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.queryCardBindStatus(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/queryCardBindStatus  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }

    /**
     * 单卡状态变更历史查询
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:querySimChangeHistory')")
    @PostMapping(value = "/querySimChangeHistory", produces = {"application/json;charset=utf-8"})
    public String querySimChangeHistory (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);
            String iccid = parameter.get("iccid").toString();
            Map<String, Object> rMap = apiProcessor.querySimChangeHistory(iccid,null);
            return synCommonReturn(rMap);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/querySimChangeHistory  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }

    @PreAuthorize("@ss.hasPermi('iotos:card:cardMatch')")
    @PostMapping(value = "/cardMatch", produces = {"application/json;charset=utf-8"})
    public String cardMatch (@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter = getParameter(pwdStr);

            return RetunnSuccess(iCardService.cardMatch(parameter),null);
        } catch (Exception e) {
            logger.error("<br/> /iotos/card/querySimChangeHistory  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>", pwdStr, getIP(), e.getCause().toString());
        }
        return RetunnError(null);
    }


}