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
			// 实例化自己 方便调用自己的open方法
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

		// 获取默认的显示设备
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

		// 实例化窗口
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

		// 设置窗口大小
		shell.setSize(1027, 515);

		// 设置窗口标题
		shell.setText("\u5B66\u751F\u7BA1\u7406\u7CFB\u7EDF");

		// 左面板
		Composite Lcomposite = new Composite(shell, SWT.NONE);
		Lcomposite.setBackgroundImage(SWTResourceManager.getImage("./src/image/4.jpg"));
		Lcomposite.setBackgroundMode(SWT.INHERIT_FORCE);
		Lcomposite.setBounds(0, 0, 276, 480);

		// 右面板
		Composite Rcomposite1 = new Composite(shell, SWT.NONE);
		Rcomposite1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		Rcomposite1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		Rcomposite1.setBackgroundMode(SWT.INHERIT_FORCE);
		Rcomposite1.setBounds(275, 0, 746, 480);

		// 左树
		Tree tree = new Tree(Lcomposite, SWT.BORDER);
		tree.setBackground(SWTResourceManager.getColor(255, 232, 232));

		// 最大结点
		TreeItem ti = new TreeItem(tree, SWT.NONE);
		ti.setText("所有功能");

		// 子结点
		TreeItem ti_update = new TreeItem(ti, SWT.NONE);
		ti_update.setText("修改");

		TreeItem ti_add = new TreeItem(ti, SWT.NONE);
		ti_add.setText("添加");

		TreeItem ti_search = new TreeItem(ti, SWT.NONE);
		ti_search.setText("查询");

		TreeItem ti_search_1 = new TreeItem(ti_search, SWT.NONE);
		ti_search_1.setText("查看全部");

		// 初始化展开最大节点
		ti.setExpanded(true);

		// 初始化不展开拆 查询 结点
		ti_search.setExpanded(false);

		tree.setBounds(0, 0, 276, 480);

		// 树结点监听
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// 获取选中的结点
				TreeItem selectedTI = (TreeItem) e.item;

				// 接收结点名字
				String selStr = selectedTI.getText();

				// 销毁之前面板的内容
				Control[] rightCs = Rcomposite1.getChildren();
				for (int i = 0; i < rightCs.length; i++) {
					rightCs[i].dispose();
				}

				// 判断点击的结点
				if (selStr.equalsIgnoreCase("修改")) {
					new UpComposite(Rcomposite1, SWT.NONE, shell, sid);
				} else if (selStr.equalsIgnoreCase("查看全部")) {
					new SeaAllComposite(Rcomposite1, SWT.NONE, shell, true);
				} else if (selStr.equalsIgnoreCase("添加")) {
					new AddComposite(Rcomposite1, SWT.NONE, shell, sid);
				} else {
					// 最初界面图片
					Composite composite_1 = new Composite(Rcomposite1, SWT.NONE);
					composite_1.setBackgroundImage(SWTResourceManager.getImage("./src/image/5.jpg"));
					composite_1.setBounds(0, 0, 746, 480);
				}
			}
		});

	}
}
