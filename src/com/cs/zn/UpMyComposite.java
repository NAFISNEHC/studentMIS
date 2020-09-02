package com.cs.zn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class UpMyComposite extends Composite {

	// 驱动程序名
	public String driver = "com.mysql.jdbc.Driver";

	// URL 指向 访问的数据库名字
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL 用户名
	public String user = "root";

	// MySQL 密码
	public String password = "root";

	// 姓名
	private Text text;

	// 学院
	private Text text_1;

	// 性别
	private String sex;

	public UpMyComposite(Composite parent, int style, Shell shell, String sid) {
		super(parent, style);
		// TODO 自动生成的构造函数存根
		// 添加字体显示
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblNewLabel.setFont(SWTResourceManager.getFont("幼圆", 20, SWT.BOLD));
		lblNewLabel.setBounds(10, 10, 66, 33);
		lblNewLabel.setText("修改");

		// 右子面板
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setBounds(0, 49, 746, 431);

		// 字体显示
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("幼圆", 12, SWT.BOLD));
		lblNewLabel_1.setBounds(10, 20, 300, 33);
		lblNewLabel_1.setText("需要修改的学生信息：");

		// 姓名
		Label label = new Label(composite, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label.setBounds(140, 121, 75, 20);
		label.setText("\u59D3\u540D\uFF1A");

		// 姓名文本框
		text = new Text(composite, SWT.NONE);
		text.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		text.setBounds(221, 109, 148, 26);

		// 下划线
		Label label_1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(215, 141, 151, 2);

		// 学院
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setText("学院：");
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_2.setBounds(140, 168, 75, 20);

		// 学院文本框
		text_1 = new Text(composite, SWT.NONE);
		text_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		text_1.setBounds(221, 160, 148, 26);

		// 下划线
		Label label_3 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(215, 188, 151, 2);

		// 性别
		Label label_4 = new Label(composite, SWT.NONE);
		label_4.setText("性别：");
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_4.setBounds(140, 214, 75, 20);

		Combo combo = new Combo(composite, SWT.READ_ONLY);
		combo.setBounds(215, 210, 92, 28);
		String items[] = new String[2];
		items[0] = "男";
		items[1] = "女";

		combo.setItems(items);
		combo.select(0);
		sex = combo.getText();
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sex = combo.getText();
			}
		});

		try {
			// 加载驱动程序
			Class.forName(driver);

			// 声明 Connection
			Connection con;

			// .getConnection()方法 连接MySQL数据库
			con = DriverManager.getConnection(url, user, password);
			// 新建 statement 对象 执行 SQL 语句
			String sql = "select * from stu where sid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, sid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				text.setText(rs.getString(3));
				text_1.setText(rs.getString(2));
				if (rs.getString(4).equals("男"))
					combo.select(0);
				else
					combo.select(1);
				sex = combo.getText();
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// 确定
		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (text_1.getText().equals("") | text.getText().equals("")) {
					MessageBox mb = new MessageBox(shell);
					mb.setText("提示信息");
					mb.setMessage("信息不能为空！");
					mb.open();
				} else {
					try {
						// 加载驱动程序
						Class.forName(driver);

						// 声明 Connection
						Connection con;

						// .getConnection()方法 连接MySQL数据库
						con = DriverManager.getConnection(url, user, password);
						// 新建 statement 对象 执行 SQL 语句
						String sql = "update stu set school=?,name=?,sex=? where sid=?";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, text_1.getText());
						ps.setString(2, text.getText());
						ps.setString(3, sex);
						ps.setString(4, sid);
						ps.executeUpdate();
						MessageBox mb = new MessageBox(shell);
						mb.setText("提示信息");
						mb.setMessage("修改成功！");
						mb.open();
						shell.dispose();
						new SMainFrame(sid);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		button.setBounds(221, 295, 98, 30);
		button.setText("\u786E\u5B9A");

		// 图片
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setBackgroundImage(SWTResourceManager.getImage("./src/image/14.jpg"));
		composite_1.setBounds(428, 41, 275, 275);

	}
}
