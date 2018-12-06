package net.riking.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * 系统配置加密算法信息加载类
 *
 * <p>
 * 系统配置加密算法信息加载类
 * @author qing.zhang
 * @date 2017年5月15日 下午4:46:12
 * @see
 * @since 1.0
 */
public class ConfigUtils {

	private static Properties DEV_PROPERTIES = new Properties();
	private static Properties DATA_PROPERTIES = new Properties();
	private static Properties WIPEDATA_PROPERTIES = new Properties();

	static {
		Logger logger = Logger.getLogger(ConfigUtils.class);
		InputStream is = null;
		try {
			is = ConfigUtils.class.getClassLoader().getResourceAsStream("net/riking/config/application-dev.properties");
			DEV_PROPERTIES.load(is);
			is = ConfigUtils.class.getClassLoader().getResourceAsStream("net/riking/config/data.properties");
			DATA_PROPERTIES.load(is);
			is = ConfigUtils.class.getClassLoader().getResourceAsStream("net/riking/config/wipeData.properties");
			WIPEDATA_PROPERTIES.load(is);
			logger.info("配置文件加载成功");
		} catch (IOException e) {
			logger.error("初始化配置文件失败。 " + e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				logger.error(" 配置文件：encryConfig.properties关闭失败。" + e);
			}
		}
	}

	private ConfigUtils() {
	}

	/**
	 * 从属性文件获取键对应的值
	 * @param key
	 * @return key对应的value
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getProperty(Properties prop) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration enum1 = prop.propertyNames();//得到配置文件的名字
		while(enum1.hasMoreElements()) {
		     String strKey = (String) enum1.nextElement();
		     String strValue = prop.getProperty(strKey);
		     map.put(strKey, strValue);
		}
		return map;
	}

	public static Properties getDEV_PROPERTIES() {
		return DEV_PROPERTIES;
	}

	public static void setDEV_PROPERTIES(Properties dEV_PROPERTIES) {
		DEV_PROPERTIES = dEV_PROPERTIES;
	}

	public static Properties getDATA_PROPERTIES() {
		return DATA_PROPERTIES;
	}

	public static void setDATA_PROPERTIES(Properties dATA_PROPERTIES) {
		DATA_PROPERTIES = dATA_PROPERTIES;
	}

	public static Properties getWIPEDATA_PROPERTIES() {
		return WIPEDATA_PROPERTIES;
	}

	public static void setWIPEDATA_PROPERTIES(Properties wIPEDATA_PROPERTIES) {
		WIPEDATA_PROPERTIES = wIPEDATA_PROPERTIES;
	}

}
