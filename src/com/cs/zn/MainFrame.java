package com.cs.zn;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class MainFrame {

	protected Shell shell;
	private String sid;
	public TrayMenu tm;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */

	MainFrame(String sid) {
		try {
			// ʵ�����Լ� ��������Լ���open����
			this.sid = sid;
			this.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {

		// ��ȡĬ�ϵ���ʾ�豸
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

		// ʵ��������
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
            		} catch (Exception e1) {
            			e1.printStackTrace();
            		}
                }
            }
        });
		shell.setLocation(new Point(96, 96));
		shell.setLayoutDeferred(true);
		shell.setBackgroundImage(null);
		shell.setImage(SWTResourceManager.getImage(MainFrame.class, "/image/3.png"));

		// ���ô��ڴ�С
		shell.setSize(1027, 515);

		// ���ô��ڱ���
		shell.setText("\u5B66\u751F\u7BA1\u7406\u7CFB\u7EDF");

		// �����
		Composite Lcomposite = new Composite(shell, SWT.NONE);
		Lcomposite.setBackgroundImage(SWTResourceManager.getImage("./src/image/4.jpg"));
		Lcomposite.setBackgroundMode(SWT.INHERIT_FORCE);
		Lcomposite.setBounds(0, 0, 276, 480);

		// �����
		Composite Rcomposite1 = new Composite(shell, SWT.NONE);
		Rcomposite1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		Rcomposite1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		Rcomposite1.setBackgroundMode(SWT.INHERIT_FORCE);
		Rcomposite1.setBounds(275, 0, 746, 480);

		// ����
		Tree tree = new Tree(Lcomposite, SWT.BORDER);
		tree.setBackground(SWTResourceManager.getColor(255, 232, 232));

		// �����
		TreeItem ti = new TreeItem(tree, SWT.NONE);
		ti.setText("���й���");

		// �ӽ��
		TreeItem ti_update = new TreeItem(ti, SWT.NONE);
		ti_update.setText("�޸�");

		TreeItem ti_add = new TreeItem(ti, SWT.NONE);
		ti_add.setText("���");

		TreeItem ti_search = new TreeItem(ti, SWT.NONE);
		ti_search.setText("��ѯ");

		TreeItem ti_search_1 = new TreeItem(ti_search, SWT.NONE);
		ti_search_1.setText("�鿴ȫ��");

		// ��ʼ��չ�����ڵ�
		ti.setExpanded(true);

		// ��ʼ����չ���� ��ѯ ���
		ti_search.setExpanded(false);

		tree.setBounds(0, 0, 276, 480);

		// ��������
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// ��ȡѡ�еĽ��
				TreeItem selectedTI = (TreeItem) e.item;

				// ���ս������
				String selStr = selectedTI.getText();

				// ����֮ǰ��������
				Control[] rightCs = Rcomposite1.getChildren();
				for (int i = 0; i < rightCs.length; i++) {
					rightCs[i].dispose();
				}

				// �жϵ���Ľ��
				if (selStr.equalsIgnoreCase("�޸�")) {
					new UpComposite(Rcomposite1, SWT.NONE, shell, sid);
				} else if (selStr.equalsIgnoreCase("�鿴ȫ��")) {
					new SeaAllComposite(Rcomposite1, SWT.NONE, shell, true);
				} else if (selStr.equalsIgnoreCase("���")) {
					new AddComposite(Rcomposite1, SWT.NONE, shell, sid);
				} else {
					// �������ͼƬ
					Composite composite_1 = new Composite(Rcomposite1, SWT.NONE);
					composite_1.setBackgroundImage(SWTResourceManager.getImage("./src/image/5.jpg"));
					composite_1.setBounds(0, 0, 746, 480);
				}
			}
		});

	}
}
