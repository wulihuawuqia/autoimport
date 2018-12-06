package net.riking.auto;

import net.riking.util.CommUtil;

import java.util.List;
import java.util.Map;

public class WipeData {
	
	public static void main(String [] args) {
		 try {
			//根据配置导出需要删除的表数据
			String path = ExportFileEntrance.class.getResource("/").getPath();
			Map<String, String> map = CommUtil.getMapPorpFile(path + "application-dev.properties");
			String sqlType = map.get("sqlType");
			List<String> list = WriteDataBase.readFile(path + "wipeData.properties", sqlType);
//			List<String> list = WriteDataBase.readFile("D:\\DBImport\\wipeData.properties");
//			Map<String, String> map = CommUtil.getMapPorpFile("D:\\DBImport\\application-dev.properties");
    		String url = map.get("url2");
    		String username = map.get("username2");
    		String password = map.get("password2");
			String driverClassName = map.get("driverClassName2");
			DatabaseUtil databaseUtil = new DatabaseUtil();
			databaseUtil.init(driverClassName, url, username, password);
			for(int i = 0; i < list.size(); i++) {
				  String table = list.get(i).split("\\=")[0];
				  String value = list.get(i).split("\\=")[1];
				  if("1".equals(value.trim())) {
					  WriteDataBase.deleteTable(table);
					  System.out.println("成功删除表:" + table);
				  }
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
