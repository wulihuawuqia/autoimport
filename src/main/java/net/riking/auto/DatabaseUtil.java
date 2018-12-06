package net.riking.auto;

import net.riking.util.UtilStr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUtil {
	
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseUtil.class);
    
    private static String DRIVER;
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    
    
    @SuppressWarnings("static-access")
	public void init(String DRIVER, String URL, String USERNAME, String PASSWORD) {
    	this.DRIVER = DRIVER;
    	this.URL = URL;
    	this.USERNAME = USERNAME;
    	this.PASSWORD = PASSWORD;
    }

    private static final String SQL = "SELECT * FROM ";// 数据库操

    /**
     * 获取数据库连�?
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
			Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get connection failure", e);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return conn;
    }

    /**
     * 关闭数据库连�?
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }

    /**
     * 获取数据库下的所有表�?
     */
    public static List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数�?
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到�?有的表名
            rs = db.getTables(null, null, null, new String[] { "TABLE" });
            while(rs.next()) {
            	if(rs.getString(3).indexOf("T_") != -1) {
            		tableNames.add(rs.getString(3));
            	}
            }
        } catch (SQLException e) {
            LOGGER.error("getTableNames failure", e);
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                LOGGER.error("close ResultSet failure", e);
            }
        }
        return tableNames;
    }

    /**
     * 获取表中�?有字段名�?
     * @param tableName 表名
     * @return
     */
    public static List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        //与数据库的连�?
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            pStemt.executeQuery();
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列�?
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
            	columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.error(tableName +" getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取表中�?有字段类�?
     * @param tableName
     * @return
     */
    public static List<String> getColumnTypes(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连�?
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            pStemt.executeQuery();
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列�?
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnTypes.add(rsmd.getColumnTypeName(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.error("getColumnTypes failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnTypes close pstem and connection failure", e);
                }
            }
        }
        return columnTypes;
    }

    /**
     * 获取表中字段的所有注�?
     * @param tableName
     * @return
     */
    public static List<String> getColumnComments(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连�?
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();//列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                	pStemt.close();
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }
    
    /**
     * 判断表中是否存在自增�?
     * @param tableName
     * @return
     */
    public static boolean isIdentity(String tableName) {
    	boolean flag = false;
    	Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = "select is_identity from sys.columns where object_ID=object_ID('"+tableName+"') and is_identity = 1";
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery();
            while (rs.next()) {
                if(null != rs.getString("is_identity") && rs.getString("is_identity").equals("1")){
                	flag = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                	pStemt.close();
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return flag;
    }
    
    /**
     * 根据表名获取表数
     * @param tableNames
     * @param columns
     * @param types
     * @return
     */
    public static List<Object> getDataByTables(String tableNames, String columns, List<String> types){
    	String sql = "select "+columns.replace("[", "").replace("]", "")+" from "+tableNames+""; // 查询sql
    	List<Object> retList = new ArrayList<Object>();
    	PreparedStatement pStemt = null;
    	ResultSet rs = null;
    	Connection conn = getConnection();
    	try{
    		pStemt = conn.prepareStatement(sql);
            rs = pStemt.executeQuery();
    		while (rs.next()) {
    			for(int i = 0; i < types.size(); i++) {
    				String type = types.get(i);
    				if(type.equals("bigint")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getLong(i+1));
    				}else if(type.equals("ntext") || type.equals("text") || type.equals("nvarchar(max)") || type.equals("nvarchar") || type.equals("varchar(max)") || type.equals("varchar") || type.equals("char")) {
    					retList.add(rs.getString(i+1));
    				}else if(type.equals("datetime") || type.equals("datetime2") || type.equals("smalldatetime")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getTimestamp(i+1));
    				}else if(type.equals("int") || type.equals("integer")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getInt(i+1));
    				}else if(type.equals("bit")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getBoolean(i+1));
    				}else if(type.equals("decimal") || type.equals("numeric")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getBigDecimal(i+1));
    				}else if(type.equals("float") || type.equals("double")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getDouble(i+1));
    				}else if(type.equals("date")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getDate(i+1));
    				}else if(type.equals("time")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getTime(i+1));
    				}else if(type.equals("tinyint")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getShort(i+1));
    				}else if(type.equals("real")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getFloat(i+1));
    				}else if(type.equals("blob")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getBlob(i+1));
    				}else if(type.equals("clob")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getClob(i+1));
    				}
    				if(i < types.size() - 1) {
    					retList.add("|");
    				}
    			}
    			retList.add("$#$");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	} finally {
            if (rs != null) {
                try {
                	pStemt.close();
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
    	return retList;
    }
    
    /**
     * 根据表名获取表数
     * @param tableNames
     * @param columns
     * @param types
     * @return
     */
    public static List<Object> getOracleDataByTables(String tableNames, String columns, List<String> types){
    	String sql = "select "+columns.replace("[", "").replace("]", "")+" from "+tableNames+""; // 查询sql
    	List<Object> retList = new ArrayList<Object>();
    	PreparedStatement pStemt = null;
    	ResultSet rs = null;
    	Connection conn = getConnection();
    	try{
    		pStemt = conn.prepareStatement(sql);
            rs = pStemt.executeQuery();
    		while (rs.next()) {
    			for(int i = 0; i < types.size(); i++) {
    				String type = types.get(i);
    				if(type.toLowerCase().equals("bigint")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getLong(i+1));
    				}else if(type.toLowerCase().equals("ntext") || type.toLowerCase().equals("text") || type.toLowerCase().equals("nvarchar(max)") || type.toLowerCase().equals("nvarchar") || type.toLowerCase().equals("varchar(max)") || type.toLowerCase().equals("varchar") || type.toLowerCase().equals("varchar2") || type.toLowerCase().equals("char")) {
    					retList.add(rs.getString(i+1));
    				}else if(type.toLowerCase().equals("datetime") || type.toLowerCase().equals("datetime2") || type.toLowerCase().equals("smalldatetime")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getTimestamp(i+1));
    				}else if(type.toLowerCase().equals("int") || type.toLowerCase().equals("integer")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getInt(i+1));
    				}else if(type.toLowerCase().equals("bit")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getBoolean(i+1));
    				}else if(type.toLowerCase().equals("decimal") || type.toLowerCase().equals("numeric") || type.toLowerCase().equals("number")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getBigDecimal(i+1));
    				}else if(type.toLowerCase().equals("float") || type.toLowerCase().equals("double")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getDouble(i+1));
    				}else if(type.toLowerCase().equals("date") || type.toLowerCase().equals("time")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getString(i+1));
    				}else if(type.toLowerCase().equals("tinyint")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getShort(i+1));
    				}else if(type.toLowerCase().equals("real")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getFloat(i+1));
    				}else if(type.toLowerCase().equals("blob")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getBlob(i+1));
    				}else if(type.toLowerCase().equals("clob")) {
    					retList.add(UtilStr.isEmpty(rs.getString(i+1)) ? "" : rs.getClob(i+1));
    				}
    				if(i < types.size() - 1) {
    					retList.add("|");
    				}
    			}
    			retList.add("$#$");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	} finally {
            if (rs != null) {
                try {
                	pStemt.close();
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
    	return retList;
    }

    /**
     * 组装列名�?
     * @param columns
     * @return
     */
	public static List<Object> getColumnByTables(List<String> columns) {
		List<Object> retList = new ArrayList<Object>();
		for(int i = 0; i < columns.size(); i++) {
			String column = columns.get(i);
			retList.add(column);
			if(i < columns.size() - 1) {
				retList.add(",");
			}
		}
		return retList;
	}

	/**
	 * 列名对应的类�?
	 * @param columnNames
	 * @param columnTypes
	 * @return
	 */
	public static Map<String, String> getColumnMatchType(List<String> columnNames, List<String> columnTypes) {
		Map<String, String> map = new HashMap<String, String>();
		for(int i = 0; i < columnNames.size(); i++) {
			map.put(columnNames.get(i).toUpperCase(), columnTypes.get(i));
		}
		return map;
	}
}