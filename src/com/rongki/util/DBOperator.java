package com.rongki.util;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.rongki.util.ProcedureParameter;

public class DBOperator {
	
	DBOption db = new DBOption();
	
	

	/**
	 * ִ�д洢���� (δ����)
	 */
	public String executeProcedures(String proc,List<ProcedureParameter> parms){
		return db.executeProcedures(proc, parms);
	}
	
	
	/**
	 * ִ�д洢���� ���ص�һ�����
	 */
	public ResultSet getResultSetByProcedures(String proc,List<ProcedureParameter> parms){
		return db.getResultSetProcedures(proc, parms);
	}
	
	public Map<String,Object> getMapSetByProcedures(String proc,List<ProcedureParameter> parms){
		return db.getMapSetProcedures(proc, parms);
	}
}
