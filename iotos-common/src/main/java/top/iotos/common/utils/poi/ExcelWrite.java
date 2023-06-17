package top.iotos.common.utils.poi;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

/**
 * 用于处理Excel写操作
 */
@Component
public class  ExcelWrite{

	/**
	 * 下载文件
	 * @param path
	 * @param response
	 * @param charsetName
	 * @param newName
	 * @throws IOException
	 */
	public void download(String path, HttpServletResponse response,String charsetName,String newName) throws IOException {
		charsetName = charsetName!=null?charsetName:"GBK";//ISO-8859-1 UTF-8 GBK
		// path是指欲下载的文件的路径。
		File file = new File(path);
		// 以流的形式下载文件。
		InputStream fis = new BufferedInputStream(new FileInputStream(path));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		// 清空response
		response.reset();
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(newName+file.getName(),charsetName));

		response.addHeader("Content-Length", "" + file.length());
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
	}


	/**
	 * 输出 写入excel
	 * @param response
	 * @param dataList
	 * @param cls
	 * @param <T>
	 */
	public static <T> void writeExcel(HttpServletResponse response, List<T> dataList, Class<T> cls){
		Field[] fields = cls.getDeclaredFields();
		List<Field> fieldList = Arrays.stream(fields)
				.filter(field -> {
					ExcelColumnAnnotation annotation = field.getAnnotation(ExcelColumnAnnotation.class);
					if (annotation != null && annotation.col() > 0) {
						field.setAccessible(true);
						return true;
					}
					return false;
				}).sorted(Comparator.comparing(field -> {
					int col = 0;
					ExcelColumnAnnotation annotation = field.getAnnotation(ExcelColumnAnnotation.class);
					if (annotation != null) {
						col = annotation.col();
					}
					return col;
				})).collect(Collectors.toList());

		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("转换数据");
		AtomicInteger ai = new AtomicInteger();
		{
			Row row = sheet.createRow(ai.getAndIncrement());
			AtomicInteger aj = new AtomicInteger();
			//写入头部
			fieldList.forEach(field -> {
				ExcelColumnAnnotation annotation = field.getAnnotation(ExcelColumnAnnotation.class);
				String columnName = "";
				if (annotation != null) {
					columnName = annotation.value();
				}
				Cell cell = row.createCell(aj.getAndIncrement());

				CellStyle cellStyle = wb.createCellStyle();
				cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

				Font font = wb.createFont();
				cellStyle.setFont(font);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(columnName);
			});
		}
		if (CollectionUtils.isNotEmpty(dataList)) {
			dataList.forEach(t -> {
				Row row1 = sheet.createRow(ai.getAndIncrement());
				AtomicInteger aj = new AtomicInteger();
				fieldList.forEach(field -> {
					Class<?> type = field.getType();
					Object value = "";
					try {
						value = field.get(t);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Cell cell = row1.createCell(aj.getAndIncrement());
					if (value != null) {
						if (type == Date.class) {
							cell.setCellValue(value.toString());
						} else {
							cell.setCellValue(value.toString());
						}
						cell.setCellValue(value.toString());
					}
				});
			});
		}
		//冻结窗格
		wb.getSheet("转换数据").createFreezePane(0, 1, 0, 1);
		//生成excel文件
		String now=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String exc ="Conversion-"+now+".xlsx";
		OutputStream os=null;
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
			response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(exc,"utf-8"));
			os=response.getOutputStream();
			response.flushBuffer();
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
