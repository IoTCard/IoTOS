package top.iotos.synApi.utils.iotos.time;



import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/***
 * 
 * @ClassName: VeDate   
 * @Description: 格式化时间帮助类(这里用一句话描述这个类的作用)   
 * @author zhangfeng
 * @date 2018年8月15日
 */
public class VeDate {
 /**
  * 获取现在时间
  * 
  * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
  */
 public static Date getNowDate() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String dateString = formatter.format(currentTime);
  ParsePosition pos = new ParsePosition(8);
  Date currentTime_2 = formatter.parse(dateString, pos);
  return currentTime_2;
 }

 /**
  * 获取现在时间
  * 
  * @return返回短时间格式 yyyy-MM-dd
  */
 public static Date getNowDateShort() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  String dateString = formatter.format(currentTime);
  ParsePosition pos = new ParsePosition(8);
  Date currentTime_2 = formatter.parse(dateString, pos);
  return currentTime_2;
 }
 
 /**
  * 获取现在时间
  * 
  * @return返回短时间格式 yyyyMMdd
  */
 public static String getNowDateShortNuber() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  String dateString = formatter.format(currentTime);
  System.out.println(dateString);
  String times[]=dateString.toString().split("-");
  return times[0]+times[1]+times[2];
 }

 /**
  * 获取现在时间
  *
  * @return返回短时间格式 yyyyMMdd
  */
 public static String getyyyyM() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M");
  String dateString = formatter.format(currentTime);
  return dateString;
 }

 public static String getyyyyMM() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
  String dateString = formatter.format(currentTime);
  return dateString;
 }

 /**
  * 获取现在时间
  * 
  * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
  */
 public static String getStringDate() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String dateString = formatter.format(currentTime);
  return dateString;
 }

 /**
  * 获取一个月的最后一天
  * @param yyyy_MM_dd
  * @return
  */
 public static String getEndDateOfMonth_yyyy_MM_DD(String yyyy_MM_dd) {// yyyy-MM-dd
  String[] Strs = yyyy_MM_dd.split("-");
  String str = Strs[0]+"-"+Strs[1]+"-";
  String month = Strs[1];
  // System.out.println("month "+month);
  //String yyyy_MM_dd = str.substring(0, 4)+"-"+month+"-01";
  int mon = Integer.parseInt(month);
  if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
   str += "31";
  } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
   str += "30";
  } else {
   if (isLeapYear(yyyy_MM_dd)) {
    str += "29";
   } else {
    str += "28";
   }
  }
  return str;
 }

 /**
  * 获取现在时间
  *
  * @return返回字符串格式 yyyy-MM-dd HH:mm:ss.SSS
  */
 public static String getStringDateSSS() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
  String dateString = formatter.format(currentTime);
  return dateString;
 }



 /**
  * 获取现在时间
  * 
  * @return 返回短时间字符串格式yyyy-MM-dd
  */
 public static String getStringDateShort() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  String dateString = formatter.format(currentTime);
  return dateString;
 }
 /**
  * 获取当前年月集合
  * @return
  */
 public static String[] getYyyyAndMm(){
	 String data=getStringDateShort();
	 return data.split("-");
 }

 /**
  * 获取时间 小时:分;秒 HH:mm:ss
  * 
  * @return
  */
 public static String getTimeShort() {
  SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
  Date currentTime = new Date();
  String dateString = formatter.format(currentTime);
  return dateString;
 }
 /**
  * 字符串转换成时间格式
  * @param
  * @return
  */
 public static String GetHH_MM_SS(String strDate){
	  String str=strDate.split(" ")[1];
	  return str;
 }
 

 /**
  * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
  * 
  * @param strDate
  * @return
  */
 public static Date strToDateLong(String strDate) {
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  ParsePosition pos = new ParsePosition(0);
  Date strtodate = formatter.parse(strDate, pos);
  return strtodate;
 }

 /**
  * 格式化 日期
  * @param strDate
  * @return
  */
 public static Date strToDateLong(String strDate,String Format) {
  SimpleDateFormat formatter = new SimpleDateFormat(Format);
  ParsePosition pos = new ParsePosition(0);
  Date strtodate = formatter.parse(strDate, pos);
  return strtodate;
 }


 /**
  * Date 格式化
  * @param strDate
  * @param Format
  * @return
  */
 public static String strToDate(String strDate,String Format) {
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format);
  return  simpleDateFormat.format(strToDateLong(strDate,Format));
 }



 /**
  * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
  * 
  * @param dateDate
  * @return
  */
 public static String dateToStrLong(Date dateDate) {
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String dateString = formatter.format(dateDate);
  return dateString;
 }

 /**
  * 将短时间格式时间转换为字符串 yyyy-MM-dd
  * 
  * @param dateDate
  * @param
  * @return
  */
 public static String dateToStr(Date dateDate) {
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  String dateString = formatter.format(dateDate);
  return dateString;
 }

 /**
  * 将短时间格式字符串转换为时间 yyyy-MM-dd 
  * 
  * @param strDate
  * @return
  */
 public static Date strToDate(String strDate) {
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  ParsePosition pos = new ParsePosition(0);
  Date strtodate = formatter.parse(strDate, pos);
  return strtodate;
 }

 /**
  * 得到现在时间
  * 
  * @return
  */
 public static Date getNow() {
  Date currentTime = new Date();
  return currentTime;
 }

 /**
  * 提取一个月中的最后一天
  * 
  * @param day
  * @return
  */
 public static Date getLastDate(long day) {
  Date date = new Date();
  long date_3_hm = date.getTime() - 3600000 * 34 * day;
  Date date_3_hm_date = new Date(date_3_hm);
  return date_3_hm_date;
 }

 /**
  * 得到现在时间
  * 
  * @return 字符串 yyyyMMdd HHmmss
  */
 public static String getStringToday() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
  String dateString = formatter.format(currentTime);
  return dateString;
 }

 /**
  * 得到现在小时
  */
 public static String getHour() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String dateString = formatter.format(currentTime);
  String hour;
  hour = dateString.substring(11, 13);
  return hour;
 }

 /**
  * 得到现在分钟
  * 
  * @return
  */
 public static String getTime() {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String dateString = formatter.format(currentTime);
  String min;
  min = dateString.substring(14, 16);
  return min;
 }

 /**
  * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
  * 
  * @param sformat
  *            yyyyMMddhhmmss
  * @return
  */
 public static String getUserDate(String sformat) {
  Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat(sformat);
  String dateString = formatter.format(currentTime);
  return dateString;
 }

 /**
  * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
  */
 public static String getTwoHour(String st1, String st2) {
  String[] kk = null;
  String[] jj = null;
  kk = st1.split(":");
  jj = st2.split(":");
  if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
   return "0";
  else {
   double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
   double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
   if ((y - u) > 0)
    return y - u + "";
   else
    return "0";
  }
 }

 /**
  * 得到二个日期间的间隔天数
  */
 public static String getTwoDay(String sj1, String sj2) {
  SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
  long day = 0;
  try {
   Date date = myFormatter.parse(sj1);
   Date mydate = myFormatter.parse(sj2);
   day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
  } catch (Exception e) {
   return "";
  }
  return day + "";
 }

 /**
  * 时间前推或后推分钟,其中JJ表示分钟.
  */
 public static String getPreTime(String sj1, String jj) {
  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String mydate1 = "";
  try {
   Date date1 = format.parse(sj1);
   long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
   date1.setTime(Time * 1000);
   mydate1 = format.format(date1);
  } catch (Exception e) {
  }
  return mydate1;
 }

 /**
  * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
  */
 public static String getNextDay(String nowdate, String delay) {
  try{
  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
  String mdate = "";
  Date d = strToDate(nowdate);
  long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
  d.setTime(myTime * 1000);
  mdate = format.format(d);
  return mdate;
  }catch(Exception e){
   return "";
  }
 }


 public static String getNextDay(String delay) {
  try{
   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
   String mdate = "";
   Date d = strToDate(getStringDateShort());
   long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
   d.setTime(myTime * 1000);
   mdate = format.format(d);
   return mdate;
  }catch(Exception e){
   return "";
  }
 }



 /**
  * 判断是否润年
  * 
  * @param ddate
  * @return
  */
 public static boolean isLeapYear(String ddate) {

  /**
   * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
   * 3.能被4整除同时能被100整除则不是闰年
   */
  Date d = strToDate(ddate);
  GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
  gc.setTime(d);
  int year = gc.get(Calendar.YEAR);
  if ((year % 400) == 0)
   return true;
  else if ((year % 4) == 0) {
   if ((year % 100) == 0)
    return false;
   else
    return true;
  } else
   return false;
 }

 /**
  * 返回美国时间格式 26 Apr 2006
  * 
  * @param str
  * @return
  */
 public static String getEDate(String str) {
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  ParsePosition pos = new ParsePosition(0);
  Date strtodate = formatter.parse(str, pos);
  String j = strtodate.toString();
  String[] k = j.split(" ");
  return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
 }

 /**
  * 获取一个月的最后一天
  * 
  * @param dat
  * @return
  */
 public static String getEndDateOfMonth(String dat) {// yyyyMM
  String str = dat.substring(0, 6);
  String month = dat.substring(4, 6);
 // System.out.println("month "+month);
  String yyyy_MM_dd = str.substring(0, 4)+"-"+month+"-01";
  int mon = Integer.parseInt(month);
  if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
   str += "31";
  } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
   str += "30";
  } else {
   if (isLeapYear(yyyy_MM_dd)) {
    str += "29";
   } else {
    str += "28";
   }
  }
  return str;
 }



 /**
  * 获取一个月的最后一天
  *
  * @param dat
  * @return
  */
 public static String getEndDateOfMonthTime(String dat) {// yyyyMM
  String str = dat.substring(0, 6);
  String month = dat.substring(4, 6);
  // System.out.println("month "+month);
  String yyyy_MM_dd = str.substring(0, 4)+"-"+month+"-01";
  int mon = Integer.parseInt(month);
  if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
   str += "31";
  } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
   str += "30";
  } else {
   if (isLeapYear(yyyy_MM_dd)) {
    str += "29";
   } else {
    str += "28";
   }
  }
  return str;
 }



 /**
  * 判断二个时间是否在同一个周
  * 
  * @param date1
  * @param date2
  * @return
  */
 public static boolean isSameWeekDates(Date date1, Date date2) {
  Calendar cal1 = Calendar.getInstance();
  Calendar cal2 = Calendar.getInstance();
  cal1.setTime(date1);
  cal2.setTime(date2);
  int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
  if (0 == subYear) {
   if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
    return true;
  } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
   // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
   if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
    return true;
  } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
   if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
    return true;
  }
  return false;
 }

 /**
  * 产生周序列,即得到当前时间所在的年度是第几周
  * 
  * @return
  */
 public static String getSeqWeek() {
  Calendar c = Calendar.getInstance(Locale.CHINA);
  String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
  if (week.length() == 1)
   week = "0" + week;
  String year = Integer.toString(c.get(Calendar.YEAR));
  return year + week;
 }

 /**
  * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
  * 
  * @param sdate
  * @param num
  * @return
  */
 public static String getWeek(String sdate, String num) {
  // 再转换为时间
  Date dd = VeDate.strToDate(sdate);
  Calendar c = Calendar.getInstance();
  c.setTime(dd);
  if (num.equals("1")) // 返回星期一所在的日期
   c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
  else if (num.equals("2")) // 返回星期二所在的日期
   c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
  else if (num.equals("3")) // 返回星期三所在的日期
   c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
  else if (num.equals("4")) // 返回星期四所在的日期
   c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
  else if (num.equals("5")) // 返回星期五所在的日期
   c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
  else if (num.equals("6")) // 返回星期六所在的日期
   c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
  else if (num.equals("0")) // 返回星期日所在的日期
   c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
  return new SimpleDateFormat("6").format(c.getTime());
 }

 /**
  * 根据一个日期，返回是星期几的字符串
  * 
  * @param sdate
  * @return
  */
 public static String getWeek(String sdate) {
  // 再转换为时间
  Date date = VeDate.strToDate(sdate);
  Calendar c = Calendar.getInstance();
  c.setTime(date);
  // int hour=c.get(Calendar.DAY_OF_WEEK);
  // hour中存的就是星期几了，其范围 1~7
  // 1=星期日 7=星期六，其他类推
  return new SimpleDateFormat("EEEE").format(c.getTime());
 }
 public static String getWeekStr(String sdate){
  String str = "";
  str = VeDate.getWeek(sdate);
  if("1".equals(str)){
   str = "星期日";
  }else if("2".equals(str)){
   str = "星期一";
  }else if("3".equals(str)){
   str = "星期二";
  }else if("4".equals(str)){
   str = "星期三";
  }else if("5".equals(str)){
   str = "星期四";
  }else if("6".equals(str)){
   str = "星期五";
  }else if("7".equals(str)){
   str = "星期六";
  }
  return str;
 }

 /**
  * 两个时间之间的天数
  * 
  * @param date1
  * @param date2
  * @return
  */
 public static long getDays(String date1, String date2) {
  if (date1 == null || date1.equals(""))
   return 0;
  if (date2 == null || date2.equals(""))
   return 0;
  // 转换为标准时间
  SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
  Date date = null;
  Date mydate = null;
  try {
   date = myFormatter.parse(date1);
   mydate = myFormatter.parse(date2);
  } catch (Exception e) {
  }
  long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
  return day;
 }

 /**
  * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
  * 此函数返回该日历第一行星期日所在的日期
  * 
  * @param sdate
  * @return
  */
 public static String getNowMonth(String sdate) {
  // 取该时间所在月的一号
  sdate = sdate.substring(0, 8) + "01";

  // 得到这个月的1号是星期几
  Date date = VeDate.strToDate(sdate);
  Calendar c = Calendar.getInstance();
  c.setTime(date);
  int u = c.get(Calendar.DAY_OF_WEEK);
  String newday = VeDate.getNextDay(sdate, (1 - u) + "");
  return newday;
 }

 /**
  * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
  * 
  * @param k
  *            表示是取几位随机数，可以自己定
  */

 public static String getNo(int k) {

  return getUserDate("yyyyMMddhhmmss") + getRandom(k);
 }

 /**
  * 返回一个随机数
  * 
  * @param i
  * @return
  */
 public static String getRandom(int i) {
  Random jjj = new Random();
  // int suiJiShu = jjj.nextInt(9);
  if (i == 0)
   return "";
  String jj = "";
  for (int k = 0; k < i; k++) {
   jj = jj + jjj.nextInt(9);
  }
  return jj;
 }

 /**
  * 
  * @param
  */
 public static boolean RightDate(String date) {

  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  ;
  if (date == null)
   return false;
  if (date.length() > 10) {
   sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  } else {
   sdf = new SimpleDateFormat("yyyy-MM-dd");
  }
  try {
   sdf.parse(date);
  } catch (ParseException pe) {
   return false;
  }
  return true;
 }
 
 
 
 
 /**
  * 时间前推或后推分钟,其中SS表示秒.
  */
 public static String getSTime(String sj1, String SS) {
  SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
  String mydate1 = "";
  try {
   Date date1 = format.parse(sj1);
   long Time = (date1.getTime() / 1000) + Integer.parseInt(SS) ;
   date1.setTime(Time * 1000);
   mydate1 = format.format(date1);
  } catch (Exception e) {
  }
  return mydate1;
 }
 
