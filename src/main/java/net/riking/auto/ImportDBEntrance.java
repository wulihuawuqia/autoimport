package net.riking.auto;

import net.riking.util.CommUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImportDBEntrance {

	protected final transient Logger logger = LogManager.getLogger(getClass());

	public static void main(String[] args){
		ImportDBEntrance importDBEntrance = new ImportDBEntrance();
		importDBEntrance.importDb();
    }
    
    @SuppressWarnings("static-access")
	public boolean importDb() {
    	try{
//    		Map<String, String> map = CommUtil.getMapPorpFile("D:\\DBImport\\application-dev.properties");
    		String path = net.riking.auto.ExportFileEntrance.class.getResource("/").getPath();
	    	Map<String, String> map = CommUtil.getMapPorpFile(path + "application-dev.properties");
    		String url = map.get("url2");
    		String username = map.get("username2");
    		String password = map.get("password2");
			String driverClassName = map.get("driverClassName2");
			String exportFilePath = map.get("exportFilePath");
			String sqlType = map.get("sqlType");
			Long branchId = Long.valueOf(map.get("branchId"));
			net.riking.auto.DatabaseUtil databaseUtil = new net.riking.auto.DatabaseUtil();
			databaseUtil.init(driverClassName, url, username, password);
			//存放目录下所有的文件名
			List<String> tableNames = new ArrayList<String>();
			File file = new File(exportFilePath);
			if(null != file && file.listFiles() != null) {
				File [] files = file.listFiles();
				for(int i = 0; i < files.length; i++) {
					tableNames.add(files[i].getName());
				}
			}
			for (String tableName : tableNames) {
				String table = tableName.substring(0, tableName.indexOf(".") - 8);
		      	List<String> columnNames = databaseUtil.getColumnNames(table);
		      	List<String> columnTypes = databaseUtil.getColumnTypes(table);
		      	Map<String, String> map1 = databaseUtil.getColumnMatchType(columnNames, columnTypes);
		      	List<String> data = net.riking.auto.WriteDataBase.readFile(exportFilePath + tableName, sqlType);
		      	//写入先清理
		      	//WriteDataBase.deleteTable(table);

                net.riking.auto.WriteDataBase.romoveExist(data, table);
		      	//写入数据到数据库
		      	if(data.size() >= 2) {
		      		boolean flag = false;
		      		if(sqlType.equals("1")) {
		      			flag = net.riking.auto.WriteDataBase.insertTable(table, data, map1, branchId);
		      		}else if(sqlType.equals("2")) {
		      			flag = net.riking.auto.WriteDataBase.insertOracleTable(table, data, map1, branchId);
		      		}else if(sqlType.equals("3")){
			      		
			      	}
					logger.info(tableName+" 导入数据库:"+(flag == true ? "完成" : "失败"));
					logger.info(tableName+" 数据条数:"+data.size());
		      	}else {
                    logger.info(tableName+" 未找到待导入数据！");
		      	}
			}
			return true;
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		return false;
    	}
    }
}
