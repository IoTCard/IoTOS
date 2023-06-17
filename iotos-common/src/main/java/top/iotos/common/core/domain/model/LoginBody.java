package top.iotos.common.core.domain.model;

import java.util.HashMap;

/**
 * 用户登录对象
 * 
 * @author iotos.top
 */
public class LoginBody
{
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }


    public LoginBody(HashMap<String, Object> parameter){
        this.setUsername(parameter.get("username").toString());
        this.setPassword(parameter.get("password").toString());
        this.setCode(parameter.get("code").toString());
        this.setUuid(parameter.get("uuid").toString());
    }
}
