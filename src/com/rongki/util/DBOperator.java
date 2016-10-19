package com.rongki.util;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.rongki.util.ProcedureParameter;

public class DBOperator {
	
	DBOption db = new DBOption();
	
	

	/**
	 * 执行存储过程 (未测试)
	 */
	public String executeProcedures(String proc,List<ProcedureParameter> parms){
		return db.executeProcedures(proc, parms);
	}
	
	
	/**
	 * 执行存储过程 返回单一结果集
	 */
	public ResultSet getResultSetByProcedures(String proc,List<ProcedureParameter> parms){
		return db.getResultSetProcedures(proc, parms);
	}
	
	public Map<String,Object> getMapSetByProcedures(String proc,List<ProcedureParameter> parms){
		return db.getMapSetProcedures(proc, parms);
	}
}
