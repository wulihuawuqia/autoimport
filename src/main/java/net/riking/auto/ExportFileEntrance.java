package net.riking.auto;

import net.riking.util.CommUtil;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExportFileEntrance {
    
	public static void main(String[] args){
    	ExportFileEntrance exportFileEntrance = new ExportFileEntrance();
    	exportFileEntrance.exportFile();
    }
	
	@SuppressWarnings("static-access")
    public boolean exportFile() {
    	try{
//		     //从数据库中导出数据到文件
//		     Map<String, String> map = CommUtil.getMapPorpFile("D:\\DBImport\\application-dev.properties");
			  String path = ExportFileEntrance.class.getResource("/").getPath();
			  Map<String, String> map = CommUtil.getMapPorpFile(path + "application-dev.properties");
			  String sqlType = map.get("sqlType");
			  List<String> list = WriteDataBase.readFile(path + "data.properties", sqlType);
//			  //根据配置导出需要的表数据
//		      List<String> list = WriteDataBase.readFile("D:\\DBImport\\data.properties", sqlType);
			  String url = map.get("url1");
			  String username = map.get("username1");
			  String password = map.get("password1");
			  String driverClassName = map.get("driverClassName1");
			  String exportFilePath = map.get("exportFilePath");
			  DatabaseUtil databaseUtil = new DatabaseUtil();
			  databaseUtil.init(driverClassName, url, username, password);
			  List<String> tableNames = new ArrayList<String>();
			  for(int i = 0; i < list.size(); i++) {
				  String table = list.get(i).split("\\=")[0];
				  String value = list.get(i).split("\\=")[1];
				  if("1".equals(value.trim())) {
					  tableNames.add(table);
				  }
			  }
		      System.out.println("tableNames:" + tableNames.size());
		      //导出文件前，先清理之前的文件
		      File file = new File(exportFilePath);
		      if(null != file) {
		    	  File [] files = file.listFiles();
		    	  for(int i = 0; i < files.length; i++) {
		    		  files[i].delete();
				  }
		      }
		      for (String tableName : tableNames) {
		    	System.out.println("TABLE: " + tableName);
		      	List<String> columnNames = databaseUtil.getColumnNames(tableName);
		      	List<String> columnTypes = databaseUtil.getColumnTypes(tableName);
		      	System.out.println("ColumnNames:" + databaseUtil.getColumnNames(tableName));
		      	System.out.println("ColumnTypes:" + databaseUtil.getColumnTypes(tableName));
		      	//给表列名加工
		      	List<Object> column = databaseUtil.getColumnByTables(columnNames);
		      	List<Object> data = new ArrayList<Object>();
		      	//封装表数:
		      	if(sqlType.equals("1")) {
		      		data = databaseUtil.getDataByTables(tableName, columnNames.toString(), columnTypes);
		      	}else if(sqlType.equals("2")) {
		      		data = databaseUtil.getOracleDataByTables(tableName, columnNames.toString(), columnTypes);
		      	}else if(sqlType.equals("3")){
		      		
		      	}
		    	System.out.println(tableName+" 数据条数:"+data.size());
		    	boolean flag = WriteFile.writeDataTxt(exportFilePath, data, tableName, column, sqlType);
		    	System.out.println("写入文件:"+(flag == true ? "完成" : "失败"));
		      }
		      return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
    }
	
	public void tables() {
		try {
			Map<String, String> map = CommUtil.getMapPorpFile("D:\\DBImport\\application-dev.properties");
			String url = map.get("url2");
			String username = map.get("username2");
			String password = map.get("password2");
			String driverClassName = map.get("driverClassName2");
//			String exportFilePath = map.get("exportFilePath");
			DatabaseUtil databaseUtil = new DatabaseUtil();
			databaseUtil.init(driverClassName, url, username, password);
			List<String> tableNames = new ArrayList<String>();
			List<String> list = databaseUtil.getTableNames();
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i)+"="+"1");
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
