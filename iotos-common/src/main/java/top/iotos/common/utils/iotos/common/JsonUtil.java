package top.iotos.common.utils.iotos.common;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonUtil {

    /**
     * 读取JSON文件转换为字符串
     * @param filePath
     * @return
     */
    public static String readJsonFile(String filePath) {
        String content = null;
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            InputStream inputStream = resource.getInputStream();
            content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
       return content;
    }

}
