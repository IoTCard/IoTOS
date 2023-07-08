package top.iotos.common.utils.iotos.web;

import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.iotos.synApi.utils.http.HttpUtil;
import top.iotos.synApi.utils.iotos.common.GetMapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *  与 IoTOS-IM 通讯 工具
 */
@Component
public class IMUtils {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String imServiceIp ="http://localhost:8072";

    //消息类型
    public static final String MSG_TYPE_TEXT = "0";//文本
    public static final String MSG_TYPE_IMG = "1";//图片
    public static final String MSG_TYPE_VOICE = "2";//语音
    public static final String MSG_TYPE_VIDEO = "3";//视频
    public static final String MSG_TYPE_MUSIC = "4";//音乐
    public static final String MSG_TYPE_NEWS = "5";//图文
    //聊天类型
    public static final String CHAT_TYPE_UNKNOWN = "0";//未知
    public static final String CHAT_TYPE_PUBLIC = "1";//公聊
    public static final String CHAT_TYPE_PRIVATE = "2";//私聊
    //命令类型
    public static final String COMMAND_UNKNOW = "0";//文本
    public static final String COMMAND_HANDSHAKE_REQ = "1";//握手请求，含http的websocket握手请求
    public static final String COMMAND_HANDSHAKE_RESP = "2";//握手响应，含http的websocket握手响应
    public static final String COMMAND_AUTH_REQ = "3";//鉴权请求
    public static final String COMMAND_AUTH_RESP = "4";//鉴权响应
    public static final String COMMAND_LOGIN_REQ = "5";//登录请求
    public static final String COMMAND_LOGIN_RESP = "6";//登录响应
    public static final String COMMAND_JOIN_GROUP_REQ = "7";//登录请求
    public static final String COMMAND_JOIN_GROUP_RESP = "8";//*申请进入群组响应
    public static final String COMMAND_JOIN_GROUP_NOTIFY_RESP = "9";//进入群组通知
    public static final String COMMAND_EXIT_GROUP_NOTIFY_RESP = "10";//退出群组通知
    public static final String COMMAND_CHAT_REQ = "11";//聊天请求
    public static final String COMMAND_CHAT_RESP = "12";//聊天响应
    public static final String COMMAND_HEARTBEAT_REQ = "13";//心跳请求
    public static final String COMMAND_CLOSE_REQ = "14";//关闭请求
    public static final String COMMAND_CANCEL_MSG_REQ = "15";//发出撤消消息指令(管理员可以撤消所有人的消息，自己可以撤消自己的消息)
    public static final String COMMAND_CANCEL_MSG_RESP = "16";//收到撤消消息指令
    public static final String COMMAND_GET_USER_REQ = "17";//获取用户信息
    public static final String COMMAND_GET_USER_RESP = "18";//获取用户信息响应
    public static final String COMMAND_GET_MESSAGE_REQ = "19";//获取聊天消息
    public static final String COMMAND_GET_MESSAGE_RESP = "20";//获取聊天消息响应


    /**
     * 获取消息主体
     * @param chatType  聊天类型
     * @param cmd 命令类型
     * @param content 内容
     * @param from 来自谁
     * @param msgType 消息类型
     * @param to 发给谁
     * @return
     */
    public Map<String,Object> getSedMap(String chatType,String cmd,String content,String from,String msgType,String to){
        Map<String,Object> msg = new HashMap<>();
        msg.put("chatType",chatType);
        msg.put("cmd",cmd);
        msg.put("content",content);
        msg.put("from",from);
        msg.put("createTime",System.currentTimeMillis());
        msg.put("msgType",msgType);
        msg = GetMapUtil.putMap(msg,"to",to);
        return msg;
    }


    /**
     * 发送
     * @param map
     * @return
     */
    public String send(Map<String,Object> map){
        String url = imServiceIp+"/api/message/send";
        String str = HttpUtil.post(url, JSON.toJSONString(map));
        log.info("url {} \n map {} \n str {}",url,map,str);
        return str;
    }

    public String send(String chatType,String cmd,String content,String from,String msgType,String to){
        return send(getSedMap(chatType,cmd,content,from,msgType,to));
    }




}
