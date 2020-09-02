package com.cs.zn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class DelComposite {

	// 驱动程序名
	public String driver = "com.mysql.jdbc.Driver";

	// URL 指向 访问的数据库名字
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL 用户名
	public String user = "root";

	// MySQL 密码
	public String password = "root";

	public DelComposite(Shell shell, String str) {
		try {
			// 加载驱动程序
			Class.forName(driver);

			// 声明 Connection 对象
			Connection con;

			// .getConnection()方法 连接MySQL数据库
			con = DriverManager.getConnection(url, user, password);

			// 新建 statement 对象 执行 SQL 语句
			String sql = "delete from stu where sid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, str);
			int result = ps.executeUpdate();
			if (result > 0) {
				MessageBox mb = new MessageBox(shell);
				mb.setText("提示信息");
				mb.setMessage("删除成功！");
				mb.open();
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
