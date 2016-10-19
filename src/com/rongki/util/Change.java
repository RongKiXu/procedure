package com.rongki.util;

import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Change {
	
	/**
	 * 将返回的结果集列名换为java驼峰命名法 并返回一个包含TreeMap的List
	 * @param rs
	 * @return
	 */
	public List<Map<String, Object>> fullToList(ResultSet rs) {
		ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			int iColumn = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new TreeMap<String, Object>();
				for (int i = 1; i <= iColumn; i++) {
					String tmpkey = rs.getMetaData().getColumnName(i);// 通过索引找到行的名字

					String[] tmps = tmpkey.split("_");// 分割所有含"_"字符，返回数组
					String key = "";
					for (int j = 0; j < tmps.length; j++) {
						if (j == 0) {
							key += tmps[j].toLowerCase();// 转换为小写
						} else {
							key += tmps[j].substring(0, 1).toUpperCase()
									+ tmps[j].substring(1).toLowerCase();
						}
					}

					Object val = rs.getObject(i);
					
					/*
					 * 2009-06-27 修改该部分代码, 以确保页面数据显示正确性, 防止页面显示数据不正常
					 */
					// switch行类型
//					System.out.println("变量名　：　"+key+"数据类型 :　"+rs.getMetaData().getColumnType(i)+" 变量值 : " + rs.getObject(i));
//					switch (rs.getMetaData().getColumnType(i)) {
//					case Types.DATE:
//						val = rs.getDate(i);
//						break;
//					case Types.DOUBLE:
//						val = rs.getDouble(i);
//						break;
//					case Types.NUMERIC:
//						val = rs.getDouble(i);
//						break;
//					case Types.DECIMAL:
//						val = rs.getDouble(i);
//						break;
//					case Types.INTEGER:
//						val = rs.getInt(i);
//						break;
//					default:
//						val = rs.getString(i);
//						break;
//					}
					
					switch (rs.getMetaData().getColumnType(i)){
						case Types.CLOB:
							val = this.clobToString(rs.getClob(i)) ;
							break;
						default:
							val = rs.getObject(i);
							break;
					}
					
					if(null==val){
						val="";
					}
					//if (null != val) {
						// map找找不到指定键便设置个
						if (!map.containsKey(key)) {
							map.put(key, val);
						}
					//}
				}
				//System.out.println(map.toString());
				result.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	private String clobToString(Clob clob){
		
		StringBuffer sb=new StringBuffer(); 
		char[] cb = new char[1024]; 
		try {
			Reader reader = clob.getCharacterStream();
			for(int len = reader.read(cb);len>0;len= reader.read(cb)){ 
				sb.append(cb,0,len); 
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		return sb.toString();
	}
}
