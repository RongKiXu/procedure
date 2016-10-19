package com.rongki.util;

/**
 * ���ݿ�����
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//���ݿ��װ��
public class DBUtil {

	static Properties properties = null;
	private static ComboPooledDataSource ds = null;
	private static String  URL = "jdbc:oracle:thin:@120.25.232.128:1521:orcl";
	private static String  DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static String  USER = "ylwj";
	private static String  PASSWORD = "aaaaaa";
	private static String  MAXPOOLSIZE = "0";
	private static String  MINPOOLSIZE = "20";
	static {
		try {
//			Properties properties = new Properties();
//			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config.properties");
//			properties.load(in);
//			DRIVER = properties.getProperty("driver");
//			URL = properties.getProperty("url");
//			USER = properties.getProperty("user");
//			PASSWORD = properties.getProperty("password");
//			MAXPOOLSIZE = properties.getProperty("maxPoolSize");
//			MINPOOLSIZE = properties.getProperty("minPoolSize");
			ds = new ComboPooledDataSource();
			// ����JDBC��Driver��
			ds.setDriverClass(DRIVER);
			// ����JDBC��URL
			ds.setJdbcUrl(URL);
			// �������ݿ�ĵ�¼�û���
			ds.setUser(USER);
			// �������ݿ�ĵ�¼�û�����
			ds.setPassword(PASSWORD);
			// �������ӳص����������
			ds.setMaxPoolSize(Integer.parseInt(MAXPOOLSIZE));
			// �������ӳص���С������
			ds.setMinPoolSize(Integer.parseInt(MINPOOLSIZE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �õ����ӵķ���
	public  Connection getConnection() throws SQLException {
		Connection connection = ds.getConnection();
		return connection;
	}

	// �ر���Դ�ķ���
	public void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�ر�resultSet��Դʧ�ܣ�");
		}
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�ر�statement��Դʧ�ܣ�");
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�ر�connection��Դʧ�ܣ�");
		}
	}
}
