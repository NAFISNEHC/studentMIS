package com.cs.zn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class SeaComposite {

	// 驱动程序名
	public String driver = "com.mysql.jdbc.Driver";

	// URL 指向 访问的数据库名字
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL 用户名
	public String user = "root";

	// MySQL 密码
	public String password = "root";

	public SeaComposite(Table table) {
		try {
			// 加载驱动程序
			Class.forName(driver);

			// 声明 Connection 对象
			Connection con;

			// .getConnection()方法 连接MySQL数据库
			con = DriverManager.getConnection(url, user, password);

			// 新建 statement 对象 执行 SQL 语句
			String sql = "select * from stu";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// 循环数值
			while (rs.next()) {
				TableItem ti = new TableItem(table, SWT.NONE);
				if (rs.getString(6).equals("1"))
					ti.setText(
							new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), "管理员" });
				else
					ti.setText(new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							"普通学生" });
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
