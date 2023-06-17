package top.iotos.web.controller.iotos.performTasks;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.iotos.common.core.domain.entity.SysDictData;
import top.iotos.common.core.domain.entity.SysUser;
import top.iotos.common.utils.iotos.service.PerformTaskUtil;
import top.iotos.common.utils.iotos.web.IoTOSTools;
import top.iotos.common.utils.poi.ExcelWrite;
import top.iotos.common.utils.poi.WriteCSV;
import top.iotos.system.service.ISysDictTypeService;
import top.iotos.system.service.iotos.performTasks.IPerformTasksService;
import top.iotos.web.core.config.MyBaseController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * 执行任务
 * @author iotos.top
 */
@RestController
@RequestMapping("/iotos/performTasks")
public class PerformTasksController extends MyBaseController
{
    @Resource
    private IPerformTasksService iPerformTasksService;
    @Resource
    public WriteCSV writeCSV;
    @Resource
    private ExcelWrite excelWrite;
    @Resource
    private PerformTaskUtil performTaskUtil;
    @Resource
    private IoTOSTools ioTOSTools;

    @Resource
    private ISysDictTypeService iSysDictTypeService;
    @PreAuthorize("@ss.hasPermi('iotos:performTasks:list')")
    @PostMapping(value = "/list", produces = { "application/json;charset=UTF-8" })
    public String list(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameterAddPermissions(pwdStr);
            return RetunnSuccess(iPerformTasksService.getList(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/performTasks/list  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }




    @PreAuthorize("@ss.hasPermi('iotos:performTasks:list')")
    @PostMapping(value = "/findFile", produces = { "application/json;charset=utf-8" })
    public String findFile(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameterAddPermissions(pwdStr);
            return RetunnSuccess(iPerformTasksService.findFile(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/performTasks/find  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 查看文件下载记录
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:performTasks:flieDownload')")
    @PostMapping(value = "/downloadList", produces = { "application/json;charset=utf-8" })
    public String downloadList(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameterAddPermissions(pwdStr);
            return RetunnSuccess(iPerformTasksService.downloadList(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/performTasks/downloadList  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 查询执行任务详情
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:performTasks:tasksDetailsList')")
    @PostMapping(value = "/tasksDetailsList", produces = { "application/json;charset=utf-8" })
    public String tasksDetailsList(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameterAddPermissions(pwdStr);
            return RetunnSuccess(iPerformTasksService.tasksDetailsList(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/performTasks/tasksDetailsList  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }

    /**
     * 查询执行任务详情 导出
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:performTasks:tasksDetailsExport')")
    @PostMapping(value = "/tasksDetailsExport", produces = { "application/json;charset=utf-8" })
    public String tasksDetailsExport(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameterAddPermissions(pwdStr);
            return RetunnIfStr(iPerformTasksService.tasksDetailsExport(parameter),"common.cmdSuccess","common.cmdFailed",null);
        }catch (Exception e){
            logger.error("<br/> /iotos/performTasks/tasksDetailsExport  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }



    @GetMapping(value = "/download")
    public void  download(@RequestParam String pwdStr, HttpServletResponse response,HttpServletRequest request) {
        String ip = getIP();
        if(!ioTOSTools.overclock(ip,"download",20)){
            try {
                HashMap<String, Object> parameter = getParameter(pwdStr);
                if (parameter.get("path") != null && parameter.get("token") != null && parameter.get("t_no") != null && parameter.get("fid") != null) {
                    String path = parameter.get("path").toString();
                    String t_no = parameter.get("t_no").toString();
                    String fid = parameter.get("fid").toString();
                    String token = parameter.get("token").toString();
                    SysUser user = getUser(token);
                    if (user != null) {
                        boolean bool = performTaskUtil.downloadAddRecord(user,t_no,ip,fid);
                        if(bool){
                            logger.info("<br/>  企业 {}  用户 ID {} 账号 {}  <br/> pwdStr = {} <br/> ip =  {} <br/> ",user.getDept().getDeptName(),user.getUserId(),user.getUserName(),pwdStr,ip);
                            File file2 = new File(""); //获取当前文件下载 地址
                            String filePath = file2.getCanonicalPath();
                            filePath +=WriteCSV.downloadUrl;
                            String Prefix = path.split("/")[1];//切割出下载的地址请求头
                            path = path.substring(Prefix.length()+2);
                            path = filePath+path; //拼接下载地址
                            excelWrite.download(path,response,"UTF-8",System.currentTimeMillis() + "_csv_");
                        }else {
                            logger.error("<br/>  企业 {}  用户 ID {} 账号 {}  <br/> pwdStr = {} <br/> ip =  {}  ;<br/> downloadAddRecord > bool {} <br/>",user.getDept().getDeptName(),user.getUserId(),user.getUserName(),pwdStr,ip,bool);
                        }
                    }
                } else {
                    logger.info("<br/> pwdStr = " + pwdStr + " ip =  " + ip + " 下载文件参数缺失");
                }
            } catch (Exception e) {
                logger.error("<br/> /iotos/performTasks/getName  <br/>  <br/> ip = {} <br/> e = {} <br/>", ip, e.getCause().toString());
            }
        }else {
            logger.error("<br/> /iotos/performTasks/getName  <br/>  <br/> ip = {} <br/> e = {} <br/>", ip,"访问超频！！");
        }
    }



    /**
     * 下载 转换【CSV 转 Excle】 （简单数据转换，不适合复杂的）
     * @param pwdStr
     * @return
     */
    @GetMapping(value = "/downloadConversion")
    public void  downloadConversion(@RequestParam String pwdStr, HttpServletResponse response,HttpServletRequest request) {
        String ip = getIP();
        if(!ioTOSTools.overclock(ip,"downloadConversion",20)){
            try {
                HashMap<String, Object> parameter = getParameter(pwdStr);
                if (parameter.get("path") != null && parameter.get("token") != null && parameter.get("t_no") != null && parameter.get("fid") != null) {
                    String path = parameter.get("path").toString();
                    String t_no = parameter.get("t_no").toString();
                    String fid = parameter.get("fid").toString();
                    String token = parameter.get("token").toString();
                    SysUser user = getUser(token);
                    if (user != null) {
                        boolean bool = performTaskUtil.downloadAddRecord(user,t_no,ip,fid);
                        if(bool){
                             logger.info("<br/>  企业 {}  用户 ID {} 账号 {}  <br/> pwdStr = {} <br/> ip =  {} <br/> ",user.getDept().getDeptName(),user.getUserId(),user.getUserName(),pwdStr,ip);
                             writeCSV.csvOrExcle(path,response);
                        }else {
                            logger.error("<br/>  企业 {}  用户 ID {} 账号 {}  <br/> pwdStr = {} <br/> ip =  {}  ;<br/> downloadAddRecord > bool {} <br/>",user.getDept().getDeptName(),user.getUserId(),user.getUserName(),pwdStr,ip,bool);
                        }
                    }
                } else {
                    logger.info("<br/> pwdStr = " + pwdStr + " ip =  " + ip + " 下载文件参数缺失");
                }
            } catch (Exception e) {
                logger.error("<br/> /iotos/performTasks/getName  <br/>  <br/> ip = {} <br/> e = {} <br/>", ip, e.getCause().toString());
            }
        }else {
            logger.error("<br/> /iotos/performTasks/getName  <br/>  <br/> ip = {} <br/> e = {} <br/>", ip,"访问超频！！");
        }
    }


    /**
     * 下载模板
     * @param pwdStr
     * @return
     */
    @GetMapping(value = "/downloadTemplate" )
    public void  downloadTemplate(@RequestParam String pwdStr, HttpServletResponse response,HttpServletRequest request) {
            String ip = getIP();
            if(!ioTOSTools.overclock(ip,"downloadTemplate",20)){
                try {
                HashMap<String, Object> parameter = getParameter(pwdStr);
                if (parameter.get("path") != null && parameter.get("token") != null) {
                    String path = parameter.get("path").toString();
                    String token = parameter.get("token").toString();
                    SysUser user = getUser(token);
                    if (user != null) {
                        List<SysDictData> data = iSysDictTypeService.selectDictDataByType("download_template");
                        if (data!=null)
                        {
                            String Prefix = path.split("/")[1];
                            String fyPath = path.substring(Prefix.length() + 2);    //切割出下载的地址请求头
                            boolean bool = false;
                            for (int i = 0; i < data.size(); i++) {//匹对是否是字典配置中 允许 下载文件名称
                                SysDictData dictData = data.get(i);
                                String value = dictData.getDictValue();
                                if(value.equals(fyPath)){
                                    bool = true;
                                    break;
                                }
                            }
                              logger.info("<br/> " + " 企业 " + user.getDept().getDeptName() + "  用户名 " + user.getUserId() + " " + user.getUserName() + " <br/> pwdStr = " + pwdStr + " <br/> ip =  " + ip + " <br/> ");
                              if(bool){
                                  writeCSV.csvOrExcle(path,response);
                              }
                        }
                    }
                } else {
                    logger.info("<br/> pwdStr = " + pwdStr + " ip =  " + ip + " 下载文件参数缺失");
                }
            } catch (Exception e) {
                logger.error("<br/> /iotos/performTasks/getName  <br/>  <br/> ip = {} <br/> e = {} <br/>", ip, e.getCause().toString());
            }
        }else {
            logger.error("<br/> /iotos/performTasks/getName  <br/>  <br/> ip = {} <br/> e = {} <br/>", ip,"访问超频！！");
        }
    }





}