package com.rongki.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;




public class DBOption extends DBUtil{
	
	protected Statement stmt;

	protected PreparedStatement pstmt;

	protected CallableStatement callStmt;//执行存储过程

	protected Connection con;

	
	/**
	 * 执行存储方法
	 * @param sCmd
	 * @param parms
	 * @return
	 */
	public String executeProcedures(String sCmd, List<ProcedureParameter> parms) {
		StringBuffer result = new StringBuffer();
		try {
			String tmpCmd = "{call " + sCmd + "(";

			for (int i = 0; i < parms.size(); i++) {
				tmpCmd += "?";
				if (i < parms.size() - 1) {
					tmpCmd += ",";
				}
			}

			tmpCmd += ")}";
			con = this.getConnection();
			callStmt = con.prepareCall(tmpCmd);// 创建 PreparedStatement
			// 对象来将参数化的 SQL 语句发送到数据库
			// 根据参数组的值,设置存储过程的值
			for (int i = 0; i < parms.size(); i++) {
				ProcedureParameter parm = parms.get(i);
				if (parm.isIn()) {
					callStmt.setString(i + 1, parm.getValue().toString());
				} else {
					callStmt.registerOutParameter(i + 1, parm.getType());
					// 登记输入的参数类形
				}
			}
			callStmt.execute();

			for (int i = 0; i < parms.size(); i++) {
				ProcedureParameter parm = parms.get(i);
				if (!parm.isIn()) {
					// 返回执行存储过程后的信息结果
					result.append(callStmt.getString(i + 1));
					if (i != parms.size() - 1) {
						result.append("|");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll(con, callStmt, null);
		}
		// System.out.println(result.toString());
		return result.toString();

	}
	
	
	/**
	 * 功能 : 通过执行存储过程获得统计报表数据信息(返回单一结果集)
	 * @param sCmd
	 * @param parms
	 * @return
	 * @throws DBCloseException
	 */
	public ResultSet getResultSetProcedures(String sCmd,List<ProcedureParameter> parms)  {
		ResultSet rs = null;
		try {
			String tmpCmd = "{call " + sCmd + "(";

			for (int i = 0; i < parms.size(); i++) {
				tmpCmd += "?";
				if (i < parms.size() - 1) {
					tmpCmd += ",";
				}
			}

			tmpCmd += ")}";
			con = this.getConnection();
			callStmt = con.prepareCall(tmpCmd);
			for (int i = 0; i < parms.size(); i++) {
				ProcedureParameter parm = parms.get(i);
				if (parm.isIn()) {
					callStmt.setString(i + 1, parm.getValue().toString());
				} else {
					callStmt.registerOutParameter(i + 1, parm.getType());
				}
			}
			callStmt.execute();

			for (int i = 0; i < parms.size(); i++) {
				ProcedureParameter parm = parms.get(i);
				if (!parm.isIn()) {
					rs = (ResultSet) callStmt.getObject(i + 1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll(con, callStmt, rs);
		}
		return rs;
	}
	
	/**
	 * 功能 : 通过执行存储过程获得统计报表数据信息(返回数据集和其他)
	 * @param sCmd
	 * @param parms
	 * @return
	 * @throws DBCloseException
	 */
	public Map<String, Object> getMapSetProcedures(String sCmd,
			List<ProcedureParameter> parms) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String tmpCmd = "{call " + sCmd + "(";

			for (int i = 0; i < parms.size(); i++) {
				tmpCmd += "?";
				if (i < parms.size() - 1) {
					tmpCmd += ",";
				}
			}

			tmpCmd += ")}";
			con = this.getConnection();
			callStmt = con.prepareCall(tmpCmd);
			for (int i = 0; i < parms.size(); i++) {
				ProcedureParameter parm = parms.get(i);
				if (parm.isIn()) {
					callStmt.setString(i + 1, parm.getValue().toString());
				} else {
					callStmt.registerOutParameter(i + 1, parm.getType());
				}
			}
			callStmt.execute();

			for (int i = 0; i < parms.size(); i++) {
				ProcedureParameter parm = parms.get(i);
				if (!parm.isIn()) {
					if(parm.getType()==Types.VARCHAR){
						map.put(parm.getName().toString(), (String) callStmt.getObject(i + 1));
					}else if(parm.getType()==Types.INTEGER){
						map.put(parm.getName().toString(), (Integer) callStmt.getObject(i + 1));
					}else if(parm.getType()==OracleTypes.CURSOR){
						ResultSet rs = null;
						rs = (ResultSet) callStmt.getObject(i + 1);
						map.put(parm.getName().toString(), rs);
					}
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll(con, callStmt, null);
		}
		return map;

	}
}
