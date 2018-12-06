package net.riking.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

public class HelpUtil {
	/**
	 * 读取配置文件的属性到Map
	 * 
	 * @param filePath
	 *            配置文件的路径
	 * @return 各种属性配置的Map
	 * @throws ConfigurationException
	 *             解析文件异常
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getMapByPorpFile(String filePath)
			throws ConfigurationException {
		Configuration config;
		config = new PropertiesConfiguration(filePath);
		Iterator<String> iter = config.getKeys();
		Map<String, String> map = new HashMap<String, String>();
		while (iter.hasNext()) {
			String key = iter.next().toString();
			map.put(key, config.getString(key));
		}
		return map;

	}
	
	/**
	 * 读取配置文件的属性到Map
	 * 
	 * @param filePath
	 *            配置文件的路径
	 * @return 各种属性配置的Map
	 * @throws ConfigurationException
	 *             解析文件异常
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getLinkedHashMapByPorpFile(String filePath)
			throws ConfigurationException {

		Configuration config;
		config = new PropertiesConfiguration(filePath);
		Iterator<String> iter = config.getKeys();

		Map<String, String> map = new LinkedHashMap<String, String>();

		while (iter.hasNext()) {

			String key = iter.next().toString();

			map.put(key, config.getString(key));

		}
		return map;

	}
	
	/**
	 * 将MAP的KEY已字符串的形式返回
	 * @param map 
	 * @param spiltchar 分隔字符串的字符
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getStringByMap(Map map, String spiltchar) {
		if (null == map || map.isEmpty())
			return null;
		Set keys = map.keySet();
		StringBuffer strBuf = new StringBuffer();
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			strBuf.append("'" + iter.next() + "'"+spiltchar);
		}
		return strBuf.substring(0, strBuf.length() - 1);
	}
	
	public static long dateDiff(Date d1,Date d2){
		long n1 = d1.getTime();
		long n2 = d2.getTime();
		long diff = Math.abs(n1-n2);
		diff/=3600*1000*24;
		return diff;
	}
	/**
	 * 对一个日期进行偏移
	 * 
	 * @param date
	 *            日期
	 * @param offset
	 *            偏移两
	 * @return 偏移后的日期
	 */
	public static Date addDayByDate(Date date, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, day + offset);
		return cal.getTime();
	}
	
	/**
	 * List转换为MAP
	 */
	public static Map listToMap(List<?> objList, Class<?> clazz, String key) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		StringBuffer sb = new StringBuffer("get");
		sb.append((key.charAt(0) + "").toUpperCase());
		sb.append(key.substring(1));
//		System.out.print(sb.toString());
		try {

			Method method = clazz.getMethod(sb.toString(), null);

			for (Object obj : objList) {
				resultMap.put(method.invoke(obj, null).toString().trim(), obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return resultMap;
		}

		return resultMap;
	}
	
	/**
	 * 将值为STRING的list转换为指定字符连接的字符
	 * 
	 * @param list
	 *            值为STRING的LIST对象
	 * @param flagChar
	 *            连接字符
	 * @return 将值为STRING的list转换为指定字符连接的字符
	 */
	public static String listToStr(List<String> list, String flagChar) {
		if (null == list || list.size() == 0)
			return null;
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			str.append(list.get(i) + flagChar);
		}
		return str.toString().substring(0, str.length() - 1);
	}

	/**
	 * 将一个用指定字符连接的字符串转换为LIST返回
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param flagChar
	 *            连接字符
	 * @return 转换后的LIST
	 */
	public static List<String> strToList(String str, String flagChar) {
		if (null == str || str.length() == 0)
			return null;
		List<String> list = new ArrayList<String>();
		String[] strArray = str.split(flagChar);
		for (int i = 0; i < strArray.length; i++) {
			list.add(strArray[i]);
		}
		return list;
	}

	/**
	 * 将Map转换为List
	 * 
	 * @param map
	 *            要转换的Map
	 * @return 转换后的结果list
	 */
	@SuppressWarnings("unchecked")
	public static List mapToList(Map map) {
		if (null == map)
			return null;
		Set keys = map.keySet();
		List<Object> list = new ArrayList<Object>();
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			list.add(map.get(iter.next().toString()));
		}
		return list;
	}

	/**
	 * 将Map转换为List并且排序
	 * 
	 * @param map
	 *            要转换的Map
	 * @return 转换后的结果list
	 */
	@SuppressWarnings("unchecked")
	public static List mapToListSort(Map map) {
		if (null == map)
			return null;
		List list = new ArrayList();
		Set keys = map.keySet();
		String[] array = new String[map.size()];
		int i = 0;
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			array[i] = iter.next().toString();
			i++;
		}
		Arrays.sort(array);
		for (int j = 0; j < array.length; j++)
			list.add(array[j]);
		return list;
	}
	
	/**
	 * 目录不存在，新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static boolean newFolder(String folderPath) {
		try {
			String splitStr = "";
			if (folderPath.indexOf("/") != -1)
				splitStr = "/";
			else
				splitStr = "\\";
			StringTokenizer st = new StringTokenizer(folderPath, splitStr);
			String folder = st.nextToken() + splitStr;
			String subFolder = folder;
			while (st.hasMoreTokens()) {
				folder = st.nextToken() + splitStr;
				subFolder += folder;
				File myFilePath = new File(subFolder);
				if (!myFilePath.exists())
					myFilePath.mkdir();
			}
			return true;
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}
	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹

		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();

		}
	}
}
