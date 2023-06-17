package top.iotos.common.core.domain.model;

import java.util.HashMap;

/**
 * 用户注册对象
 * 
 * @author iotos.top
 */
public class RegisterBody extends LoginBody
{

    public RegisterBody(HashMap<String, Object> parameter) {
        super(parameter);
    }
}
