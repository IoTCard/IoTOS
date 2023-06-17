package top.iotos.common.utils.poi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;
import top.iotos.common.utils.file.FileUtils;
import top.iotos.synApi.utils.iotos.common.ListCompare;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 写入CSV文件
 * @Description:
 */
@Component
public class WriteCSV {

    public static final String downloadUrl = "/mnt/iotos/download/csv/";//下载文件位置


    @Resource
    private ExcelWrite excelWrite;
    @Resource
    private CsvToXlsxUtil csvToXlsxUtil;

    private int outSize = 50;//默认 多少条数据导出一次

    /**
     * 输出CSV 文件 重名 文件 追加写入
     * @param fileName 文件名
     * @param mapList 写入数据
     * @param withHeader 表头
     * @param OutUrl 写入地址 /a/ 默认 /mnt/iotos/download/
     */
    public  void  Write (String fileName , List<Map<String,Object>> mapList,String[]  withHeader,String OutUrl,String[]  keys){
        Write(fileName,mapList,withHeader,OutUrl,keys,"");
    }


    /**
     * 输出CSV 文件 重名 文件 追加写入
     * @param fileName 文件名
     * @param mapList 写入数据
     * @param withHeader 表头
     * @param OutUrl 写入地址 /a/ 默认 /mnt/iotos/download/
     * @param Flieurl
     */
    public  void  Write (String fileName , List<Map<String,Object>> mapList,String[]  withHeader,String OutUrl,String[]  keys,String Flieurl){
        Write(fileName,mapList,withHeader,OutUrl,keys,"",false);
    }

