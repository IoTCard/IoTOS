package top.iotos.common.utils.poi;

import org.jumpmind.symmetric.csv.CsvReader;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;

/**
 *-----------------------maven 依赖----------------------------
 * CSV解析依赖包
 * <dependency>
 *      <groupId>org.jumpmind.symmetric</groupId>
 *      <artifactId>symmetric-csv</artifactId>
 *      <version>3.5.19</version>
 * </dependency>
 *
 * APACHE POI包
 * <dependency>
 *      <groupId>org.apache.poi</groupId>
 *      <artifactId>poi-ooxml</artifactId>
 *      <version>3.17</version>
 * </dependency>
 *
 * -----------------------------------------------------------
 * @description
 */


@Component
public class CsvToXlsxUtil {

    private static final Logger log = LoggerFactory.getLogger(CsvToXlsxUtil.class);

    //CSV常用分隔符，如需动态扩展设置成配置项
    private static final char[] DELIMITERS = {
            ',',
            ';',
            '\001',
            ' ',
            '\t',
            '|',
            '#',
            '&'
    };




    /**
     * 读取CSV文件并写入到XLSX文件中，默认编码
     * @param csvFileAddress
     * @return
     */
    public static String csvToXLSX(String csvFileAddress) {
        return csvToXLSX(csvFileAddress,"GBK");
    }

    /**
     * 读取CSV文件并写入到XLSX文件中，指定CSV文件编码
     * @param csvFileAddress
     * @param charset
     * @return
     */
    public static String csvToXLSX(String csvFileAddress,String charset) {
        String xlsxFileAddress = "";
        FileOutputStream fileOutputStream = null;
        try {
            char delimiter = getDelimiter(csvFileAddress);
            //xlsxFileAddress = csvFileAddress.replace("csv","xlsx"); //xlsx file address
            xlsxFileAddress = csvFileAddress.split("\\.")[0]+".xls";
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet(getSheetName(csvFileAddress));
            int RowNum=-1;
            CsvReader csvReader = new CsvReader(csvFileAddress, delimiter, Charset.forName(charset));
            while (csvReader.readRecord()){
                RowNum++;
                XSSFRow currentRow=sheet.createRow(RowNum);
                for(int i=0;i<csvReader.getColumnCount();i++){
                    currentRow.createCell(i).setCellValue(csvReader.get(i));
                }
            }
            fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            return getFileName(xlsxFileAddress);
        } catch (Exception e) {
            log.error("CsvToXlsxUtil exception :" , e);
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                log.error("CsvToXlsxUtil close FileOutputStream exception :" , e);
            }
        }
        return getFileName(xlsxFileAddress);
    }


    /**
     * 设置excel文件的sheet名称
     * 获取CSV文件名作为Excel文件的sheet名称
     * @param path
     * @return
     */
    private static String getSheetName(String path){
        try {
            String[] file = getFileName(path).split("\\.");
            return file[0];
        }catch (Exception e){
            log.error("CsvToXlsxUtil get sheet name exception : ",e);
            return "Sheet";
        }
    }


    /**
     * 根据资源路径切割获取文件名
     * @param path
     * @return
     */
    private static String getFileName(String path){
        String[] paths = path.contains("\\")?path.split("\\\\"):path.split("/");
        return paths[paths.length-1];
    }

    /**
     * 常用CSV分隔符数组遍历资源第一行，分隔的字段数多的为资源分隔符
     * 异常情况下默认用’,‘作为分隔符
     * @param path 资源路径
     * @return
     */
    private static char getDelimiter(String path){
        BufferedReader br = null;
        char delimiter = ',';
        try {
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            CsvReader csvReader;
            int columCount = 0;
            for (char delimiterTest : DELIMITERS){
                csvReader = new CsvReader(getStringStream(line),delimiterTest,Charset.forName("UTF-8"));
                if(csvReader.readRecord()){
                    int newColumnCount = csvReader.getColumnCount();
                    if(newColumnCount>columCount){
                        columCount = newColumnCount;
                        delimiter = delimiterTest;
                    }
                }
            }
        } catch (Exception e) {
            log.error("CsvToXlsxUtil get delimiter exception :",e);
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                log.error("CsvToXlsxUtil get delimiter close BufferedReader exception :",e);
            }
        }
        return delimiter;
    }


    /**
     * 字符串转输入流
     * 把CSV文件第一行数据转成输入流
     * @param sInputString
     * @return
     */
    private static InputStream getStringStream(String sInputString){
        if (sInputString != null && !sInputString.equals("")){
            try{
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            }catch (Exception e){
                log.error("CsvToXlsxUtil get StringStream exception :",e);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String csvFile = "C:/D/gitflie/buzhiming/my-iot-os/upload/importCard/2151694b58524d40acd9e55985065397_cardImport.csv";
        System.out.println(csvToXLSX(csvFile));
    }
}