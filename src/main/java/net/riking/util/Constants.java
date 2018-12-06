package net.riking.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public interface Constants {
	/**
	 * HttpServletRequest作用域中存储消息列表的键
	 */
	public static final String MESSAGES_KEY = "messages_alert";
	
	public static final String MAX_DATE = "9999-12-31";
	
	public static final String SESSION_CURRENT_USER = "sessionCurrentUser";
	/**
	 * HttpSession作用域中存储当前用户�?有模�?
	 */
	public  static final String SESSION_MODULES="sessionModules";
	
	public static final String SESSION_USER_RIGHTSLIST = "sessionRightsList";
	
	/**
	 * HttpSession作用域中存储当前用户�?有模块的功能权限
	 */
	public static final String SESSION_USER_MODULEFUN="sessionUserModuleFuns";
	/**
	 * HttpSession作用域中存储系统�?有模块的URL
	 */
	public static final String SESSION_URLS="sessionURL";
	
	public static final String SESSION_CURRENT_MODULE = "sessionCurrentModule";
	
	//上线后此处修改为域名
	public static final String localUrl = "http://localhost:8080/";
	
	public static final String GLOBAL_LOCK_PAGES = "GLOBAL_LOCK_PAGES";
	
	String SESSION_WINDOW_NAME="window_name";// set name of pop up window for every login 
	
    String CONST_INPUT_DATE_FORMAT = "yyyy-MM-dd";

    String CONST_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    String CONST_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    String CONST_TIME_FORMAT = "HH:mm";
    public static final String DATE_PATTERN = "yyyyMMdd";
	public static final String REPT_DATE_PATTERN = "yyMMdd";
    public String locale_language = "locale";
    
	public static final String stringZero = "0";
	public static final String stringOne = "1";
	public static final String stringTwo = "2";
	public static final Integer integerZero = 0;
	public static final Integer integerOne = 1;
	public static final BigDecimal zero = new BigDecimal(0);
	public static final BigDecimal percentTen = new BigDecimal(0.1);
	
	public static final String INTERFACE_FILE_TYPE = ".XML";
	
	public static final int MAX_RECORDS = 5000;
	/**
	 * 应用类型
	 */
	public static final String ACC = "ACC";
	
	/**
	 * 应用类型
	 */
	public static final String AMLNZIP = "AMLNZIP";
	
	/**
	 * 应用类型
	 */
	public static final String AMLTZIP = "AMLTZIP";
	
	/**
	 * 应用类型
	 */
	public static final String SHANZIP = "SHANZIP";
	
	/**
	 * 应用类型
	 */
	public static final String SHATZIP = "SHATZIP";
	
	/**
	 * 账户
	 */
	public static final String ACC_CA = "ACCCA";
	
	/**
	 * 收支�?
	 */
	public static final String ACC_CB = "ACCCB";
	
	/**
	 * 控制文件
	 */
	public static final String ACC_TT = "TT";
	
	/**
	 * 应用类型
	 */
	public static final String JSH = "JSH";
	
	/**
	 * 结汇-基础
	 */
	public static final String JSH_D = "JSHD";
	
	/**
	 * 结汇-管理
	 */
	public static final String JSH_F = "JSHF";
	
	/**
	 * 购汇-基础
	 */
	public static final String JSH_E = "JSHE";
	/**
	 * 购汇-管理
	 */
	public static final String JSH_G = "JSHG";
	/**
	 * 控制文件
	 */
	public static final String JSH_T = "T";
	
	public static final String CFA = "CFA";
	public static final String CFA_TT = "TT";
	
	/**
	 * 错误文件
	 */
	public static final String ERR = "ERR";
	/**
	 * 申报标志 - 未申�?

	 */
	public static final Integer NOT_SEND = 102001;
	
	/**
	 * 申报标志 - 申报完成
	 */
	public static final Integer SEND_COMPLETE = 102002;
	
	/**
	 * 审核标志 - 已审�?
	 */
	public static final Integer CONFIRM_YES = 101002;
	
	/**
	 * 审核标志 - 未审�?
	 */
	public static final Integer CONFIRM_NO = 101001;
	
	public static final String ACTIONTYPE_A="A";
	public static final String ACTIONTYPE_C="C";
	public static final String ACTIONTYPE_D="D";
	public static final String ACTIONTYPE_T="T";  //当导入的数据取消审核时，不确定该数据是删除还是修改，先改为变量T，如果导入的数据有�?�，是申报修改，否则申报删除
	
	public static class ReportType {
		
		/**
		 * 外汇账户内结�? -基础
		 */
		public static final ReportType G = new ReportType('G', '1');
		
		/**
		 * 外汇账户内购�?-基础
		 */
		public static final ReportType H = new ReportType('H', '2');
		
		public char type;
		public char inOutFlag;
		
		private ReportType(char type, char inOutFlag) {
			this.type = type;
			this.inOutFlag = inOutFlag;
		}
	}
	
	//北欧地区代码MAPPING的路�?
	public static final String AREAMAPPING = "net/config/areacode.properties";
	//北欧外�?? cnum过滤MAPPING
	public static final String CFAFILTERCNUMAPPING = "net/config/cfaCnumFilter.properties";
	//CFA主键MAPPING的路�?
	public static final String CFAKEYSMAP = "net/config/cfakeys.properties";
	
	public static final String CFAMAPPINGMAP = "net/config/cfaChangeBalance.properties";
	
	public static final String CFAKEYMAP = "net/config/cfakey.properties";
	
	public static final String COUNTRY = "net/config/importdata/country.properties";
	public static final String COUNTRYNUM = "net/config/importdata/countrynum.properties";
	
	public static final String BOP_TXFREE_CONFIGFILEPATH ="net/config/bop/boptxfreecodeconfig.properties";
	
	public static final String BOP_COUNTRY_CONFIGFILEPATH = "net/config/bop/countrynum.properties";
	
	//境外汇款导入DBF的临时表
	public static final String TEMP_TAB4_HTR = "T_TempDBFRMT";
	public static final String TEMP_TAB4_LRMT = "T_TEMPDBFCUST";
	
	public final static Map<String,String> cfaMap = new HashMap<String,String>() {{  
	    put("AA", "99");  
	    put("AB", "98");
	    put("AC", "97"); 
	    put("AD", "96"); 
	    put("AE", "95"); 
	    put("AF", "94"); 
	    put("AG", "93"); 
	    put("AH", "92"); 
	    put("AI", "91"); 
	    put("AJ", "90"); 
	    put("AK", "89"); 
	    put("AL", "88"); 
	    put("AM", "87"); 
	    put("AN", "86");
	    put("AP", "85");
	    put("AQ", "84");
	    put("BA", "83");
	    put("CA", "82");
	    put("DA", "81");
	    put("EA", "80");
	    put("FA", "79");
	}};
	public final static Map<String,String> cfaCodeMap = new HashMap<String,String>() {{  
	    put("99","AA");  
	    put("98","AB");
	    put("97","AC"); 
	    put("96","AD"); 
	    put("95","AE"); 
	    put("94","AF"); 
	    put("93","AG"); 
	    put("92","AH"); 
	    put("91","AI"); 
	    put("90","AJ"); 
	    put("89","AK"); 
	    put("88","AL"); 
	    put("87","AM"); 
	    put("86","AN");
	    put("85","AP");
	    put("84","AQ");
	    put("83","BA");
	    put("82","CA");
	    put("81","DA" );
	    put("80","EA");
	    put("79","FA");
	}};
	
	//总行代码
	public final static String HO="0000000";
	//报告机构�?在地区代码及名称
	public  static final String SESSION_CURRBRCA="sessionCurrbrca";
	//报告机构�?在地区代�?
	public  static final String SESSION_BRCA="sessionbrca";
	//报表单位
	public static final String SESSION_UNIT = "sessionunit";
	
	//报告时间
	public static final String SESSION_CURRDATE = "sessionCurrdate";
	
	public static final String PBOC_AMOUNT = "net/config/pboc/pboc.properties";
	
	public static final String PBOC_UNIT = "net/config/pboc/unit.properties";
	
	public static final String PBOC_CCY_MAPPING = "net/config/pboc/pbocCcyMapping.properties";
	
	public static final String PBOC_CCY_UNIT_MAPPING = "net/config/pboc/pbocexcelccyunit.properties";
	
	public static final String PBOC_CCY_FLAG_MAPPING = "net/config/pboc/pbocExcelTitle.properties";
	
	public static final String PBOC_AREA_MAPPING = "net/config/pboc/area.properties";
	//系统代号
	public static final String PBOC_SYSCODE = "A";
	
	public static final String PBOC_FREQMAPPING = "net/config/pboc/freq.properties";
	//顺序�?
	public static final String PBOC_NUM ="1";
	//币种转换
	public static final String PBOC_CCY_CONVERT = "net/config/pboc/pbocCcy.properties";
	//数据属�?�转�?
	public static final String PBOC_DATAPROPERTY_CONVERT = "net/config/pboc/pbocDataProperty.properties";
	
	/**
	 * PBOC 上报文件存放路径
	 */
	public static final String PBOC_IJFILEPATHNAME = "pboc/download/pboc/output/ij";
	
	public static final String PBOC_ZIPPATHNAME = "pboc/download/pboc/output/zip";
	
	public static final String UPLOADFILEPATH = "pboc/upload/file";
	
	public static final String UPLOADZIPPATH = "pboc/upload/zip";
	
	public static final String PBOC_TEMPLATE = "pboc/template";
	
	public static final String PBOC_TEMPLATEZIP = "pboc/template/zip";
	
	public static final String PBOC_TEMPLATEFILETOZIP = "pboc/template/filetozip";
	
	public static final String PBOC_EXCELOUTPUTTEMPPATH = "pboc/download/pboc/excel/template";
	
	public static final String PBOC_EXCELOUTPUTFILETOZIP = "pboc/download/pboc/excel/filetozip";
	
	public static final String PBOC_EXCELOUTPUTZIP = "pboc/download/pboc/excel/zip";
	
	public static final String PBOC_EXCELOUTPUT = "pboc/download/pboc/excel/ouput";
	
	/**
	 * CBRC 上报文件存放路径
	 */
	public static final String CBRC_IJFILEPATHNAME = "cbrc/download/pboc/output/ij";
	
	public static final String CBRC_ZIPPATHNAME = "cbrc/download/pboc/output/zip";
	
	public static final String CBRCUPLOADFILEPATH = "cbrc/upload/file";
	
	public static final String CBRCUPLOADZIPPATH = "cbrc/upload/zip";
	
	public static final String CBRC_TEMPLATE = "cbrc/template";
	
	public static final String CBRC_TEMPLATEZIP = "cbrc/template/zip";
	
	public static final String CBRC_TEMPLATEFILETOZIP = "cbrc/template/filetozip";
	
	public static final String CBRC_EXCELOUTPUTTEMPPATH = "cbrc/download/cbrc/excel/template";
	
	public static final String CBRC_EXCELOUTPUTFILETOZIP = "cbrc/download/cbrc/excel/filetozip";
	
	public static final String CBRC_EXCELOUTPUTZIP = "cbrc/download/cbrc/excel/zip";
	
	public static final String CBRC_EXCELOUTPUT = "cbrc/download/cbrc/excel/ouput";
	
	
	public static final String FSS_LOAFPATH = "fss/FssData/loaf";
	public static final String FSS_LOABPATH = "fss/FssData/loab";
	public static final String FSS_DEPBPATH = "fss/FssData/depb";
	public static final String FSS_DATAPATH = "fss/FssData/datapath";
	public static final String FSS_ZIPPATH = "fss/FssData/zippath";
	
	public static final String ROOT_PATH="";
	
//	public static final String ROOT_PATH="../";
}
