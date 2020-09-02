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

public class AddComposite extends Composite {

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

	// 学号
	private Text text_3;

	// 密码
	private Text text_4;

	public AddComposite(Composite parent, int style, Shell shell, String sid) {
		super(parent, style);
		// TODO 自动生成的构造函数存根
		// 添加字体显示
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblNewLabel.setFont(SWTResourceManager.getFont("幼圆", 20, SWT.BOLD));
		lblNewLabel.setBounds(10, 10, 66, 33);
		lblNewLabel.setText("添加");

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
		lblNewLabel_1.setText("需要添加的学生信息：");

		// 姓名
		Label label = new Label(composite, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label.setBounds(140, 101, 75, 20);
		label.setText("\u59D3\u540D\uFF1A");

		// 姓名文本框
		text = new Text(composite, SWT.NONE);
		text.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		text.setBounds(221, 89, 148, 26);

		// 下划线
		Label label_1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(215, 121, 151, 2);

		// 学院
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setText("学院：");
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_2.setBounds(140, 141, 75, 20);

		// 学院文本框
		text_1 = new Text(composite, SWT.NONE);
		text_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		text_1.setBounds(221, 129, 148, 26);

		// 下划线
		Label label_3 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(215, 161, 151, 2);

		// 性别
		Label label_4 = new Label(composite, SWT.NONE);
		label_4.setText("性别：");
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_4.setBounds(140, 181, 75, 20);

		Combo combo = new Combo(composite, SWT.READ_ONLY);
		combo.setBounds(215, 181, 92, 28);
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

		// 学号
		Label label_6 = new Label(composite, SWT.NONE);
		label_6.setText("学号：");
		label_6.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_6.setBounds(140, 221, 75, 20);

		text_3 = new Text(composite, SWT.NONE);
		text_3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		text_3.setBounds(221, 209, 148, 26);

		Label label_7 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_7.setBounds(215, 241, 151, 2);

		// 密码
		Label label_8 = new Label(composite, SWT.NONE);
		label_8.setText("密码：");
		label_8.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_8.setBounds(140, 261, 75, 20);

		text_4 = new Text(composite, SWT.NONE);
		text_4.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		text_4.setBounds(221, 249, 148, 26);

		Label label_9 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_9.setBounds(215, 281, 151, 2);

		// 确定
		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (text_1.getText().equals("") | text.getText().equals("") | text_3.getText().equals("")
						| text_4.getText().equals("")) {
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
						String sql = "select * from stu where sid=?";
						PreparedStatement ps = con.prepareStatement(sql);
						String str = text_3.getText();
						ps.setString(1, str);
						ResultSet rs = ps.executeQuery();
						if (rs.next()) {
							MessageBox mb = new MessageBox(shell);
							mb.setText("提示信息");
							mb.setMessage("该学号已存在！");
							mb.open();
						} else {
							String sql2 = "insert into stu values(?,?,?,?,?,?,?)";
							PreparedStatement ps2 = con.prepareStatement(sql2);
							ps2.setString(2, text_1.getText());
							ps2.setString(3, text.getText());
							ps2.setString(4, sex);
							ps2.setString(5, text_4.getText());
							ps2.setString(6, "0");
							ps2.setString(7, "0");
							ps2.setString(1, text_3.getText());
							ps2.executeUpdate();
							MessageBox mb = new MessageBox(shell);
							mb.setText("提示信息");
							mb.setMessage("添加成功！");
							mb.open();
							shell.dispose();
							new MainFrame(sid);
						}
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
		composite_1.setBackgroundImage(SWTResourceManager.getImage("./src/image/1.jpg"));
		composite_1.setBounds(428, 41, 275, 275);

	}
}
