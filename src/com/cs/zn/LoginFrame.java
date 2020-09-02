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

	// �����
	protected Shell shell;

	// �˺��ı���
	private Text text;

	// �����ı���1
	private Text text_1;

	// �����ı���2 �����ʾ��ʾ��
	private Text text_2;

	private String rem = "0";

	// ����������
	public String driver = "com.mysql.jdbc.Driver";

	// URL ָ�� ���ʵ����ݿ�����
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL �û���
	public String user = "root";

	// MySQL ����
	public String password = "root";

	//	����
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
                	tm.tray.dispose();
            }
        });
		// ����λ��
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);
		shell.setLayoutDeferred(true);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

		// ���ڱ���ͼ
		shell.setBackgroundImage(
				SWTResourceManager.getImage(LoginFrame.class, "/image/2.jpg"));

		// ����ͼ��
		shell.setImage(SWTResourceManager.getImage(LoginFrame.class, "/image/3.png"));

		// ���ڴ�С
		shell.setSize(1027, 515);

		// �����ı�
		shell.setText("\u5B66\u751F\u7BA1\u7406\u7CFB\u7EDF");

		// ��ס����İ�ť
		Button btnCheckButton = new Button(shell, SWT.CHECK);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// ��ȡ����
				text_2.forceFocus();
			}
		});
		btnCheckButton.setBounds(406, 263, 119, 20);
		btnCheckButton.setText("��ס����");

		// ȷ����ť
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// ��ȡ�û������˺ź�����
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
					String sql = "select * from stu where sid=? and pwd=?";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, sid);
					ps.setString(2, pwd);
					ResultSet rs = ps.executeQuery();

					// ѭ������ƥ����˺�����
					if (rs.next()) {
						MessageBox mb = new MessageBox(shell);
						mb.setText("��ʾ��Ϣ");
						mb.setMessage("��½�ɹ���");
						mb.open();
						if (btnCheckButton.getSelection())
							rem = "1";
						else
							rem = "0";
						try {

							// .getConnection()���� ����MySQL���ݿ�
							con = DriverManager.getConnection(url, user, password);

							// �½� statement ���� ִ�� SQL ���
							String sql2 = "update stu set rem=? where sid=?";
							PreparedStatement ps2 = con.prepareStatement(sql2);
							ps2.setString(1, rem);
							ps2.setString(2, sid);
							ps2.executeUpdate();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						// �رյ�ǰ����
						shell.dispose();

						// ��ת��������
						if (rs.getString(6).equals("0")) {
							tm.tray.dispose();
							new SMainFrame(sid);
							
						} else if (rs.getString(6).equals("1")) {
							tm.tray.dispose();
							new MainFrame(sid);
						}
					} else {

						// �ж��˺������Ƿ�Ϊ��
						if (sid.equals("�˺�") | pwd.equals("����")) {
							MessageBox mb = new MessageBox(shell);
							mb.setText("��ʾ��Ϣ");
							mb.setMessage("�˺����벻��Ϊ�գ�");
							mb.open();

							// ���벻ƥ��
						} else {
							MessageBox mb = new MessageBox(shell);
							mb.setText("��ʾ��Ϣ");
							mb.setMessage("�������");
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

		// ȷ����ťλ��
		button.setBounds(400, 299, 98, 30);
		button.setText("\u767B\u9646");

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
				if (text.getText().equals(""))
					text.setText("�˺�");
				if (text.getText().length() == 11) {
					try {
						// ������������
						Class.forName(driver);

						// ���� Connection
						Connection con;

						// .getConnection()���� ����MySQL���ݿ�
						con = DriverManager.getConnection(url, user, password);

						// �½� statement ���� ִ�� SQL ���
						String sql = "select * from stu where sid=? ";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, text.getText());
						ResultSet rs = ps.executeQuery();
						if (rs.next()) {
							rem = rs.getString(7); 
							if (rem.equals("1")) {
								text_2.setText(rs.getString(5));
								
								// ��ȡ����
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

		// �����ı���
		text_1 = new Text(shell, SWT.BORDER);
		text_2 = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		text_1.setText("\u5BC6\u7801");

		// �жϽ����¼�
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

		// �жϽ����¼�
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

		// ע�ᰴť
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// �رյ�ǰ����
				shell.dispose();
				tm.tray.dispose();

				// ��ת��ע�����
				new RegisterFrame();
			}
		});
		button_1.setBounds(532, 299, 98, 30);
		button_1.setText("\u6CE8\u518C");

	}
}
