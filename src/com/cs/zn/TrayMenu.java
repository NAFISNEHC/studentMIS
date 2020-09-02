package com.cs.zn;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

public class TrayMenu {
	public Tray tray;
	TrayMenu(Shell shell, Display display) {
		final Menu menu = new Menu(shell);
		final MenuItem mi1 = new MenuItem(menu, SWT.PUSH);
		mi1.setText("隐藏窗口");
		mi1.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (shell.isVisible()) {
					shell.setVisible(false);
					mi1.setText("显示窗口");
				} else {
					shell.setVisible(true);
					mi1.setText("隐藏窗口");
					shell.forceActive();
				}
			}
		});
		MenuItem quit = new MenuItem(menu, SWT.PUSH);
		quit.setText("退出");
		quit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				display.dispose();
				System.exit(0);
			}
		});
		tray = display.getSystemTray(); // 构造 Tree 对象，设置样式
		TrayItem trayitem1 = new TrayItem(tray, SWT.DROP_DOWN); // 构造 TreeItem对象，设置样式
		
		trayitem1.setText("托盘");
		trayitem1.setToolTipText("学生管理系统");
		trayitem1.setImage(new Image(display,"./src/image/13.png"));
		trayitem1.addMenuDetectListener(new MenuDetectListener() {
			public void menuDetected(MenuDetectEvent arg0) {
				menu.setLocation(display.getCursorLocation());
				menu.setVisible(true);
			}
		});
	}
}
