package net.riking.auto;

import net.riking.util.DateUtil;
import net.riking.util.UtilStr;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteDataBase {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseUtil.class);
	
	public static void main(String [] args) {
		try {
//			List<String> list = readFile("D:\\data\\zxxt\\T_MBT_AUTH_PROTOCOL_HIS20180819.txt");
//			List<Long> list = Arrays.asList(new Long [] {1L,2L,3L,4L,5L,6L,7L,8L});
//			int PAGE_SIZE = 3;
//			//总记录数
//	        Integer totalCount = list.size();
//	        //分多少次处理
//	        Integer requestCount = totalCount / PAGE_SIZE;
//	        for (int i = 0; i <= requestCount; i++) {
//	            Integer fromIndex = i * PAGE_SIZE;
//	            //如果总数少于PAGE_SIZE,为了防止数组越界,toIndex直接使用totalCount即可
//	            int toIndex = Math.min(totalCount, (i + 1) * PAGE_SIZE);
//	            List<Long> subList = list.subList(fromIndex, toIndex);
//	            System.out.println(subList);
//	            //总数不到�?页或者刚好等于一页的时�??,只需要处理一次就可以�?出for循环�?
//	            if (toIndex == totalCount) {
//	                break;
//	            }
//	        }
			
			String s = "180709170816635, A, 6, , , henrrymbtdev, 2018-07-09 17:08:16.637, 1, 8, 1, henrrymbtdev, 2018-07-09 17:08:16.637, , , 1000000, 180709170545871, 10, 2018-07-09 00:00:00.0, , 1, 2018-07-09 00:00:00.0, 0, 11111, 2018-07-10 00:00:00.0, 111111, 1111, 2018-07-09 00:00:00.0, 2018-07-28 00:00:00.0, 111, 1111111, 123, 10, 111111111";
			System.out.println(s.split(""));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 将数据插入到表中
	 * @param tableName
	 * @param data
	 * @param map
	 * @return
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public static boolean insertTable(String tableName, List<String> data, Map<String, String> map, Long branchId) throws SQLException, ParseException {
		boolean falg = false;
		//判断表是否存在自增长�?
		boolean identity = DatabaseUtil.isIdentity(tableName);
		//拼装列头
		String [] column = data.get(0).split(",");
		String str = ""; String col = "";
		for(int i = identity == true ? 1 : 0; i < column.length; i++) {
			col += column[i];
			str += "?";
			if(i < column.length - 1) {
				str += " ,";
				col += " ,";
			}
		}
		String insertSql = "insert into "+tableName+" ("+col+") values(";
		insertSql += str + ")";
		//获取数据�?
		Connection conn = DatabaseUtil.getConnection();
        PreparedStatement pStemt = null;
        pStemt = conn.prepareStatement(insertSql);
		try{
	        //每次批量提交1000
			int PAGE_SIZE = 1000;
			//总记录数
	        Integer totalCount = data.size();
	        //分多少次处理
	        Integer requestCount = totalCount / PAGE_SIZE;
	        for (int i = 0; i <= requestCount; i++) {
	            Integer fromIndex = i * PAGE_SIZE;
	            //如果总数少于PAGE_SIZE,为了防止数组越界,toIndex直接使用totalCount即可
	            int toIndex = Math.min(totalCount, (i + 1) * PAGE_SIZE);
	            List<String> subList = data.subList(fromIndex, toIndex);
	            for(int j = i == 0 ? 1 : 0; j < subList.size(); j++) {
	            	String [] value = StringUtils.splitByWholeSeparatorPreserveAllTokens(subList.get(j),"|");
//	            	String [] value = subList.get(j).split("\\|", column.length);
//	            	System.out.println("##"+value.length+"==="+column.length);
//	            	System.out.println("@@"+value[0]+"=="+value[1]+"=="+value[2]+"=="+value[3]+"=="+"=="+value[4]+"=="+column.length+"==="+value.length);
		            	for(int k = identity == true ? 1 : 0; k < column.length; k++) {
		            		String type = map.get(column[k]);
		            		int index = identity == true ? k : k+1;
		            		/*对于创建人用户名(createdBy) System、机构ID（BRANCH_ID）、数据来源 （DATA_SOURCE）*/
                            if (column[k].toUpperCase().equals("createdBy".toUpperCase())) {
                                pStemt.setString(index, "System");
                            } else if(column[k].toUpperCase().equals("BRANCH_ID".toUpperCase()) || column[k].toUpperCase().equals("BRANCHID".toUpperCase())) {
                                pStemt.setLong(index, branchId);
                            } else if(column[k].toUpperCase().equals("DATA_SOURCE".toUpperCase()) || column[k].toUpperCase().equals("DATASOURCE".toUpperCase())) {
                                pStemt.setInt(index, 1);
                            } else if(column[k].toUpperCase().equals("createdTime".toUpperCase())
                                    || column[k].toUpperCase().equals("lastModifiedTime".toUpperCase())
                                    ) {
                                pStemt.setTimestamp(index, new Timestamp(System.currentTimeMillis()));
                            }  else if(column[k].toUpperCase().equals("invalid".toUpperCase())) {
                                pStemt.setInt(index, 0);
                            } else if(column[k].toUpperCase().equals("checkBy".toUpperCase())
                                    || column[k].toUpperCase().equals("submitBy".toUpperCase())
                                    ) {
                                pStemt.setNull(index, Types.VARCHAR);
                            } else if(column[k].toUpperCase().equals("lastModifiedBy".toUpperCase())) {
                                pStemt.setString(index, "System");
                            } else if(column[k].toUpperCase().equals("checkTime".toUpperCase())
                                    || column[k].toUpperCase().equals("submitTime".toUpperCase())
                                    ) {
                                pStemt.setNull(index, Types.TIME);
                            } else if(column[k].toUpperCase().equals("ACTION_TYPE".toUpperCase())
									|| column[k].toUpperCase().equals("ACTIONTYPE".toUpperCase())
                                    || column[k].toUpperCase().equals("LIST_ACTION_TYPE".toUpperCase())
									|| column[k].toUpperCase().equals("LISTACTIONTYPE".toUpperCase())
                                    ) {
                                pStemt.setString(index, "A");
                            } else if(column[k].toUpperCase().equals("DATA_STATUS".toUpperCase())
									|| column[k].toUpperCase().equals("DATASTATUS".toUpperCase())
                                    || column[k].toUpperCase().equals("LIST_DATA_STATUS".toUpperCase())
									|| column[k].toUpperCase().equals("LISTDATASTATUS".toUpperCase())
                                    ) {
                                //待提交
                                pStemt.setInt(index, 1);
                            } else {
                                if(type.toLowerCase().equals("bigint")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setLong(index, Long.valueOf(value[k]));
                                    }else {
                                        pStemt.setNull(index, Types.DOUBLE);
                                    }
                                }else if(type.toLowerCase().equals("ntext") || type.toLowerCase().equals("text") || type.toLowerCase().equals("nvarchar(max)") || type.toLowerCase().equals("nvarchar") || type.toLowerCase().equals("varchar(max)") || type.toLowerCase().equals("varchar") || type.toLowerCase().equals("varchar2") || type.toLowerCase().equals("char")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setString(index, value[k]);
                                    }else {
                                        pStemt.setNull(index, Types.VARCHAR);
                                    }
                                }else if(type.toLowerCase().equals("time") || type.toLowerCase().equals("datetime") || type.toLowerCase().equals("datetime2") || type.toLowerCase().equals("smalldatetime")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setTimestamp(index, new Timestamp(DateUtil.parseStrToDate(value[k], "yyyy-MM-dd HH:mm:ss").getTime()));
                                    }else {
                                        pStemt.setNull(index, Types.TIME);
                                    }
                                }else if(type.toLowerCase().equals("int")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setInt(index, Integer.valueOf(value[k]));
                                    }else {
                                        pStemt.setNull(index, Types.INTEGER);
                                    }
                                }else if(type.toLowerCase().equals("bit")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setBoolean(index, Boolean.valueOf(value[k]));
                                    }else {
                                        pStemt.setNull(index, Types.BIT);
                                    }
                                }else if(type.toLowerCase().equals("decimal") || type.toLowerCase().equals("numeric") || type.toLowerCase().equals("number")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setBigDecimal(index, new BigDecimal(value[k]));
                                    }else {
                                        pStemt.setNull(index, Types.DECIMAL);
                                    }
                                }else if(type.toLowerCase().equals("float") || type.toLowerCase().equals("real") || type.toLowerCase().equals("double")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setDouble(index, Float.valueOf(value[k]));
                                    }else {
                                        pStemt.setNull(index, Types.FLOAT);
                                    }
                                }else if(type.toLowerCase().equals("date")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setDate(index,  new java.sql.Date(DateUtil.parseStrToDate(value[k], "yyyy-MM-dd HH:mm:ss").getTime()));
                                    }else {
                                        pStemt.setNull(index, Types.DATE);
                                    }
                                }else if(type.toLowerCase().equals("tinyint")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setShort(index, Short.parseShort(value[k]));
                                    }else {
                                        pStemt.setNull(index, Types.TINYINT);
                                    }
                                }else if(type.toLowerCase().equals("blob")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setBlob(index, new SerialBlob(value[k].getBytes("GBK")));
                                    }else {
                                        pStemt.setNull(index, Types.BLOB);
                                    }
                                }else if(type.toLowerCase().equals("clob")) {
                                    if(value[k] != null && !value[k].equals("")) {
                                        pStemt.setClob(index, new SerialClob(value[k].toCharArray()));
                                    }else {
                                        pStemt.setNull(index, Types.CLOB);
                                    }
                                }
                            }
		            	}
	            	pStemt.addBatch();
	            }
	            pStemt.executeBatch();
				conn.commit();
				falg = true;
	            System.out.println(subList.size());
	            //总数不到�?页或者刚好等于一页的时�??,只需要处理一次就可以�?出for循环�?
	            if (toIndex == totalCount) {
	                break;
	            }
	        }
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if (pStemt != null) {
                try {
                    pStemt.close();
                    DatabaseUtil.closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
		}
		return falg;
	}

	/**
	 * 将数据插入到表中
	 * @param tableName
	 * @param data
	 * @param map
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static boolean insertOracleTable(String tableName, List<String> data, Map<String, String> map, Long branchId) throws SQLException, ParseException {
		boolean falg = false;
		//判断表是否存在自增长�?
//		boolean identity = DatabaseUtil.isIdentity(tableName);
		//拼装列头
		String [] column = data.get(0).split(",");
		String str = ""; String col = "";
		for(int i = 0; i < column.length; i++) {
			col += column[i];
			str += "?";
			if(i < column.length - 1) {
				str += " ,";
				col += " ,";
			}
		}
		String insertSql = "insert into "+tableName+" ("+col+") values(";
		insertSql += str + ")";
		//获取数据�?
		Connection conn = DatabaseUtil.getConnection();
        PreparedStatement pStemt = null;
        pStemt = conn.prepareStatement(insertSql);
		try{
	        //每次批量提交1000
			int PAGE_SIZE = 1000;
			//总记录数
	        Integer totalCount = data.size();
	        //分多少次处理
	        Integer requestCount = totalCount / PAGE_SIZE;
	        for (int i = 0; i <= requestCount; i++) {
	            Integer fromIndex = i * PAGE_SIZE;
	            //如果总数少于PAGE_SIZE,为了防止数组越界,toIndex直接使用totalCount即可
	            int toIndex = Math.min(totalCount, (i + 1) * PAGE_SIZE);
	            List<String> subList = data.subList(fromIndex, toIndex);
	            for(int j = i == 0 ? 1 : 0; j < subList.size(); j++) {
	            	String [] value = StringUtils.splitByWholeSeparatorPreserveAllTokens(subList.get(j), "|");
//	            	String [] value = subList.get(j).split("\\|", column.length);
//	            	System.out.println("##"+value.length+"==="+column.length);
//	            	System.out.println("@@"+value[0]+"=="+value[1]+"=="+value[2]+"=="+value[3]+"=="+"=="+value[4]+"=="+column.length+"==="+value.length);
		            	for(int k = 0; k < column.length; k++) {
		            		String type = map.get(column[k].toUpperCase());
		            		int index = k + 1;
							/*对于创建人用户名(createdBy) System、机构ID（BRANCH_ID）、数据来源 （DATA_SOURCE）*/
							if (column[k].toUpperCase().equals("createdBy".toUpperCase())) {
								pStemt.setString(index, "System");
							} else if(column[k].toUpperCase().equals("BRANCH_ID".toUpperCase()) || column[k].toUpperCase().equals("BRANCHID".toUpperCase())) {
								pStemt.setLong(index, branchId);
							} else if(column[k].toUpperCase().equals("DATA_SOURCE".toUpperCase()) || column[k].toUpperCase().equals("DATASOURCE".toUpperCase())) {
								pStemt.setInt(index, 1);
							} else if(column[k].toUpperCase().equals("createdTime".toUpperCase())
									|| column[k].toUpperCase().equals("lastModifiedTime".toUpperCase())
									) {
								pStemt.setTimestamp(index, new Timestamp(System.currentTimeMillis()));
							}  else if(column[k].toUpperCase().equals("invalid".toUpperCase())) {
								pStemt.setInt(index, 0);
							} else if(column[k].toUpperCase().equals("checkBy".toUpperCase())
									|| column[k].toUpperCase().equals("submitBy".toUpperCase())
									) {
								pStemt.setNull(index, Types.VARCHAR);
							} else if(column[k].toUpperCase().equals("lastModifiedBy".toUpperCase())) {
								pStemt.setString(index, "System");
							} else if(column[k].toUpperCase().equals("checkTime".toUpperCase())
									|| column[k].toUpperCase().equals("submitTime".toUpperCase())
									) {
								pStemt.setNull(index, Types.TIME);
							} else if(column[k].toUpperCase().equals("ACTION_TYPE".toUpperCase())
									|| column[k].toUpperCase().equals("ACTIONTYPE".toUpperCase())
									|| column[k].toUpperCase().equals("LIST_ACTION_TYPE".toUpperCase())
									|| column[k].toUpperCase().equals("LISTACTIONTYPE".toUpperCase())
									) {
								pStemt.setString(index, "A");
							} else if(column[k].toUpperCase().equals("DATA_STATUS".toUpperCase())
									|| column[k].toUpperCase().equals("DATASTATUS".toUpperCase())
									|| column[k].toUpperCase().equals("LIST_DATA_STATUS".toUpperCase())
									|| column[k].toUpperCase().equals("LISTDATASTATUS".toUpperCase())
									) {
								//待提交
								pStemt.setInt(index, 1);
							} else {
                                if (type.toLowerCase().equals("bigint")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setLong(index, Long.valueOf(value[k]));
                                    } else {
                                        pStemt.setNull(index, Types.DOUBLE);
                                    }
                                } else if (type.toLowerCase().equals("ntext") || type.toLowerCase().equals("text") || type.toLowerCase().equals("nvarchar(max)") || type.toLowerCase().equals("nvarchar") || type.toLowerCase().equals("varchar(max)") || type.toLowerCase().equals("varchar") || type.toLowerCase().equals("varchar2") || type.toLowerCase().equals("char")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setString(index, value[k]);
                                    } else {
                                        pStemt.setNull(index, Types.VARCHAR);
                                    }
                                } else if (type.toLowerCase().equals("time") || type.toLowerCase().equals("datetime") || type.toLowerCase().equals("datetime2") || type.toLowerCase().equals("smalldatetime")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setTimestamp(index, new Timestamp(DateUtil.parseStrToDate(value[k], "yyyy-MM-dd HH:mm:ss").getTime()));
                                    } else {
                                        pStemt.setNull(index, Types.TIME);
                                    }
                                } else if (type.toLowerCase().equals("int")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setInt(index, Integer.valueOf(value[k]));
                                    } else {
                                        pStemt.setNull(index, Types.INTEGER);
                                    }
                                } else if (type.toLowerCase().equals("bit")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setBoolean(index, Boolean.valueOf(value[k]));
                                    } else {
                                        pStemt.setNull(index, Types.BIT);
                                    }
                                } else if (type.toLowerCase().equals("decimal") || type.toLowerCase().equals("numeric") || type.toLowerCase().equals("number")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setBigDecimal(index, new BigDecimal(value[k]));
                                    } else {
                                        pStemt.setNull(index, Types.DECIMAL);
                                    }
                                } else if (type.toLowerCase().equals("float") || type.toLowerCase().equals("real") || type.toLowerCase().equals("double")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setDouble(index, Double.valueOf(value[k]));
                                    } else {
                                        pStemt.setNull(index, Types.FLOAT);
                                    }
                                } else if (type.toLowerCase().equals("date")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setTimestamp(index, new Timestamp(DateUtil.parseStrToDate(value[k], "yyyy-MM-dd HH:mm:ss").getTime()));
                                    } else {
                                        pStemt.setNull(index, Types.DATE);
                                    }
                                } else if (type.toLowerCase().equals("tinyint")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setShort(index, Short.parseShort(value[k]));
                                    } else {
                                        pStemt.setNull(index, Types.TINYINT);
                                    }
                                } else if (type.toLowerCase().equals("blob")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setBlob(index, new SerialBlob(value[k].getBytes("GBK")));
                                    } else {
                                        pStemt.setNull(index, Types.BLOB);
                                    }
                                } else if (type.toLowerCase().equals("clob")) {
                                    if (value[k] != null && !value[k].equals("")) {
                                        pStemt.setClob(index, new SerialClob(value[k].toCharArray()));
                                    } else {
                                        pStemt.setNull(index, Types.CLOB);
                                    }
                                }
                            }
		            	}
	            	pStemt.addBatch();
	            }
	            pStemt.executeBatch();
				conn.commit();
				falg = true;
	            System.out.println(subList.size());
	            //总数不到�?页或者刚好等于一页的时�??,只需要处理一次就可以�?出for循环�?
	            if (toIndex == totalCount) {
	                break;
	            }
	        }
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if (pStemt != null) {
                try {
                    pStemt.close();
                    DatabaseUtil.closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
		}
		return falg;
	}
	
	
	/**
	 * 读文�?
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readToString(String fileName) {  
        String encoding = "UTF-8";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
    }  
	
	/**
	 * 读文�?
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFile(String fileName, String sqlType) throws IOException {
		List<String> list = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			String encoding = "GBK";
			if(sqlType.equals("1")) {
				encoding = "UTF-8";
			}
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	list.add(line);
		    }
		} catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
		    reader.close();
		}
		return list;
	}

	/**
	 * 剔除已经存在的数据
	 * @param data
	 */
	public static void romoveExist(List<String> data, String tableName) {
	    if(null != data && data.size() > 1) {
	        Map<Long, String> idIndex = new HashMap<>();
            StringBuffer idStr = new StringBuffer("(");
            int j = 0;
            List<String> removeIds = new ArrayList<>();
            for (int i = 1; i < data.size(); i++) {
                String [] val = StringUtils.splitByWholeSeparatorPreserveAllTokens(data.get(i), "|");
                if (j > 0) {
                    idStr.append(",");
                }
                idStr.append(val[0]);
                idIndex.put(Long.valueOf(val[0]), data.get(i));
                if (j % 20 == 0) {
                    idStr.append(")");
                    List<Long> getIds = getByIds(idStr.toString(), tableName);
                    for (Long id : getIds) {
                        removeIds.add(idIndex.get(id));
                    }
                    j = 0;
                    idStr = new StringBuffer("(");
                }
            }
            /*如果不是整数倍，*/
            if (j > 0) {
                idStr.append(")");
                List<Long> getIds = getByIds(idStr.toString(), tableName);
                for (Long id : getIds) {
                    removeIds.add(idIndex.get(id));
                }
            }
            /*删除已经存在的记录*/
            for (String index : removeIds) {
                data.remove(index);
            }
        }
	}

	public static List<Long> getByIds(String ids, String tableName) {
        List<Long> list = new ArrayList<>(0);
        String sql = "SELECT ID FROM " + tableName + " where ID IN " + ids;
        //获取数据�?
        Connection conn = DatabaseUtil.getConnection();
        PreparedStatement pStemt = null;
        try {
            pStemt = conn.prepareStatement(sql);
            ResultSet resultSet = pStemt.executeQuery();
            while (resultSet.next()) {
                if (!UtilStr.isEmpty(resultSet.getString(1))) {
                    list.add(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    DatabaseUtil.closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return list;
    }

	/**
	 * 清理数据
	 * @param tableName
	 */
	public static void deleteTable(String tableName) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM "+tableName;
        //获取数据�?
        Connection conn = DatabaseUtil.getConnection();
        PreparedStatement pStemt = null;
        try {
            pStemt = conn.prepareStatement(sql);
            pStemt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    DatabaseUtil.closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
        }
	}
}
