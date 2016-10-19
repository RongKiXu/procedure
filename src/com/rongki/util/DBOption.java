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

	protected CallableStatement callStmt;//ִ�д洢����

	protected Connection con;

	
	/**
	 * ִ�д洢����
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
			callStmt = con.prepareCall(tmpCmd);// ���� PreparedStatement
			// ���������������� SQL ��䷢�͵����ݿ�
			// ���ݲ������ֵ,���ô洢���̵�ֵ
			for (int i = 0; i < parms.size(); i++) {
				ProcedureParameter parm = parms.get(i);
				if (parm.isIn()) {
					callStmt.setString(i + 1, parm.getValue().toString());
				} else {
					callStmt.registerOutParameter(i + 1, parm.getType());
					// �Ǽ�����Ĳ�������
				}
			}
			callStmt.execute();

			for (int i = 0; i < parms.size(); i++) {
				ProcedureParameter parm = parms.get(i);
				if (!parm.isIn()) {
					// ����ִ�д洢���̺����Ϣ���
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
	 * ���� : ͨ��ִ�д洢���̻��ͳ�Ʊ���������Ϣ(���ص�һ�����)
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
	 * ���� : ͨ��ִ�д洢���̻��ͳ�Ʊ���������Ϣ(�������ݼ�������)
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