/**
 *  时间比较
 * @param date_1 
 * @param date_standard 比较标准
 * @return
 */
 public static boolean TimeComparison(String date_1, String date_standard) {
	 boolean bool = false;
	 SimpleDateFormat myFormatter = new SimpleDateFormat("HH:mm:ss");
  try {
		Date d1 = myFormatter.parse(date_1);
		Date d2 = myFormatter.parse(date_standard);
		bool =d1.compareTo(d2)>=0?true:false;
  } catch (Exception e) {
  }
  return bool;
 }




 /**
  *  日期比较
  * @param date_1
  * @param date_standard 比较标准
  * @return
  */
 public static boolean TimeComparison_yyyy_MM_dd(String date_1, String date_standard) {
  boolean bool = false;
  SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
  try {
   Date d1 = myFormatter.parse(date_1);
   Date d2 = myFormatter.parse(date_standard);
   bool =d1.compareTo(d2)>0?true:false;
  } catch (Exception e) {
  }
  return bool;
 }

 

 /**
 * 获取某月的最后一天
 * @Title:getLastDayOfMonth
 * @Description:
 * @param:@param year
 * @param:@param month
 * @param:@return
 * @return:String
 * @throws
 */
public static String getLastDayOfMonth(int year,int month)
{
	Calendar cal = Calendar.getInstance();
	//设置年份
	cal.set(Calendar.YEAR,year);
	//设置月份
	cal.set(Calendar.MONTH, month-1);
	//获取某月最大天数
	int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	//设置日历中月份的最大天数
	cal.set(Calendar.DAY_OF_MONTH, lastDay);
	//格式化日期
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String lastDayOfMonth = sdf.format(cal.getTime());
	
	return lastDayOfMonth;
}

 /**
  * 获取指定日期当月的第一天
  * @param dateStr
  * @param format
  * @return
  */
 public static String getFirstDayOfGivenMonth(String dateStr,String format){
  SimpleDateFormat sdf = new SimpleDateFormat(format);
  try {
   Date date = sdf.parse(dateStr);
   Calendar calendar = Calendar.getInstance();
   calendar.setTime(date);
   calendar.set(Calendar.DAY_OF_MONTH,1);
   calendar.add(Calendar.MONTH, 0);
   return sdf.format(calendar.getTime());
  } catch (ParseException e) {
   e.printStackTrace();
  }
  return null;
 }

 /**

  * 获取指定日期下个月的第一天
  * @param dateStr
  * @param format
  * @return
  */
 public static String getFirstDayOfNextMonth(String dateStr,String format){
  SimpleDateFormat sdf = new SimpleDateFormat(format);
  try {
   Date date = sdf.parse(dateStr);
   Calendar calendar = Calendar.getInstance();
   calendar.setTime(date);
   calendar.set(Calendar.DAY_OF_MONTH,1);
   calendar.add(Calendar.MONTH, 1);
   return sdf.format(calendar.getTime());
  } catch (ParseException e) {
   e.printStackTrace();
  }
  return null;
 }

 /**
  * @param datestr ⽇期字符串
  * @param day     相对天数，为正数表⽰之后，为负数表⽰之前
  * @return 指定⽇期字符串n天之前或者之后的⽇期
  */
 public static String getBeforeAfterDate(String datestr, int day) {
  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  Date olddate = null;
  try {
   df.setLenient(false);
   olddate = new Date(df.parse(datestr).getTime());
  } catch (ParseException e) {
   throw new RuntimeException("⽇期转换错误");
  }
  Calendar cal = new GregorianCalendar();
  cal.setTime(olddate);
  int Year = cal.get(Calendar.YEAR);
  int Month = cal.get(Calendar.MONTH);
  int Day = cal.get(Calendar.DAY_OF_MONTH);
  int NewDay = Day + day;
  cal.set(Calendar.YEAR, Year);
  cal.set(Calendar.MONTH, Month);
  cal.set(Calendar.DAY_OF_MONTH, NewDay);

  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  String lastDayOfMonth = sdf.format(cal.getTime());
  return lastDayOfMonth;
 }


 /**
  * 获取startDate日期后month月的日期
  * @param yyyy_MM_dd_HH_mm_ss 开始日期 yyyy-MM-dd HH:mm:ss
  * @param month  几个月后
  * @return
  */
 public static String getMonthDate(String yyyy_MM_dd_HH_mm_ss,int month)  {
  try {
   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   Date date1 = formatter.parse(yyyy_MM_dd_HH_mm_ss);
   LocalDateTime localDateTime = date1.toInstant()
           .atZone(ZoneId.systemDefault() )
           .toLocalDateTime().plusMonths(month);
   Date date = Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());

   String dateString = formatter.format(date);
   return dateString;
  }catch (Exception e){
   return null;
  }
 }


 /**
  *获取 多少秒后的时间
  * @param yyyy_MM_dd_HH_mm_ss
  * @param second
  * @return
  * @throws ParseException
  */
 public static String getNextSecond(String yyyy_MM_dd_HH_mm_ss,int second) throws ParseException {
  LocalDateTime parse = LocalDateTime.parse(yyyy_MM_dd_HH_mm_ss, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  LocalDateTime plusSeconds = parse.plusSeconds(second);// X 秒后
  //System.out.println(plusSeconds);
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  Date date = Date.from(plusSeconds.atZone( ZoneId.systemDefault()).toInstant());
  String dateString = formatter.format(date);
  return dateString;
 }

 /**
  * 获取时间分钟差
  * @param time yyyy-MM-dd HH:mm:ss
  * @param allowTime
  * @return
  */
 public static boolean compareTime(String time,int allowTime){
  boolean bool = false;
   try{
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date now = sf.parse(time);//传入时间
    Date date = new Date();//当前时间
    long diff = now.getTime() - date.getTime();
    long min = diff /60/1000;
    bool =(Math.abs(min) <= allowTime);
  }catch (Exception e){
    System.out.println("compareTime 异常 0 "+time+" " +allowTime);
   }
  return bool;
 }

 /**
  * 获取 monthsToAdd 月之后的 format 格式 时间
  * @param monthsToAdd
  * @param format
  * @return
  */
 public static String getMonth(int monthsToAdd,String format){
  LocalDate currentDate = LocalDate.now();
  LocalDate nextMonth = currentDate.plusMonths(monthsToAdd);
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
  return  nextMonth.format(formatter);
 }


 /**
  * 获取前 x 月份
  * @param x
  * @param format
  * @return
  */
 public static String[] getPreviousXMonth(int x,String format) {
  LocalDate currentDate = LocalDate.now();
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
  String[] months = new String[x];
  for (int i = 0; i < x; i++) {
   LocalDate previousMonth = currentDate.minusMonths(i + 1);
   String formattedMonth = previousMonth.format(formatter);
   months[i] = formattedMonth;
  }
  return months;
 }

 /**
  * 获取前 x 日期
  * @param x
  * @param format
  * @return
  */
 public static String[] getPreviousXDay(int x,String format) {
  LocalDate currentDate = LocalDate.now();
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
  String[] months = new String[x];
  for (int i = 0; i < x; i++) {
   LocalDate previousMonth = currentDate.minusDays(i + 1);
   String formattedMonth = previousMonth.format(formatter);
   months[i] = formattedMonth;
  }
  return months;
 }


 /**
  * 判断 timeString 是否在 X分钟内
  * @param timeString
  * @param xMinutes
  * @return
  */
 public static boolean isWithinXMinutes(String timeString, int xMinutes) {
  // 定义时间字符串的格式
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  // 解析时间字符串为LocalDateTime对象
  LocalDateTime currentTime = LocalDateTime.now();
  LocalDateTime compareTime = LocalDateTime.parse(timeString, formatter);
  // 判断compareTime是否在xMinutes分钟内
  LocalDateTime lowerBound = currentTime.minusMinutes(xMinutes);
  LocalDateTime upperBound = currentTime.plusMinutes(xMinutes);

  return compareTime.isAfter(lowerBound) && compareTime.isBefore(upperBound);
 }

 public static void main(String[] args) {
  System.out.println(VeDate.isWithinXMinutes("2023-05-23 15:28:00",15));
 }

}