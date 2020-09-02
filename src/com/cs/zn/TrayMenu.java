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
		mi1.setText("���ش���");
		mi1.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (shell.isVisible()) {
					shell.setVisible(false);
					mi1.setText("��ʾ����");
				} else {
					shell.setVisible(true);
					mi1.setText("���ش���");
					shell.forceActive();
				}
			}
		});
		MenuItem quit = new MenuItem(menu, SWT.PUSH);
		quit.setText("�˳�");
		quit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				display.dispose();
				System.exit(0);
			}
		});
		tray = display.getSystemTray(); // ���� Tree ����������ʽ
		TrayItem trayitem1 = new TrayItem(tray, SWT.DROP_DOWN); // ���� TreeItem����������ʽ
		
		trayitem1.setText("����");
		trayitem1.setToolTipText("ѧ������ϵͳ");
		trayitem1.setImage(new Image(display,"./src/image/13.png"));
		trayitem1.addMenuDetectListener(new MenuDetectListener() {
			public void menuDetected(MenuDetectEvent arg0) {
				menu.setLocation(display.getCursorLocation());
				menu.setVisible(true);
			}
		});
	}
}
