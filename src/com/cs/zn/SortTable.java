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

		// sortCols 数组存放所有列的索引
		int[] sortCols = new int[table.getColumnCount()];
		for (int i = 1; i < sortCols.length; i++) {
			sortCols[i] = i;
		}

		this.table = table;

		// 需要排序的列的索引
		this.sortColIndexes = sortCols;

		// 存放所有列的降序升序控制变量
		this.sortFlags = new int[table.getColumnCount()];

		init();
	}

	private void init() {

		// 遍历所有列 每一列添加监听事件
		for (int i = 0; i < sortColIndexes.length; i++) {
			final int sortColIndex = this.sortColIndexes[i];
			TableColumn col = table.getColumn(sortColIndex);

			col.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {

					// 处理该列的升序降序
					columnHandleEvent(event, sortColIndex);
				}
			});
		}
	}

	private void columnHandleEvent(Event event, int sortColIndex) {
		try {

			// 存放所有列的地址
			for (int i = 0; i < sortColIndexes.length; i++) {
				table.getColumn(i);
			}

			// 判断该列的升序和降序
			if (this.sortFlags[sortColIndex] == 1) {
				clearSortFlags();
				this.sortFlags[sortColIndex] = -1;

				// 升序
				this.addStringSorter(table.getColumn(sortColIndex), true);
			} else {
				this.sortFlags[sortColIndex] = 1;

				// 降序
				this.addStringSorter(table.getColumn(sortColIndex), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addStringSorter(TableColumn column, boolean isAscend) {

		Collator comparator = Collator.getInstance(Locale.getDefault());

		// 获取排序列的索引
		int columnIndex = getColumnIndex(table, column);

		// 存放所有行
		TableItem[] items = table.getItems();

		// 使用冒泡法进行排序
		for (int i = 1; i < items.length; i++) {

			// 存放该列的值
			String str2value = items[i].getText(columnIndex);
			if (str2value.equalsIgnoreCase("")) {
				// 当遇到表格中的空项目时就停止往下检索排序项目
				break;
			}
			for (int j = 0; j < i; j++) {

				// 存放其他列的值
				String str1value = items[j].getText(columnIndex);

				// 比较两值
				boolean isLessThan = comparator.compare(str2value, str1value) < 0;

				// 判断降序升序以及两值关系
				if ((isAscend && isLessThan) || (!isAscend && !isLessThan)) {

					// 存放所有值
					String[] values = getTableItemText(table, items[i]);

					// 获取该列所有值
					Object obj = items[i].getData();

					// 销毁该列
					items[i].dispose();

					// 新增该列
					TableItem item = new TableItem(table, SWT.NONE, j);

					// 重新赋予数据
					item.setText(values);
					item.setData(obj);

					// 初始化数组 重新获得所有行
					items = table.getItems();
					break;
				}
			}
		}

		// 设置该列到table
		table.setSortColumn(column);

		// 判断升序降序
		table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
		isAscend = !isAscend;
	}

	private int getColumnIndex(Table table, TableColumn column) {

		// 存放所有列 
		TableColumn[] columns = table.getColumns();

		// 找到匹配的列 返回该列索引
		for (int i = 0; i < columns.length; i++) {
			if (columns[i].equals(column))
				return i;
		}
		return -1;
	}

	private String[] getTableItemText(Table table, TableItem item) {

		// 列数
		int count = table.getColumnCount();

		// 获取该列每行的值
		String[] strs = new String[count];
		for (int i = 0; i < count; i++) {
			strs[i] = item.getText(i);
		}
		return strs;
	}

	private void clearSortFlags() {

		// 初始化所有列的升序降序控制变量
		for (int i = 0; i < table.getColumnCount(); i++) {
			this.sortFlags[i] = 0;
		}
	}
}