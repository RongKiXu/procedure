package com.rongki.test;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import oracle.jdbc.OracleTypes;

import com.rongki.util.Change;
import com.rongki.util.DBOperator;
import com.rongki.util.JsonDateValueProcessorImpl;
import com.rongki.util.JsonNumberValueProcessorImpl;
import com.rongki.util.ProcedureParameter;

/**
 * 测试类 测试存储过程的调用
 * @author 徐容基
 * 2016年10月18日17:31:03
 *
 */
public class test {
	/**
	 * 模拟Action层
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Map<String,Object> map = dao1("0000000201","0","10");
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("v_cursor");
		if(list.size() != 0)
		{
			JsonConfig config=new JsonConfig();
			config.registerJsonValueProcessor(java.util.Date.class,
					new JsonDateValueProcessorImpl());
			config.registerJsonValueProcessor(java.sql.Date.class,
					new JsonDateValueProcessorImpl());
			config.registerJsonValueProcessor(java.lang.Double.class,
					new JsonNumberValueProcessorImpl());
			JSONArray jsons = JSONArray.fromObject(list, config);
			//总记录数
			int total = map.get("v_total")==null?1:Integer.parseInt(map.get("v_total").toString());
//			response.getWriter().print("{\"totalCount\":" + total + ",\"root\":" + jsons + "}");
			System.out.println("{\"totalCount\":" + total + ",\"root\":" + jsons + "}");
		}
	}
	
	
	
	
	
	/**
	 * 模拟dao层 执行只返回单一结果集的方法 这里面pList对应存储过程 有多少传多少
	 * @param Para1 参数1
	 * @param Para2 参数2
	 * @param Para3 参数3
	 * @return
	 */
	public static List<Map<String,Object>> dao(String Para1,String Para2,String Para3){
		List<ProcedureParameter> pList = new ArrayList<ProcedureParameter>();  
		//存储过程名称
		String procName="pc_Med_Inhos_Info";
		pList.add(new ProcedureParameter("v_info_flag", "1",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_hospital_code", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_patient_id", Para1,Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_inhos_no", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_start", Para2,Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_limit", Para3,Types.VARCHAR, true));
		
		pList.add(new ProcedureParameter("v_cursor", null,OracleTypes.CURSOR, false));
		

		DBOperator operator = new DBOperator();
		Change change = new Change();
		ResultSet set  = operator.getResultSetByProcedures(procName, pList);
		return change.fullToList(set);
	}
	
	
	/**
	 * 模拟dao层 返回多个结果的调用存储过程的方法
	 * @param Para1
	 * @param Para2
	 * @param Para3
	 * @return
	 */
	public static Map<String,Object> dao1(String Para1,String Para2,String Para3){
		List<ProcedureParameter> pList = new ArrayList<ProcedureParameter>();
		String procName="pc_Med_Inhos_Info_BS";
		pList.add(new ProcedureParameter("v_info_flag", "1",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_hospital_code", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_hospital_name", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_patient_id", Para1,Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_inhos_no", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_report_name", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_report_fdate", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_report_ldate", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_inhos_fdate", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_inhos_ldate", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_outhos_fdate", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_outhos_ldate", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_report_doctor", "",Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_start", Para2,Types.VARCHAR, true));
		pList.add(new ProcedureParameter("v_limit", Para3,Types.VARCHAR, true));
		
		pList.add(new ProcedureParameter("v_total", null,OracleTypes.INTEGER, false));
		pList.add(new ProcedureParameter("v_cursor", null,OracleTypes.CURSOR, false));
		DBOperator operator = new DBOperator();
		Change change = new Change();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = operator.getMapSetByProcedures(procName, pList);
			result.put("v_cursor", change.fullToList((ResultSet) result.get("v_cursor")));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return result;
	}
	
}
