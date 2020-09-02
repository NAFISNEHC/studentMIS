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

	// �����
	protected Shell shell;

	// �˺�
	private Text text;

	// ����
	private Text text_1;

	// ��������
	private Text text_2;

	// ����
	private Text text_3;

	// ѧԺ
	private Text text_4;

	// ��֤��
	private Text text_5;
	private String str;

	// �Ա�
	private String sex = "";

	// ������֤��ı仯
	private static int i = 0;

	// ����������
	public String driver = "com.mysql.jdbc.Driver";

	// URL ָ�� ���ʵ����ݿ�����
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL �û���
	public String user = "root";

	// MySQL ����
	public String password = "root";
	
	// ����
	public TrayMenu tm;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	RegisterFrame() {
		try {
			// ʵ�����Լ� ��������Լ���open����
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

		// ��������
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

		// �����
		shell = new Shell(SWT.DIALOG_TRIM);
		shell.addShellListener(new ShellAdapter() {
            public void shellClosed(final ShellEvent e) {
                MessageBox messageBox = new MessageBox(shell,
                        SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
                messageBox.setText("ȷ��");
                messageBox.setMessage("��ȷ��Ҫ�˳���");
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

		// ͼ��
		shell.setImage(SWTResourceManager.getImage(RegisterFrame.class, "/image/6.png"));

		// ����
		shell.setBackgroundImage(SWTResourceManager.getImage(RegisterFrame.class, "/image/7.jpg"));
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

		// ��С
		shell.setSize(1027, 515);

		try {

			// ��ʼ����֤��
			RandCod ranC = new RandCod(i);
			str = ranC.strr;
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// �鿴����İ�ť
		Button btnCheckButton = new Button(shell, SWT.CHECK);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// �ɲ鿴����
				if (btnCheckButton.getSelection()) {
					text_1.setText(text_2.getText());
					text_2.setVisible(false);
					text_1.setVisible(true);

					// ��ȡ����
					text_1.forceFocus();

					// ѡ��ȫ������
					text_1.selectAll();
				}

				// ���ɲ鿴����
				else {
					if (text_1.getText().equals("����"))
						text_2.setText("");
					else
						text_2.setText(text_1.getText());
					text_1.setVisible(false);
					text_2.setVisible(true);

					// ��ȡ����
					text_2.forceFocus();

					// ѡ��ȫ������
					text_2.selectAll();
				}
			}
		});
		btnCheckButton.setBounds(406, 263, 119, 20);
		btnCheckButton.setText("\u663E\u793A\u5BC6\u7801");

		// ȷ����ť
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// ��ȡ�û������˺ź�����
				if (text_1.equals("����"))
					text_2.setText("");
				else
					text_2.setText(text_1.getText());
				String sid = text.getText();
				String pwd = text_2.getText();

				// ��֤�û����������Ƿ���ȷ
				try {

					// ������������
					Class.forName(driver);

					// ���� Connection ����
					Connection con;

					// .getConnection()���� ����MySQL���ݿ�
					con = DriverManager.getConnection(url, user, password);

					// �½� statement ���� ִ�� SQL ���
					String sql = "select * from stu where sid=?";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, sid);
					ResultSet rs = ps.executeQuery();

					// ѭ�����������Ƿ���ڵ�ǰ�˺�
					if (rs.next()) {
						MessageBox mb = new MessageBox(shell);
						mb.setText("��ʾ��Ϣ");
						mb.setMessage("ע��ʧ�ܣ��Ѵ��ڸ��˺ţ�");
						mb.open();
					} else {
						if (sid.equals("�˺�") | pwd.equals("") | pwd.equals("����")) {
							MessageBox mb = new MessageBox(shell);
							mb.setText("��ʾ��Ϣ");
							mb.setMessage("�˺����벻��Ϊ�գ�");
							mb.open();
						} else {
							MessageBox mb = new MessageBox(shell);
							mb.setText("��ʾ��Ϣ");
							mb.setMessage("ע��ɹ���");
							mb.open();

							// �ı䵱ǰ����
							text.setVisible(false);
							text_1.setVisible(false);
							text_2.setVisible(false);
							button.setVisible(false);
							btnCheckButton.setVisible(false);

							// ��д������ѧԺ ��֤��
							text_3 = new Text(shell, SWT.BORDER);
							text_3.setText("����");
							text_3.addFocusListener(new FocusAdapter() {
								@Override
								public void focusGained(FocusEvent e) {
									if (text_3.getText().equals("����"))
										text_3.setText("");
								}

								@Override
								public void focusLost(FocusEvent e) {
									if (text_3.getText().equals("")) {
										text_3.setText("����");
									}
								}
							});
							text_3.setBounds(406, 200, 224, 25);

							text_4 = new Text(shell, SWT.BORDER);
							text_4.setText("ѧԺ");
							text_4.addFocusListener(new FocusAdapter() {
								@Override
								public void focusGained(FocusEvent e) {
									if (text_4.getText().equals("ѧԺ"))
										text_4.setText("");
								}

								@Override
								public void focusLost(FocusEvent e) {
									if (text_4.getText().equals("")) {
										text_4.setText("ѧԺ");
									}
								}
							});
							text_4.setBounds(406, 233, 224, 24);

							text_5 = new Text(shell, SWT.BORDER);
							text_5.setText("��֤��");
							text_5.addFocusListener(new FocusAdapter() {
								@Override
								public void focusGained(FocusEvent e) {
									if (text_5.getText().equals("��֤��"))
										text_5.setText("");
								}

								@Override
								public void focusLost(FocusEvent e) {
									if (text_5.getText().equals("")) {
										text_5.setText("��֤��");
									}
								}
							});
							text_5.setBounds(406, 266, 100, 25);

							// �Ա�
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

							// ������֤�밴ť
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

							// ȷ����ť
							Button button_1 = new Button(shell, SWT.NONE);
							button_1.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									try {

										// ��ȡ����������ύ�����ݿ�
										String name = text_3.getText();
										String sch = text_4.getText();
										if (btnRadioButton.getSelection() & !btnRadioButton_1.getSelection())
											sex = "��";
										else if (!btnRadioButton.getSelection() & btnRadioButton_1.getSelection())
											sex = "Ů";
										String sql_1 = "insert into stu values(?,?,?,?,?,?,?)";

										// �ж��Ƿ���ϢΪ��
										if (sch.equals("ѧԺ") | name.equals("����")) {
											MessageBox mb = new MessageBox(shell);
											mb.setText("��ʾ��Ϣ");
											mb.setMessage("ѧԺ��������Ϊ�գ�");
											mb.open();

											// �ж���֤���Ƿ�������ȷ
										} else if (!str.equals(text_5.getText())) {
											MessageBox mb = new MessageBox(shell);
											mb.setText("��ʾ��Ϣ");
											mb.setMessage("��֤�����");
											mb.open();

											// �ύ�����ݿ�
										} else if (sex == "") {
											MessageBox mb = new MessageBox(shell);
											mb.setText("��ʾ��Ϣ");
											mb.setMessage("��ѡ���Ա�");
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
											mb.setText("��ʾ��Ϣ");
											mb.setMessage("��ӳɹ���");
											mb.open();
											shell.dispose();

											// ��ת��½����
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

		// �˺��ı���
		text = new Text(shell, SWT.BORDER);
		text.setText("�˺�");

		// �жϽ����¼�
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (text.getText().equals("�˺�"))
					text.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (text.getText().equals("")) {
					text.setText("�˺�");
				}
			}
		});
		text.setBounds(406, 200, 224, 25);

		// �����ı���
		text_1 = new Text(shell, SWT.BORDER);
		text_2 = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		text_1.setText("\u5BC6\u7801");

		// �жϽ����¼�
		text_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				// �ɲ鿴����
				if (btnCheckButton.getSelection()) {

					// �����ı���ֵ
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

				// ���ɲ鿴����
				if (!btnCheckButton.getSelection()) {
					if (!text_1.getText().equals("����"))
						text_2.setText(text_1.getText());
					if (text_1.getText().equals("\u5BC6\u7801")) {
						text_2.setText("");
						text_1.setVisible(false);
						text_2.setVisible(true);

						// ��ȡ����
						text_2.forceFocus();
					}
				} else {
					if (text_1.getText().equals("\u5BC6\u7801")) {
						text_1.setText("");
					}
				}
			}
		});

		// �жϽ����¼�
		text_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				// �ɲ鿴����
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
