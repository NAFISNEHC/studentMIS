package com.cs.zn;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class RegisterFrame {

	// 主面板
	protected Shell shell;

	// 账号
	private Text text;

	// 密码
	private Text text_1;

	// 密码掩码
	private Text text_2;

	// 姓名
	private Text text_3;

	// 学院
	private Text text_4;

	// 验证码
	private Text text_5;
	private String str;

	// 性别
	private String sex = "";

	// 控制验证码的变化
	private static int i = 0;

	// 驱动程序名
	public String driver = "com.mysql.jdbc.Driver";

	// URL 指向 访问的数据库名字
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL 用户名
	public String user = "root";

	// MySQL 密码
	public String password = "root";
	
	// 托盘
	public TrayMenu tm;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	RegisterFrame() {
		try {
			// 实例化自己 方便调用自己的open方法
			this.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
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

		// 主面板
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
                {
                	shell.dispose();
                	try {
                		tm.tray.dispose();
            			LoginFrame window = new LoginFrame();
            			window.open();
            		} catch (Exception e2) {
            			e2.printStackTrace();
            		}
                }
            }
        });
		shell.setText("\u6CE8\u518C");
		shell.setLayoutDeferred(true);
		shell.setLocation(new Point(96, 96));

		// 图标
		shell.setImage(SWTResourceManager.getImage(RegisterFrame.class, "/image/6.png"));

		// 背景
		shell.setBackgroundImage(SWTResourceManager.getImage(RegisterFrame.class, "/image/7.jpg"));
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

		// 大小
		shell.setSize(1027, 515);

		try {

			// 初始化验证码
			RandCod ranC = new RandCod(i);
			str = ranC.strr;
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 查看密码的按钮
		Button btnCheckButton = new Button(shell, SWT.CHECK);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// 可查看密码
				if (btnCheckButton.getSelection()) {
					text_1.setText(text_2.getText());
					text_2.setVisible(false);
					text_1.setVisible(true);

					// 获取焦点
					text_1.forceFocus();

					// 选择全部内容
					text_1.selectAll();
				}

				// 不可查看密码
				else {
					if (text_1.getText().equals("密码"))
						text_2.setText("");
					else
						text_2.setText(text_1.getText());
					text_1.setVisible(false);
					text_2.setVisible(true);

					// 获取焦点
					text_2.forceFocus();

					// 选择全部内容
					text_2.selectAll();
				}
			}
		});
		btnCheckButton.setBounds(406, 263, 119, 20);
		btnCheckButton.setText("\u663E\u793A\u5BC6\u7801");

		// 确定按钮
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 获取用户名的账号和密码
				if (text_1.equals("密码"))
					text_2.setText("");
				else
					text_2.setText(text_1.getText());
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
					String sql = "select * from stu where sid=?";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, sid);
					ResultSet rs = ps.executeQuery();

					// 循环遍历查找是否存在当前账号
					if (rs.next()) {
						MessageBox mb = new MessageBox(shell);
						mb.setText("提示信息");
						mb.setMessage("注册失败！已存在该账号！");
						mb.open();
					} else {
						if (sid.equals("账号") | pwd.equals("") | pwd.equals("密码")) {
							MessageBox mb = new MessageBox(shell);
							mb.setText("提示信息");
							mb.setMessage("账号密码不能为空！");
							mb.open();
						} else {
							MessageBox mb = new MessageBox(shell);
							mb.setText("提示信息");
							mb.setMessage("注册成功！");
							mb.open();

							// 改变当前界面
							text.setVisible(false);
							text_1.setVisible(false);
							text_2.setVisible(false);
							button.setVisible(false);
							btnCheckButton.setVisible(false);

							// 填写姓名和学院 验证码
							text_3 = new Text(shell, SWT.BORDER);
							text_3.setText("姓名");
							text_3.addFocusListener(new FocusAdapter() {
								@Override
								public void focusGained(FocusEvent e) {
									if (text_3.getText().equals("姓名"))
										text_3.setText("");
								}

								@Override
								public void focusLost(FocusEvent e) {
									if (text_3.getText().equals("")) {
										text_3.setText("姓名");
									}
								}
							});
							text_3.setBounds(406, 200, 224, 25);

							text_4 = new Text(shell, SWT.BORDER);
							text_4.setText("学院");
							text_4.addFocusListener(new FocusAdapter() {
								@Override
								public void focusGained(FocusEvent e) {
									if (text_4.getText().equals("学院"))
										text_4.setText("");
								}

								@Override
								public void focusLost(FocusEvent e) {
									if (text_4.getText().equals("")) {
										text_4.setText("学院");
									}
								}
							});
							text_4.setBounds(406, 233, 224, 24);

							text_5 = new Text(shell, SWT.BORDER);
							text_5.setText("验证码");
							text_5.addFocusListener(new FocusAdapter() {
								@Override
								public void focusGained(FocusEvent e) {
									if (text_5.getText().equals("验证码"))
										text_5.setText("");
								}

								@Override
								public void focusLost(FocusEvent e) {
									if (text_5.getText().equals("")) {
										text_5.setText("验证码");
									}
								}
							});
							text_5.setBounds(406, 266, 100, 25);

							// 性别
							Button btnRadioButton = new Button(shell, SWT.RADIO);
							btnRadioButton.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
								}
							});
							btnRadioButton.setBounds(434, 299, 60, 30);
							btnRadioButton.setText("\u7537");

							Button btnRadioButton_1 = new Button(shell, SWT.RADIO);
							btnRadioButton.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
								}
							});
							btnRadioButton_1.setBounds(564, 299, 60, 30);
							btnRadioButton_1.setText("\u5973");

							// 更换验证码按钮
							Button btnCod = new Button(shell, SWT.NONE);
							btnCod.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									try {
										i++;
										RandCod ranC = new RandCod(i);
										str = ranC.strr;
										String st;
										String ts;
										String ss = "00";
										int j = i;
										while (j-- > 0) {
											ss += "0";
										}
										st = "./src/randCod/";
										ts = ".png";
										btnCod.setImage(SWTResourceManager.getImage(st + ss + ts));
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							});

							btnCod.setImage(
									SWTResourceManager.getImage("./src/randCod/00.png"));
							btnCod.setBounds(558, 263, 72, 30);

							// 确定按钮
							Button button_1 = new Button(shell, SWT.NONE);
							button_1.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									try {

										// 获取输入的内容提交到数据库
										String name = text_3.getText();
										String sch = text_4.getText();
										if (btnRadioButton.getSelection() & !btnRadioButton_1.getSelection())
											sex = "男";
										else if (!btnRadioButton.getSelection() & btnRadioButton_1.getSelection())
											sex = "女";
										String sql_1 = "insert into stu values(?,?,?,?,?,?,?)";

										// 判断是否信息为空
										if (sch.equals("学院") | name.equals("姓名")) {
											MessageBox mb = new MessageBox(shell);
											mb.setText("提示信息");
											mb.setMessage("学院姓名不能为空！");
											mb.open();

											// 判断验证码是否输入正确
										} else if (!str.equals(text_5.getText())) {
											MessageBox mb = new MessageBox(shell);
											mb.setText("提示信息");
											mb.setMessage("验证码错误！");
											mb.open();

											// 提交到数据库
										} else if (sex == "") {
											MessageBox mb = new MessageBox(shell);
											mb.setText("提示信息");
											mb.setMessage("请选择性别！");
											mb.open();
										} else {
											PreparedStatement ps = con.prepareStatement(sql_1);
											ps.setString(1, sid);
											ps.setString(2, sch);
											ps.setString(3, name);
											ps.setString(4, sex);
											ps.setString(5, pwd);
											ps.setString(6, "1");
											ps.setString(7, "0");
											ps.executeUpdate();
											MessageBox mb = new MessageBox(shell);
											mb.setText("提示信息");
											mb.setMessage("添加成功！");
											mb.open();
											shell.dispose();

											// 跳转登陆界面
											LoginFrame t2 = new LoginFrame();
											tm.tray.dispose();
											t2.open();
										}
									} catch (SQLException e2) {
										e2.printStackTrace();
									}
								}
							});
							button_1.setBounds(464, 349, 98, 30);
							button_1.setText("\u786E\u5B9A");
						}
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(464, 309, 98, 30);
		button.setText("\u786E\u5B9A");

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
				if (text.getText().equals("")) {
					text.setText("账号");
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

				// 可查看密码
				if (btnCheckButton.getSelection()) {

					// 密码文本框传值
					if (!text_1.getText().equals(""))
						text_2.setText(text_1.getText());
					else
						text_1.setText("\u5BC6\u7801");
				} else {
					if (text_1.getText().equals(""))
						text_1.setText("\u5BC6\u7801");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {

				// 不可查看密码
				if (!btnCheckButton.getSelection()) {
					if (!text_1.getText().equals("密码"))
						text_2.setText(text_1.getText());
					if (text_1.getText().equals("\u5BC6\u7801")) {
						text_2.setText("");
						text_1.setVisible(false);
						text_2.setVisible(true);

						// 获取焦点
						text_2.forceFocus();
					}
				} else {
					if (text_1.getText().equals("\u5BC6\u7801")) {
						text_1.setText("");
					}
				}
			}
		});

		// 判断焦点事件
		text_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				// 可查看密码
				if (btnCheckButton.getSelection()) {
					if (!text_2.getText().equals(""))
						text_1.setText(text_2.getText());
					else
						text_1.setText("\u5BC6\u7801");
				} else {
					if (text_2.getText().equals("")) {
						text_1.setText("\u5BC6\u7801");
						text_2.setVisible(false);
						text_1.setVisible(true);
					} else {
						text_1.setText(text_2.getText());
					}
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		text_1.setBounds(406, 233, 224, 24);
		text_2.setBounds(406, 233, 224, 24);
	}
}
