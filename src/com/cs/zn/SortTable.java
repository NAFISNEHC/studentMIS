package com.cs.zn;

import java.text.Collator;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class SortTable {
	private Table table;

	private int[] sortFlags;
	private int[] sortColIndexes;

	public SortTable(Table table) {

		// sortCols �����������е�����
		int[] sortCols = new int[table.getColumnCount()];
		for (int i = 1; i < sortCols.length; i++) {
			sortCols[i] = i;
		}

		this.table = table;

		// ��Ҫ������е�����
		this.sortColIndexes = sortCols;

		// ��������еĽ���������Ʊ���
		this.sortFlags = new int[table.getColumnCount()];

		init();
	}

	private void init() {

		// ���������� ÿһ����Ӽ����¼�
		for (int i = 0; i < sortColIndexes.length; i++) {
			final int sortColIndex = this.sortColIndexes[i];
			TableColumn col = table.getColumn(sortColIndex);

			col.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {

					// ������е�������
					columnHandleEvent(event, sortColIndex);
				}
			});
		}
	}

	private void columnHandleEvent(Event event, int sortColIndex) {
		try {

			// ��������еĵ�ַ
			for (int i = 0; i < sortColIndexes.length; i++) {
				table.getColumn(i);
			}

			// �жϸ��е�����ͽ���
			if (this.sortFlags[sortColIndex] == 1) {
				clearSortFlags();
				this.sortFlags[sortColIndex] = -1;

				// ����
				this.addStringSorter(table.getColumn(sortColIndex), true);
			} else {
				this.sortFlags[sortColIndex] = 1;

				// ����
				this.addStringSorter(table.getColumn(sortColIndex), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addStringSorter(TableColumn column, boolean isAscend) {

		Collator comparator = Collator.getInstance(Locale.getDefault());

		// ��ȡ�����е�����
		int columnIndex = getColumnIndex(table, column);

		// ���������
		TableItem[] items = table.getItems();

		// ʹ��ð�ݷ���������
		for (int i = 1; i < items.length; i++) {

			// ��Ÿ��е�ֵ
			String str2value = items[i].getText(columnIndex);
			if (str2value.equalsIgnoreCase("")) {
				// ����������еĿ���Ŀʱ��ֹͣ���¼���������Ŀ
				break;
			}
			for (int j = 0; j < i; j++) {

				// ��������е�ֵ
				String str1value = items[j].getText(columnIndex);

				// �Ƚ���ֵ
				boolean isLessThan = comparator.compare(str2value, str1value) < 0;

				// �жϽ��������Լ���ֵ��ϵ
				if ((isAscend && isLessThan) || (!isAscend && !isLessThan)) {

					// �������ֵ
					String[] values = getTableItemText(table, items[i]);

					// ��ȡ��������ֵ
					Object obj = items[i].getData();

					// ���ٸ���
					items[i].dispose();

					// ��������
					TableItem item = new TableItem(table, SWT.NONE, j);

					// ���¸�������
					item.setText(values);
					item.setData(obj);

					// ��ʼ������ ���»��������
					items = table.getItems();
					break;
				}
			}
		}

		// ���ø��е�table
		table.setSortColumn(column);

		// �ж�������
		table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
		isAscend = !isAscend;
	}

	private int getColumnIndex(Table table, TableColumn column) {

		// ��������� 
		TableColumn[] columns = table.getColumns();

		// �ҵ�ƥ����� ���ظ�������
		for (int i = 0; i < columns.length; i++) {
			if (columns[i].equals(column))
				return i;
		}
		return -1;
	}

	private String[] getTableItemText(Table table, TableItem item) {

		// ����
		int count = table.getColumnCount();

		// ��ȡ����ÿ�е�ֵ
		String[] strs = new String[count];
		for (int i = 0; i < count; i++) {
			strs[i] = item.getText(i);
		}
		return strs;
	}

	private void clearSortFlags() {

		// ��ʼ�������е���������Ʊ���
		for (int i = 0; i < table.getColumnCount(); i++) {
			this.sortFlags[i] = 0;
		}
	}
}