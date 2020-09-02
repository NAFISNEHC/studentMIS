package com.cs.zn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class SeaAllComposite extends Composite {

	private Table table;

	private Text text;

	// ����������
	public String driver = "com.mysql.jdbc.Driver";

	// URL ָ�� ���ʵ����ݿ�����
	public String url = "jdbc:mysql://localhost:3306/student";

	// MySQL �û���
	public String user = "root";

	// MySQL ����
	public String password = "root";

	public SeaAllComposite(Composite parent, int style, Shell shell, boolean flag) {
		super(parent, style);
		// TODO �Զ����ɵĹ��캯�����
		// ��ѯ
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblNewLabel.setFont(SWTResourceManager.getFont("��Բ", 20, SWT.BOLD));
		lblNewLabel.setBounds(10, 10, 76, 33);
		lblNewLabel.setText("��ѯ");

		// �������
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setBounds(0, 49, 746, 431);

		text = new Text(composite, SWT.BORDER);
		text.setText("��������ҵ�����");
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (text.getText().equals("��������ҵ�����"))
					text.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (text.getText().equals("")) {
					text.setText("��������ҵ�����");
				}
			}
		});
		text.setBounds(10, 5, 200, 30);

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!text.getText().equals("��������ҵ�����")) {
					try {
						// ������������
						Class.forName(driver);

						// ���� Connection ����
						Connection con;

						// .getConnection()���� ����MySQL���ݿ�
						con = DriverManager.getConnection(url, user, password);
						String stt = text.getText();
						String adm = "2";
						if (stt.equals("����Ա"))
							adm = "1";
						else if (stt.equals("��ͨѧ��"))
							adm = "0";
						table.removeAll();
						String sql = "select * from stu where sid like ? or school like ? or name like ? or sex like ? or adm like ?";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, "%" + stt + "%");
						ps.setString(2, "%" + stt + "%");
						ps.setString(3, "%" + stt + "%");
						ps.setString(4, "%" + stt + "%");
						ps.setString(5, adm);
						ResultSet rs = ps.executeQuery();

						// ѭ����ֵ
						while (rs.next()) {
							TableItem ti = new TableItem(table, SWT.NONE);
							if (rs.getString(6).equals("1"))
								ti.setText(new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
										rs.getString(4), "����Ա" });
							else
								ti.setText(new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
										rs.getString(4), "��ͨѧ��" });
						}
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setImage(SWTResourceManager.getImage("./src/image/8.jpg"));
		btnNewButton.setBackgroundImage(SWTResourceManager.getImage("./src/image/8.jpg"));
		btnNewButton.setBounds(220, 2, 35, 35);
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 40, 726, 390);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				if (e.button == 3 && flag) {
					Menu menu = new Menu(table);
					table.setMenu(menu);
					MenuItem item_1 = new MenuItem(menu, SWT.PUSH);
					item_1.setText("ɾ��");
					item_1.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							int x = table.getSelectionIndex();
							TableItem t = table.getItem(table.getSelectionIndex());
							new DelComposite(shell, t.getText());
							table.removeAll();
							new SeaComposite(table);
						}
					});
				}
			}
		});
		TableColumn TColumn_1 = new TableColumn(table, SWT.NONE);
		TColumn_1.setWidth(220);
		TColumn_1.setText("ѧ��");

		TableColumn TColumn_2 = new TableColumn(table, SWT.NONE);
		TColumn_2.setWidth(150);
		TColumn_2.setText("ѧԺ");
		TColumn_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new SortTable(table);
			}
		});

		TableColumn TColumn_3 = new TableColumn(table, SWT.NONE);
		TColumn_3.setWidth(150);
		TColumn_3.setText("����");

		TableColumn TColumn_4 = new TableColumn(table, SWT.NONE);
		TColumn_4.setWidth(60);
		TColumn_4.setText("�Ա�");

		TableColumn TColumn_5 = new TableColumn(table, SWT.NONE);
		TColumn_5.setWidth(142);
		TColumn_5.setText("ְλ");

		new SeaComposite(table);
	}
}
