package com.cs.zn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class DelComposite {

	// ����������
	public String driver = "com.mysql.jdbc.Driver";

	// URL ָ�� ���ʵ����ݿ�����
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL �û���
	public String user = "root";

	// MySQL ����
	public String password = "root";

	public DelComposite(Shell shell, String str) {
		try {
			// ������������
			Class.forName(driver);

			// ���� Connection ����
			Connection con;

			// .getConnection()���� ����MySQL���ݿ�
			con = DriverManager.getConnection(url, user, password);

			// �½� statement ���� ִ�� SQL ���
			String sql = "delete from stu where sid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, str);
			int result = ps.executeUpdate();
			if (result > 0) {
				MessageBox mb = new MessageBox(shell);
				mb.setText("��ʾ��Ϣ");
				mb.setMessage("ɾ���ɹ���");
				mb.open();
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
