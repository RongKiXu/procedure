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
	 * �����صĽ����������Ϊjava�շ������� ������һ������TreeMap��List
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
					String tmpkey = rs.getMetaData().getColumnName(i);// ͨ�������ҵ��е�����

					String[] tmps = tmpkey.split("_");// �ָ����к�"_"�ַ�����������
					String key = "";
					for (int j = 0; j < tmps.length; j++) {
						if (j == 0) {
							key += tmps[j].toLowerCase();// ת��ΪСд
						} else {
							key += tmps[j].substring(0, 1).toUpperCase()
									+ tmps[j].substring(1).toLowerCase();
						}
					}

					Object val = rs.getObject(i);
					
					/*
					 * 2009-06-27 �޸ĸò��ִ���, ��ȷ��ҳ��������ʾ��ȷ��, ��ֹҳ����ʾ���ݲ�����
					 */
					// switch������
//					System.out.println("������������"+key+"�������� :��"+rs.getMetaData().getColumnType(i)+" ����ֵ : " + rs.getObject(i));
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
						// map���Ҳ���ָ���������ø�
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
