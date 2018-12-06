package net.riking.auto;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WriteFile {
	
	public static void main(String [] args) {
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时
    	calendar.setTime(new Date());    //得到某一
    	String  yestedayDate= new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    	System.out.println("===="+yestedayDate);
	}
	
	 /**
	  * 写数据到txt文件�?
	  * @param path	写入路径
	  * @param objList	数据
	  * @param tableName	文件名以表名命名
	  * @param columnTypes		写入的列�?
	  * @return
	  */
    public static boolean writeDataTxt(String path, List<Object> objList, String tableName, List<Object> columns, String sqlType) {
    	boolean flag = false;
    	Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时
    	calendar.setTime(new Date());    //得到某一
    	String  yestedayDate= new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    	String filePath = path+tableName+yestedayDate+".txt";
    	File file = new File(filePath);
    	OutputStreamWriter write = null;
    	BufferedWriter writer = null;
    	String encoding = "GBK";
    	if(sqlType.equals("1")) {
    		encoding = "UTF-8";
    	}
    	try {
    		if (!file.exists()) {
    			file.createNewFile();
    		}
	    	write = new OutputStreamWriter(new FileOutputStream(file), encoding);
	    	writer = new BufferedWriter(write);
	    	//写列�?
	    	if(null != columns) {
		    	for(Object column : columns) {
		    		writer.write(column.toString()); // 输出流写入到文件�?
		    	}
		    	writer.write("\n");
	    	}
	    	//写数�?
	    	for (Object obj : objList) {
	    		if(null != obj) {
	    			if(obj.toString().equals("$#$")) {
	    				writer.write("\n"); // 输出流写入到文件�?
	    			}else {
	    				writer.write(obj.toString().replaceAll("\r|\n|\t", "")); // 输出流写入到文件�?
	    			}
	    		}
	    	}
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}finally {
    		try {
				writer.close();
				flag = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	return flag;
    }

}