    /**
     * 输出CSV 文件 重名 文件 追加写入
     * @param fileName 文件名
     * @param mapList 写入数据
     * @param withHeader 表头
     * @param OutUrl 写入地址 /a/ 默认 /mnt/iotos/download/
     * @param Flieurl 指定再加一段 文件名 如 /upload/importCard
     * @param noAddress 是否不需要 增加 固定地址 部分  【/mnt/iotos/download/】
     */
    public  void  Write (String fileName , List<Map<String,Object>> mapList,String[]  withHeader,String OutUrl,String[]  keys,String Flieurl,boolean noAddress){

        try {
            File file2 = new File("");
            String filePath = file2.getCanonicalPath();
            if(OutUrl!=null){
                filePath +=OutUrl;
            }
            if(noAddress){
                filePath += Flieurl;
            }else{
                filePath +=downloadUrl+Flieurl;
            }
            File Url=new File(filePath+"/1.txt");//tomcat 路径
            FileUtils.mkdirsmy(Url);//创建文件夹
            File file = new File(filePath+"/"+fileName+".csv");
            FileOutputStream fos = null;
            CSVFormat csvFormat = null ;
            if (file.exists()) {
                csvFormat = CSVFormat.DEFAULT.withIgnoreHeaderCase();
                fos = new FileOutputStream(filePath+"/"+fileName+".csv",true);
            }else {
                //设置表头
                csvFormat = CSVFormat.DEFAULT.withHeader(withHeader);
                fos = new FileOutputStream(filePath+"/"+fileName+".csv");
            }
            if(fos!=null){
                OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");// GBK UTF-8
                CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat);
                //循环输出表格数据
                for (int j = 0; j < mapList.size(); j++) {
                    Map<String,Object> objectMap = mapList.get(j);
                    Object oarr[] = objectMap.values().toArray();
                    Object[] Wtarr = {};
                    for (int i = 0; i < keys.length; i++) {
                        Wtarr = ListCompare.StringArrAdd(Wtarr, objectMap.get(keys[i]));//数组添加
                    }
                    csvPrinter.printRecord(Wtarr);//最后一个写入
                }
                csvPrinter.flush();
                csvPrinter.close();
            }else{
                System.err.println(" [FileOutputStream = null !!!] ");
            }

        } catch (Exception e) {
            System.err.println("WriteCSV导出CSV数据异常！");
            System.out.println(e.getMessage());
        }

    }




    /**
     * 输出 操作数据
     * @param list 输出数据
     * @param fileName 文件名
     * @param description  操作描述
     * @param deptName 执行人
     * @param result 执行结果
     * @param outColumns   输出表头
     * @param keys  输出列
     */
    public void outCSV(List<Map<String, String>> list , String fileName, String description, String deptName, String result,String outColumns[],String keys[],String IdKey){

        List<Map<String, Object>> out_list = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> out_map= new HashMap<String, Object>();

            Map<String,String> objMap=list.get(i);
            String iccid =objMap.get(IdKey);

            out_map.put(IdKey,iccid);
            out_map.put("description",description);
            out_map.put("result",result);
            out_map.put("deptName",deptName);

            out_list.add(out_map);
            if ((i+1)%outSize==0 || (i+1)==list.size()){
                //执行导出
                if(out_list.size()>0){
                    Write(fileName,out_list,outColumns,null,keys);
                    out_list = new ArrayList<>();
                }

            }
        }

    }






    /**
     * 输出 操作数据
     * @param list 输出数据
     * @param fileName 文件名
     * @param description  操作描述
     * @param deptName 执行人
     * @param result 执行结果
     * @param outColumns   输出表头
     * @param keys  输出列
     */
    public void outCSV(List<Map<String, String>> list , String fileName, String description, String deptName, String result,String outColumns[],String keys[]){
        outCSV(list,fileName,description,deptName,result,outColumns,keys,"iccid");
    }



    /**
     * 输出 操作数据
     * @param list 输出数据
     * @param fileName 文件名
     * @param description  操作描述
     * @param deptName 执行人
     * @param result 执行结果
     * @param outColumns   输出表头
     * @param keys  输出列
     */
    public void outCSVObj(List<Map<String, Object>> list , String fileName, String description, String deptName, String result,String outColumns[],String keys[]){
        outCSVObj(list,fileName,description,deptName,result,outColumns,keys,"iccid");
    }


    /**
     * 输出 操作数据
     * @param list 输出数据
     * @param fileName 文件名
     * @param description  操作描述
     * @param deptName 执行人
     * @param result 执行结果
     * @param outColumns   输出表头
     * @param keys  输出列
     */
    public void outCSVObj(List<Map<String, Object>> list , String fileName, String description, String deptName, String result,String outColumns[],String keys[],String IdKey){

        List<Map<String, Object>> out_list = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> out_map= new HashMap<String, Object>();

            Map<String,Object> objMap=list.get(i);
            String iccid =objMap.get(IdKey).toString();

            out_map.put(IdKey,iccid);
            out_map.put("description",description);
            out_map.put("result",result);
            out_map.put("deptName",deptName);

            out_list.add(out_map);
            if ((i+1)%outSize==0 || (i+1)==list.size()){
                //执行导出
                if(out_list.size()>0){
                    Write(fileName,out_list,outColumns,null,keys);
                    out_list = new ArrayList<>();
                }

            }
        }

    }




    /**
     * 输出 操作数据
     * @param list 输出数据
     * @param fileName 文件名
     * @param description  操作描述
     * @param deptName 执行人
     * @param result 执行结果
     * @param outColumns   输出表头
     * @param keys  输出列
     * @param outSize  多少条数据进行一次输出
     */
    public void outCSVObj(List<Map<String, Object>> list , String fileName, String description, String deptName, String result,String outColumns[],String keys[],int outSize){

        List<Map<String, Object>> out_list = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> out_map= new HashMap<String, Object>();
            Map<String,Object> objMap=list.get(i);
            String iccid =objMap.get("iccid").toString();
            out_map.put("iccid",iccid);
            out_map.put("description",description);
            out_map.put("result",result);
            out_map.put("deptName",deptName);
            out_list.add(out_map);
            if ((i+1)%outSize==0 || (i+1)==list.size()){
                //执行导出
                if(out_list.size()>0){
                    Write(fileName,out_list,outColumns,null,keys);
                    out_list = new ArrayList<>();
                }

            }
        }
    }

    /**
     * 输出 操作数据
     * @param list 输出数据
     * @param fileName 文件名
     * @param outColumns   输出表头
     * @param keys  输出列
     * @param outSize  多少条数据进行一次输出
     */
    public void outCSVObj(List<Map<String, Object>> list , String fileName,String outColumns[],String keys[],int outSize){
        List<Map<String, Object>> out_list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> out_map= new HashMap<String, Object>();
            Map<String,Object> objMap=list.get(i);
            for (int j = 0; j <keys.length ; j++) {
                Object oVlue = objMap.get(""+keys[j]);
                String SVlue = oVlue!=null?oVlue.toString():"";
                out_map.put(""+keys[j],SVlue);
            }
            out_list.add(out_map);
            if ((i+1)%outSize==0 || (i+1)==list.size()){
                //执行导出
                if(out_list.size()>0){
                    Write(fileName,out_list,outColumns,null,keys);
                    out_list = new ArrayList<>();
                }

            }
        }

    }



    /**
     * 输出 操作数据
     * @param list 输出数据
     * @param fileName 文件名
     * @param outColumns   输出表头
     * @param keys  输出列
     * @param defOutColumns   默认输出 【默认】该批次都会写入的数据
     * @param outSize  多少条数据进行一次输出
     */
    public void outCSVObj(List<Map<String, Object>> list , String fileName,String outColumns[],String keys[],Map<String, Object> defOutColumns,int outSize){
        List<Map<String, Object>> out_list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> out_map= new HashMap<String, Object>();
            Map<String,Object> objMap=list.get(i);
            for (int j = 0; j <keys.length ; j++) {
                Object oVlue = objMap.get(""+keys[j]);
                String SVlue = oVlue!=null?oVlue.toString():"";
                out_map.put(""+keys[j],SVlue);
            }
            if(defOutColumns!=null){
                for(String key:defOutColumns.keySet()){
                    out_map.put(""+key,defOutColumns.get(key));
                }
            }
            out_list.add(out_map);
            if ((i+1)%outSize==0 || (i+1)==list.size()){
                //执行导出
                if(out_list.size()>0){
                    Write(fileName,out_list,outColumns,null,keys);
                    out_list = new ArrayList<>();
                }
            }
        }
    }


    /**
     * 输出 操作数据
     * @param list
     * @param keyName
     * @param fileName
     * @param outColumns
     * @param keys
     * @param defOutColumns
     * @param outSize
     */
    public void outCSVListStr(List<String> list,String keyName , String fileName,String outColumns[],String keys[],Map<String, Object> defOutColumns,int outSize){
        List<Map<String, Object>> out_list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> out_map= new HashMap<String, Object>();
            String str =list.get(i);
            out_map.put(keyName,str);
            if(defOutColumns!=null){
                for(String key:defOutColumns.keySet()){
                    out_map.put(""+key,defOutColumns.get(key));
                }
            }
            out_list.add(out_map);
            if ((i+1)%outSize==0 || (i+1)==list.size()){
                //执行导出
                if(out_list.size()>0){
                    Write(fileName,out_list,outColumns,null,keys);
                    out_list = new ArrayList<>();
                }
            }
        }
    }



    /**
     * 输出 操作数据
     * @param list 输出数据
     * @param fileName 文件名
     * @param outColumns   输出表头
     * @param keys  输出列
     * @param defOutColumns   默认输出 【默认】该批次都会写入的数据
     * @param outSize  多少条数据进行一次输出
     */
    public void outCSVStr(List<Map<String, String>> list , String fileName,String outColumns[],String keys[],Map<String, Object> defOutColumns,int outSize){
        List<Map<String, Object>> out_list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> out_map= new HashMap<String, Object>();
            Map<String,String> objMap=list.get(i);
            for (int j = 0; j <keys.length ; j++) {
                Object oVlue = objMap.get(""+keys[j]);
                String SVlue = oVlue!=null?oVlue.toString():"";
                out_map.put(""+keys[j],SVlue);
            }
            if(defOutColumns!=null){
                for(String key:defOutColumns.keySet()){
                    //System.out.println("key="+key+"and value=" +defOutColumns.get(key));
                    out_map.put(""+key,""+defOutColumns.get(key));
                }
            }
            out_list.add(out_map);
            if ((i+1)%outSize==0 || (i+1)==list.size()){
                //执行导出
                if(out_list.size()>0){
                    Write(fileName,out_list,outColumns,null,keys);
                    out_list = new ArrayList<>();
                }
            }
        }

    }




    /**
     * 读取
     * @param readPath 读取路径
     * @param redColumns 列名
     * @return
     */
    public static List<Map<String,Object>> readCSV(String readPath,List<String> redColumns)  {
        return readCSV(readPath,redColumns,false);
    }


    /**
     * 读取 CSV 是否只读 首行
     * @param readPath
     * @param redColumns
     * @param firstBool
     * @return
     */
    public static List<Map<String,Object>> readCSV(String readPath,List<String> redColumns,boolean firstBool)  {
        List<Map<String,Object>> Rlist = new ArrayList<>();
        File inFile = new File(readPath);
        try
        {
            boolean sign = redColumns!=null&&redColumns.size()>0?true:false;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile),"GB2312"));
            while(reader.ready())
            {
                String line = reader.readLine();
                StringTokenizer st = new StringTokenizer(line, ",");
                if (st.hasMoreTokens() && sign)
                {
                    Map<String,Object> obj = new HashMap<>();
                    for (int i = 0; i < redColumns.size(); i++) {
                        obj.put(""+redColumns.get(i),st.nextToken());
                    }
                    //System.out.println(obj);
                    Rlist.add(obj);
                    if(firstBool){
                        reader.close();
                    }
                }
                else
                {
                    redColumns = new ArrayList<>();
                    while (st.hasMoreTokens()){ // 判断是否已经到结尾
                        redColumns.add(st.nextToken()); // 打印下一个字段
                    }
                    sign = true;
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
        catch (IOException e)
        {
            if(!firstBool){
                System.err.println(e.getMessage());
            }
        }
        return Rlist;
    }





    /**
     * 字典获取
     * @param CarMap 写入Map
     * @param DictDataArr 字典 list
     * @param basis 获取依据 字段 与 dictValue 判断
     * @param fieldName 返回字段名称
     * @return
     */
    public static Map<String,Object> writeDic (Map<String,Object> CarMap,List<Map<String, Object>> DictDataArr,String basis,String fieldName){

        Map<String,Object> Rmap = new HashMap<String,Object>();
        String status_id = CarMap.get(basis)!=null?CarMap.get(basis).toString():null;
        boolean bool = false;
        if(status_id!=null){
            for (int i = 0; i < DictDataArr.size(); i++) {
                Map<String, Object>  dictData =  DictDataArr.get(i);
                String dictValue = dictData.get("dictValue").toString();
                if(dictValue.equals(status_id)){
                    CarMap.put(fieldName,dictData.get("dictLabel"));
                    bool = true;
                    break;
                }
            }
        }
        //字段 默认值
        if(!bool){
            CarMap.put(fieldName,"");
        }
        return CarMap;
    }


    /**
     * 下载 Csv Or Excle
     * @param path
     * @param response
     * @throws IOException
     */
    public void csvOrExcle(String path, HttpServletResponse response) throws IOException {
        csvOrExcle(path, response,true);
    }

    /**
     *
     * @param path
     * @param response
     * @param is_download 是否需要下载
     * @throws IOException
     */
    public void csvOrExcle(String path,HttpServletResponse response,boolean is_download) throws IOException {
        csvOrExcle(path, response,true,false);
    }


    /**
     * 拼接 解析 下载地址
     * @param path
     * @param doesNotParseBool
     * @return
     */
    public static String getDlUrl(String path,boolean doesNotParseBool) {
        try {
            //获取当前文件下载 地址
            File file2 = new File("");
            String filePath = file2.getCanonicalPath();
            filePath = filePath.replaceAll("\\\\","/"); //xlsx file address
            //切割出下载的地址请求头
            String Prefix = path.split("/")[1];
            if(!doesNotParseBool){//是否跳过 下载地址解析
                if(Prefix.equals("getOriginal")){

                }else{
                    filePath += downloadUrl;
                }
                path = path.substring(Prefix.length() + 2);
            }
            //拼接下载地址
            path = filePath + path;
        }catch (Exception e){

        }
        return path;
    }




    /**
     *
     * @param path
     * @param response
     * @param is_download
     * @param doesNotParseBool 是否需要下载
     * @throws IOException 是否跳过 下载地址解析
     */
    public void csvOrExcle(String path,HttpServletResponse response,boolean is_download,boolean doesNotParseBool) throws IOException {

        path = getDlUrl(path,doesNotParseBool);

        // path是指欲下载的文件的路径。
        String  pathArr[] = path.split("\\.");
        if(pathArr!=null && pathArr.length>0){
            String downloadUrl = pathArr[0] + ".xls";
            File file = new File(downloadUrl);
            if (file!=null && file.exists()) {
                // xls 文件 存在直接下载
                if(is_download){
                    excelWrite.download(downloadUrl, response, "UTF-8",System.currentTimeMillis() + "_xls_");
                }
            } else {
                csvToXlsxUtil.csvToXLSX(pathArr[0] + ".csv");
                if(is_download){// 下载
                    excelWrite.download(downloadUrl, response, "UTF-8",System.currentTimeMillis() + "_xls_");
                }
            }
        }
    }

    /**
     * 删除文件
     * @param file
     */
    public static void deleteFile(File file){
        if (!file.exists()){  //判断当前文件file对象指向的路径是否存在进入if表示文件或目录不存在
            return;
        }
        if (file.isFile()){  //判断单签File是否是一个文件，如果是文件删除， 否则不进入File表示File是一个文件夹需要递归
            file.delete();
            return;
        }
        File files[] = file.listFiles(); //获取当前文件中所有的子文件或子目录
        for (File f :
                files) {  //迭代当前File目录下所有的子文件或子文件夹
            deleteFile(f);  //根据当前目录中的一个子文件或子目录使用递归删除
        }
        file.delete();  //删除当前的空文件夹
    }

}
