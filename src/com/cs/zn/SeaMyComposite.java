package com.cs.zn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class SeaMyComposite extends Composite {

	// ����������
	public String driver = "com.mysql.jdbc.Driver";

	// URL ָ�� ���ʵ����ݿ�����
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL �û���
	public String user = "root";

	// MySQL ����
	public String password = "root";

	// ����
	private Text text;

	// ѧԺ
	private Text text_1;

	// �Ա�
	private Text text_2;

	// ѧ��
	private Text text_4;

	private String str;

	public SeaMyComposite(Composite parent, int style, Shell shell, String sid) {
		super(parent, style);
		// TODO �Զ����ɵĹ��캯�����
		// ���������ʾ
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblNewLabel.setFont(SWTResourceManager.getFont("��Բ", 20, SWT.BOLD));
		lblNewLabel.setBounds(10, 10, 66, 33);
		lblNewLabel.setText("��Ϣ");

		// �������
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setBounds(0, 49, 746, 431);

		// ������ʾ
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("��Բ", 12, SWT.BOLD));
		lblNewLabel_1.setBounds(10, 20, 300, 33);
		lblNewLabel_1.setText("������Ϣ��");

		// ����
		Label label = new Label(composite, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label.setBounds(140, 121, 75, 20);
		label.setText("\u59D3\u540D\uFF1A");

		// �����ı���
		text = new Text(composite, SWT.NONE);
		text.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		text.setBounds(221, 109, 148, 26);

		// �»���
		Label label_1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(215, 141, 151, 2);

		// �Ա�
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setText("�Ա�");
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_2.setBounds(140, 166, 75, 20);

		text_1 = new Text(composite, SWT.NONE);
		text_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		text_1.setBounds(221, 154, 148, 26);

		Label label_3 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(215, 186, 151, 2);

		// ѧ��
		Label label_4 = new Label(composite, SWT.NONE);
		label_4.setText("ѧ�ţ�");
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_4.setBounds(140, 211, 75, 20);

		text_2 = new Text(composite, SWT.NONE);
		text_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		text_2.setBounds(221, 199, 148, 26);

		Label label_5 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_5.setBounds(215, 231, 151, 2);

		// ѧԺ
		Label label_8 = new Label(composite, SWT.NONE);
		label_8.setText("ѧԺ��");
		label_8.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_8.setBounds(140, 256, 75, 20);

		// ѧ���ı���
		text_4 = new Text(composite, SWT.NONE);
		text_4.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		text_4.setBounds(221, 244, 148, 26);

		// �»���
		Label label_9 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_9.setBounds(215, 276, 151, 2);

		str = sid;
		try {
			// ������������
			Class.forName(driver);

			// ���� Connection
			Connection con;

			// .getConnection()���� ����MySQL���ݿ�
			con = DriverManager.getConnection(url, user, password);
			// �½� statement ���� ִ�� SQL ���
			String sql = "select * from stu where sid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, str);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				text.setText(rs.getString(3));
				text_1.setText(rs.getString(4));
				text_2.setText(rs.getString(2));
				text_4.setText(rs.getString(1));
				text.setEnabled(false);
				text_1.setEnabled(false);
				text_2.setEnabled(false);
				text_4.setEnabled(false);
			}

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// ͼƬ
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setBackgroundImage(SWTResourceManager.getImage("./src/image/9.jpg"));
		composite_1.setBounds(428, 41, 275, 275);

	}
}
