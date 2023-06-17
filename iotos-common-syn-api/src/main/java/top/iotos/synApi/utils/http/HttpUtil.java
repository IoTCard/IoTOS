package top.iotos.synApi.utils.http;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;

public class HttpUtil {
    private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        cm.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * get 公共请求
     * @param url 地址
     * @param setHeader 设置一个请求头字段，有则覆盖，无则添加。
     * @param addHeader 添加一个新的请求头字段。（一个请求头中允许有重名字段。）
     * @return
     */
    public static String getCommon(String url, Map<String,String> setHeader, Map<String,String> addHeader) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            httpGet.setConfig(requestConfig);
            if (addHeader!=null){
                for(String key:addHeader.keySet()){
                    httpGet.addHeader(key, addHeader.get(key));
                }
            }
            if (setHeader!=null){
                for(String key:setHeader.keySet()){
                    httpGet.setHeader(key, setHeader.get(key));
                }
            }
            response = httpClient.execute(httpGet);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }




    public static String get(String url) {
        Map<String,String> setHeader = new HashMap<>();
        Map<String,String> addHeader = new HashMap<>();
        addHeader.put("Content-type", "application/json; charset=utf-8");
        setHeader.put("Accept", "application/json");
        return getCommon(url,setHeader,addHeader);
    }

    public static String get(String url, Map<String,String> headers) {
        Map<String,String> setHeader = headers;
        Map<String,String> addHeader = new HashMap<>();
        return getCommon(url,setHeader,addHeader);
    }

    public static String postCommon(String url, String jsonString, Map<String,String> setHeader, Map<String,String> addHeader) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setConfig(requestConfig);

            if (addHeader!=null){
                for(String key:addHeader.keySet()){
                    httpPost.addHeader(key, addHeader.get(key));
                }
            }
            if (setHeader!=null){
                for(String key:setHeader.keySet()){
                    httpPost.setHeader(key, setHeader.get(key));
                }
            }
            httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }



    public static String post(String url, String jsonString) {
        Map<String,String> setHeader = new HashMap<>();
        Map<String,String> addHeader = new HashMap<>();
        addHeader.put("Content-type", "application/json; charset=utf-8");
        setHeader.put("Accept", "application/json");
        return postCommon(url,jsonString,setHeader,addHeader);
    }


    public static String post(String url, String jsonString, Map<String,String> headers) {
        Map<String,String> setHeader = headers;
        Map<String,String> addHeader = new HashMap<>();
        addHeader.put("Content-type", "application/json; charset=utf-8");
        setHeader.put("Accept", "application/json");
        return postCommon(url,jsonString,setHeader,addHeader);
    }



    /**
     * 获取Json数据
     * @param request
     * @return
     */
    public static String getJsonParam(HttpServletRequest request) {

        String jsonParam = "";
        ServletInputStream inputStream = null;
        try {
            int contentLength = request.getContentLength();
            if (!(contentLength < 0)) {
                byte[] buffer = new byte[contentLength];
                inputStream = request.getInputStream();
                for (int i = 0; i < contentLength; ) {
                    int len = inputStream.read(buffer, i, contentLength);
                    if (len == -1) {
                        break;
                    }
                    i += len;
                }
                jsonParam = new String(buffer, "utf-8");
            }
        } catch (IOException e) {
            //System.out.println("参数转换成json异常 {\"+e+\"}");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //System.out.println("参数转换成json异常 {" + e + "}");
                }
            }
        }
        return jsonParam;
    }


    /**
     * json的数据格式 请求体 请求
     * @param url
     * @param body
     * @return
     * @throws Exception
     */
    public static String sendPostJson(String url, String body) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(body));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //System.out.println(response.getStatusLine().getStatusCode() + "\n");
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        //System.out.println(responseContent);
        response.close();
        httpClient.close();
        return responseContent;
    }

    /**
     * form-data post
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, Object> map) throws Exception {
        String result = "";
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(550000).setConnectTimeout(550000)
                .setConnectionRequestTimeout(550000).setStaleConnectionCheckEnabled(true).build();
        client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        URIBuilder uriBuilder = new URIBuilder(url);

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("Charset", "utf-8");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
            params.add(pair);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        try {
            response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException("创建连接失败" + e);
        } catch (IOException e) {
            throw new RuntimeException("创建连接失败" + e);
        }

        return result;
    }

    /**
     * form-data post
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, Object> map, Map<String,String> headers) throws Exception {
        String result = "";
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(550000).setConnectTimeout(550000)
                .setConnectionRequestTimeout(550000).setStaleConnectionCheckEnabled(true).build();
        client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        URIBuilder uriBuilder = new URIBuilder(url);

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("Charset", "utf-8");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        if (headers!=null){
            for(String key:headers.keySet()){
                httpPost.setHeader(key, headers.get(key));
            }
        }

        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
            params.add(pair);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        try {
            response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException("创建连接失败" + e);
        } catch (IOException e) {
            throw new RuntimeException("创建连接失败" + e);
        }

        return result;
    }


    /**
     * post xml 请求
     * @param urlStr
     * @param xmlString
     * @return
     */
    public static String postXml(String urlStr,String xmlString){
        String rResult = null;
        byte[] xmlData = xmlString.getBytes();
        DataInputStream input = null;
        ByteArrayOutputStream out = null;
        try{
            //获得到位置服务的链接
            URL url = new URL(urlStr);
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            //将xml数据发送到位置服务
            urlCon.setRequestProperty("Content-Type", "text/xml");
            urlCon.setRequestProperty("Content-length",String.valueOf(xmlData.length));
            DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());

            printout.write(xmlData);
            printout.flush();
            printout.close();
            input = new DataInputStream(urlCon.getInputStream());

            out = new ByteArrayOutputStream();
            byte[] bufferByte = new byte[256];
            int l = -1;
            int downloadSize = 0;
            while ((l = input.read(bufferByte)) > -1) {
                downloadSize += l;
                out.write(bufferByte, 0, l);
                out.flush();
            }
            rResult = out.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
                input.close();
            }
            catch (Exception ex) {

            }
        }
        return rResult;
    }


}
