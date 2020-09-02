package com.cs.zn;

import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.MessageBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class LoginFrame {

	// 主面板
	protected Shell shell;

	// 账号文本框
	private Text text;

	// 密码文本框1
	private Text text_1;

	// 密码文本框2 解决显示提示语
	private Text text_2;

	private String rem = "0";

	// 驱动程序名
	public String driver = "com.mysql.jdbc.Driver";

	// URL 指向 访问的数据库名字
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL 用户名
	public String user = "root";

	// MySQL 密码
	public String password = "root";

	//	托盘
	public TrayMenu tm;
	public static void main(String[] args) {
		try {
			LoginFrame window = new LoginFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();

		// 增加托盘
		tm = new TrayMenu(shell, display);
		shell.open();
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.DIALOG_TRIM);
		shell.addShellListener(new ShellAdapter() {
            public void shellClosed(final ShellEvent e) {
                MessageBox messageBox = new MessageBox(shell,
                        SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
                messageBox.setText("确定");
                messageBox.setMessage("你确定要退出吗？");
                int rc = messageBox.open();
                if (rc == SWT.CANCEL) {
                    e.doit = false;
                }
                else
                	tm.tray.dispose();
            }
        });
		// 窗口位置
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);
		shell.setLayoutDeferred(true);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

		// 窗口背景图
		shell.setBackgroundImage(
				SWTResourceManager.getImage(LoginFrame.class, "/image/2.jpg"));

		// 窗口图标
		shell.setImage(SWTResourceManager.getImage(LoginFrame.class, "/image/3.png"));

		// 窗口大小
		shell.setSize(1027, 515);

		// 窗口文本
		shell.setText("\u5B66\u751F\u7BA1\u7406\u7CFB\u7EDF");

		// 记住密码的按钮
		Button btnCheckButton = new Button(shell, SWT.CHECK);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 获取焦点
				text_2.forceFocus();
			}
		});
		btnCheckButton.setBounds(406, 263, 119, 20);
		btnCheckButton.setText("记住密码");

		// 确定按钮
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// 获取用户名的账号和密码
				String sid = text.getText();
				String pwd = text_2.getText();

				// 验证用户名和密码是否正确
				try {
					// 加载驱动程序
					Class.forName(driver);

					// 声明 Connection 对象
					Connection con;

					// .getConnection()方法 连接MySQL数据库
					con = DriverManager.getConnection(url, user, password);

					// 新建 statement 对象 执行 SQL 语句
					String sql = "select * from stu where sid=? and pwd=?";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, sid);
					ps.setString(2, pwd);
					ResultSet rs = ps.executeQuery();

					// 循环遍历匹配的账号密码
					if (rs.next()) {
						MessageBox mb = new MessageBox(shell);
						mb.setText("提示信息");
						mb.setMessage("登陆成功！");
						mb.open();
						if (btnCheckButton.getSelection())
							rem = "1";
						else
							rem = "0";
						try {

							// .getConnection()方法 连接MySQL数据库
							con = DriverManager.getConnection(url, user, password);

							// 新建 statement 对象 执行 SQL 语句
							String sql2 = "update stu set rem=? where sid=?";
							PreparedStatement ps2 = con.prepareStatement(sql2);
							ps2.setString(1, rem);
							ps2.setString(2, sid);
							ps2.executeUpdate();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						// 关闭当前界面
						shell.dispose();

						// 跳转到主界面
						if (rs.getString(6).equals("0")) {
							tm.tray.dispose();
							new SMainFrame(sid);
							
						} else if (rs.getString(6).equals("1")) {
							tm.tray.dispose();
							new MainFrame(sid);
						}
					} else {

						// 判断账号密码是否为空
						if (sid.equals("账号") | pwd.equals("密码")) {
							MessageBox mb = new MessageBox(shell);
							mb.setText("提示信息");
							mb.setMessage("账号密码不能为空！");
							mb.open();

							// 密码不匹配
						} else {
							MessageBox mb = new MessageBox(shell);
							mb.setText("提示信息");
							mb.setMessage("密码错误！");
							mb.open();
						}
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// 确定按钮位置
		button.setBounds(400, 299, 98, 30);
		button.setText("\u767B\u9646");

		// 账号文本框
		text = new Text(shell, SWT.BORDER);
		text.setText("账号");

		// 判断焦点事件
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (text.getText().equals("账号"))
					text.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (text.getText().equals(""))
					text.setText("账号");
				if (text.getText().length() == 11) {
					try {
						// 加载驱动程序
						Class.forName(driver);

						// 声明 Connection
						Connection con;

						// .getConnection()方法 连接MySQL数据库
						con = DriverManager.getConnection(url, user, password);

						// 新建 statement 对象 执行 SQL 语句
						String sql = "select * from stu where sid=? ";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, text.getText());
						ResultSet rs = ps.executeQuery();
						if (rs.next()) {
							rem = rs.getString(7); 
							if (rem.equals("1")) {
								text_2.setText(rs.getString(5));
								
								// 获取焦点
								text_2.forceFocus();
								btnCheckButton.setSelection(true);
							}

						}
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		text.setBounds(406, 200, 224, 25);

		// 密码文本框
		text_1 = new Text(shell, SWT.BORDER);
		text_2 = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		text_1.setText("\u5BC6\u7801");

		// 判断焦点事件
		text_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (text_1.getText().equals("\u5BC6\u7801")) {
					if (!rem.equals("1"))
						text_2.setText("");
					text_1.setVisible(false);
					text_2.setVisible(true);
					text_2.forceFocus();
				}
			}
		});

		// 判断焦点事件
		text_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (text_2.getText().equals("")) {
					text_1.setText("\u5BC6\u7801");
					text_2.setVisible(false);
					text_1.setVisible(true);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		text_1.setBounds(406, 233, 224, 24);
		text_2.setBounds(406, 233, 224, 24);

		// 注册按钮
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 关闭当前窗口
				shell.dispose();
				tm.tray.dispose();

				// 跳转到注册界面
				new RegisterFrame();
			}
		});
		button_1.setBounds(532, 299, 98, 30);
		button_1.setText("\u6CE8\u518C");

	}
}
