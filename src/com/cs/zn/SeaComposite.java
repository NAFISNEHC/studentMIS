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

	// ����������
	public String driver = "com.mysql.jdbc.Driver";

	// URL ָ�� ���ʵ����ݿ�����
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL �û���
	public String user = "root";

	// MySQL ����
	public String password = "root";

	public SeaComposite(Table table) {
		try {
			// ������������
			Class.forName(driver);

			// ���� Connection ����
			Connection con;

			// .getConnection()���� ����MySQL���ݿ�
			con = DriverManager.getConnection(url, user, password);

			// �½� statement ���� ִ�� SQL ���
			String sql = "select * from stu";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// ѭ����ֵ
			while (rs.next()) {
				TableItem ti = new TableItem(table, SWT.NONE);
				if (rs.getString(6).equals("1"))
					ti.setText(
							new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), "����Ա" });
				else
					ti.setText(new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							"��ͨѧ��" });
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
