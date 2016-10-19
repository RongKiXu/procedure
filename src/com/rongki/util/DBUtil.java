package com.rongki.util;

/**
 * 数据库连接
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//数据库封装类
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
			// 设置JDBC的Driver类
			ds.setDriverClass(DRIVER);
			// 设置JDBC的URL
			ds.setJdbcUrl(URL);
			// 设置数据库的登录用户名
			ds.setUser(USER);
			// 设置数据库的登录用户密码
			ds.setPassword(PASSWORD);
			// 设置连接池的最大连接数
			ds.setMaxPoolSize(Integer.parseInt(MAXPOOLSIZE));
			// 设置连接池的最小连接数
			ds.setMinPoolSize(Integer.parseInt(MINPOOLSIZE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 得到连接的方法
	public  Connection getConnection() throws SQLException {
		Connection connection = ds.getConnection();
		return connection;
	}

	// 关闭资源的方法
	public void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("关闭resultSet资源失败！");
		}
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("关闭statement资源失败！");
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("关闭connection资源失败！");
		}
	}
}
