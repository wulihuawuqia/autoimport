package net.riking.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

public class CommUtil {
    private static Log log = LogFactory.getLog(CommUtil.class);
    
    /**
     * 获取文件map
     * @param filename
     * @return
     * @throws ConfigurationException
     */
    public static Map<String, String>  getMapPorpFile(String filePath) throws ConfigurationException{
    	return HelpUtil.getMapByPorpFile(filePath);
    }
    
    /**
     * 对象值传递值
     * @param ob 数据源对象
     * @param name 数据源属性名字
     * @param sobj  赋值对象
     * @param newName 赋值对象属性
     * @return
     * @throws Exception
     */
	public static Object getGetMethod(Object ob , String name,Object sobj,String newName)throws Exception{
		Method[] m = ob.getClass().getMethods();
		Object value="";
		for(int i = 0;i < m.length;i++){
			if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
				  value=m[i].invoke(ob);
			}
		}
		
		m = sobj.getClass().getMethods();
		for(int i = 0;i < m.length;i++){
			if(("set"+newName).toLowerCase().equals(m[i].getName().toLowerCase())){
				return m[i].invoke(sobj,new Object[]{value});
			}
		}
		
		
		return value;
	}
	
	
    /**
     * 获取实体所有字段名字
     * @param cHis 数据源实体类Class对象
     * @return
     */
	public static List<String> getFieldname(Class cHis){
		 List<String> lists=new ArrayList<String>();
		 Field fields[]=cHis.getDeclaredFields();//cHis 是实体类名称
	        try {
	            Field.setAccessible(fields, true);
	            for (int i = 0; i < fields.length; i++) {
	                lists.add(fields[i].getName());
	                //System.out.println(fields[i].getName() + "-> ");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return lists;
	}

    /**
     * 复制对象,生成一个与原对象相同的新实例
     * 
     * @param src 源实例
     * @return 新实例
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object clone(Object src) throws IOException, ClassNotFoundException {
        if (src == null) {
            return src;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(src);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }

    /**
     * 将int型数据转换成时间字符串(秒数转化为时 分 秒)
     * 
     * @param dur
     *            需要转换的int数据
     * @return 转换后的时间字符串
     */
    public static String getTime(int dur) {
        int hour = 0, min = 0, sec = 0;
        if (dur >= 3600) {
            hour = dur / 3600;
            dur = dur % 3600;
        }
        if (dur >= 60) {
            min = dur / 60;
            dur = dur % 60;
        }
        sec = dur;
        String tmp = "";
        if (hour > 0) {
            tmp += hour + ":";
        }
        if (min > 0) {
            if (min > 9) {
                tmp += min + ":";
            } else {
                tmp += "0" + min + ":";

            }
        } else {
            tmp += "00:";

        }
        if (sec > 0) {
            if (sec > 9) {
                tmp += sec;
            } else {
                tmp += "0" + sec;

            }
        } else {
            tmp += "00";

        }
        log.info(tmp);
        return tmp;
    }

    /**
     * 将字符串转换为整型
     * 
     * @param str 源字符串
     * @param i 转换异常时的默认值
     * @return 返回转换为整型结果
     */
    public static int toInt(String str, int i) {
        int result = 0;
        try {
            if (UtilStr.isEmpty(str)) {
                result = i;
            } else {
                result = Integer.parseInt(str);
            }
        } catch (Exception ex) {
            log.error("Error", ex);
            result = i;
        }
        return result;
    }

    /**
     * 将字符串转换成Integer类型
     *
     * @param str
     *            需要转换的字符串
     * @return 转换后的Integer类型结果
     */
    public static Integer toInteger(String str) {
        if (UtilStr.isEmpty(str)) {
            return null;
        }
        Integer result = null;
        try {
            result = Integer.valueOf(str.trim());
        } catch (Exception ex) {
            log.error("Error", ex);
        }
        return result;
    }

    /**
     * 将字符串转换成int类型
     *
     * @param str
     *            需要转换的字符串
     * @return 转换后的int类型结果
     */
    public static int toInt(String str) {

        return Integer.parseInt(str);

    }

    /**
     * 将字符串转换成Date类型
     *
     * @param str
     *            需要转换的字符串
     * @return 转换后的Date类型数据
     */
    public static java.sql.Date toDate(String str) {

        try {
            return java.sql.Date.valueOf(str);
        } catch (Exception ex) {
            log.error("Error", ex);
            return null;
        }

    }

    /**
     * 将Double转换成精确到小数点后两位的字符串
     *
     * @param d
     *            需要转换的Double类型数据
     * @return 转换后的String类型结果
     */
    public static String doublePoint(Double d) {
        if (d == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(d.doubleValue());
    }

    /**
     * 获得Double数据,当输入为null时,返回结果为0
     *
     * @param d
     *            输入的Double数据
     * @return 计算后的结果
     */
    public static Double getDouble(Double d) {
        if (d == null) {
            return new Double(0);
        }
        return d;
    }

    /**
     * 验证两个日期数据是否不等
     *
     * @param date1
     *            要比较的日期对象1
     * @param date2
     *            要比较的日期对象2
     * @return 如果相等返回false，反之true
     */
    public static boolean checkNotEqualDate(Date date1, Date date2) {
        if (date1 == null && date2 != null) {
            return true;
        }
        if (date2 == null && date1 != null) {
            return true;
        }
        if (date1 == date2) {
            return false;
        }
        if (date1.compareTo(date2) != 0) {
            return true;
        }
        return false;
    }

    /**
     * 验证两个字符串是否不等（忽略大小写）
     *
     * @param value1
     *            字符串1
     * @param value2
     *            字符串2
     * @return 相等返回false，反之true
     */
    public static boolean checkNotEqualStr(String value1, String value2) {
        if (value1 != null && !value1.equalsIgnoreCase(value2)) {
            return true;
        }
        if (value2 != null && !value2.equalsIgnoreCase(value1)) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符串是否是Ingeger数据类型
     *
     * @param value
     *            需验证的字符串
     * @return 如果是返回true，反之返回false
     */
    public static boolean checkIsNumber(String value) {
        boolean isNumber = true;
        try {
            new Integer(value);
        } catch (Exception e) {
            isNumber = false;
        }
        return isNumber;
    }

    /**
     * 获得列表中的第一个对象
     *
     * @param list
     *            List类型列表
     * @return 列表中的第一个对象，如果列表为空或为null返回null
     */
    @SuppressWarnings("rawtypes")
	public static Object getFirstObject(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    /**
     * 判断某字符串是否存在于当前字符串数组中
     * @param arr 字符串数组
     * @param element 字符串
     * @return boolean
     */
    public static boolean arrayContain(String[] arr, String element) {
    	if(null == arr || arr.length == 0) return false;
    	if(null == element) return false;
    	boolean result = false;
    	for(int i=0;i<arr.length;i++){
    		if(null == arr[i]) continue;
    		if(arr[i].equals(element)){
    			result = true;
    		}
    	}
    	return result;
    }

    /**
     * 将字符串数组转换成整型数组
     * @param sourceArray 字符串数组
     * @return Integer[]
     */
    public static Integer[] arrayConvert(String[] sourceArray){
    	if(null == sourceArray) return new Integer[0];
    	Integer[] result = new Integer[sourceArray.length];
    	for(int i=0;i<sourceArray.length;i++){
    		result[i] = Integer.parseInt(sourceArray[i]);
    	}
    	return result;
    }

    /** 将Sting类型的数字转化为double类型
     * @param str
     * @return
     */
    public static Double toDouble(String str) {
        if (UtilStr.isEmpty(str)) {
            return null;
        }
        Double result = null;
        try {
            result = Double.valueOf(str.trim());
        } catch (Exception ex) {
            log.error("Error", ex);
        }
        return result;
    }
    
    public static Map listToMap(List<?> objList, Class<?> clazz, String key) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        StringBuffer sb = new StringBuffer("get");
        sb.append((key.charAt(0) + "").toUpperCase());
        sb.append(key.substring(1));
//      System.out.print(sb.toString());
        try {

            Method method = clazz.getMethod(sb.toString(), null);

            for (Object obj : objList) {
               resultMap.put(method.invoke(obj, null).toString(), obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return resultMap;
        }

        return resultMap;
     }
    
    public static Connection getConn() throws Exception {
		Map<String, String> map = HelpUtil.getMapByPorpFile(Constants.ROOT_PATH + "config/hibernate.properties");
		String driver = null;
		String url = null;
		String username = null;
		String password = null;
		if(!UtilStr.isEmpty(map)&&map.size()>0){
			driver = map.get("amlDriver");
			url = map.get("amlDriverUrl");
			username = map.get("amluser");
			password = map.get("amlpassword");
		}
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	        if(!conn.isClosed())
	             System.out.println("Succeeded connecting to the Database!");
	    } catch (ClassNotFoundException e) {
	    	//数据库驱动类异常处理
            System.out.println("Can`t find the Driver!");   
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally{
	           System.out.println("数据库数据成功获取！！");
        }
	    return conn;
	}
}