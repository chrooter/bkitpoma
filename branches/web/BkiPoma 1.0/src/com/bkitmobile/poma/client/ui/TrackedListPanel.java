package com.bkitmobile.poma.client.ui;

import java.util.ArrayList;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.*;
import com.gwtext.client.widgets.*;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.*;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.grid.*;

public class TrackedListPanel extends LoadingPanel {

	public static int test = 0;

	public static final int FIELD_DATE = 0;
	public static final int FIELD_STRING = 1;
	// public static final int FIELD_BOOLEAN_ = 2;
	// public static final int FIELD_FLOAT = 3;
	// public static final int FIELD_INTEGER = 4;

	private static final String DATE_FORMAT = "m/d/Y";

	// private final AccordionLayout accordion = new AccordionLayout(true);

	private PagingMemoryProxy proxy;
	private boolean pagingEnable = true;
	private GridPanel gridPanel;
	private Store store;
	private PagingToolbar pagingToolbar;
	private int pageSize = 3;
	private ArrayReader reader;
	private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	private RecordDef recordDef;
	private boolean isNewData = true;

	public TrackedListPanel(int columnFieldType[], String columnHeader[]) {
		super("List Point");

		constructor(columnFieldType, columnHeader);
	}

	public TrackedListPanel(int[] columnFieldType, String[] columnHeader,
			String[][] data) {
		super("List Point");

		constructor(columnFieldType, columnHeader);
		setData(data);
	}

	private void constructor(int[] columnFieldType, String columnHeader[]) {
		gridPanel = new GridPanel();
		recordDef = new RecordDef(getRecordDef(columnFieldType));
		reader = new ArrayReader(recordDef);

		store = new Store(reader);
		ColumnModel columnModel = new ColumnModel(getColumnConfig(columnHeader));
		gridPanel.setColumnModel(columnModel);

		gridPanel.setStore(store);
		add(gridPanel);

		setHeight(150);
		setGridHeight(130);
		setCollapsible(true);

		pagingToolbar = new PagingToolbar(store);
		pagingToolbar.setPageSize(pageSize);
		pagingToolbar.setDisplayInfo(true);
		pagingToolbar.setEmptyMsg("No topics to display");
		pagingToolbar.addSeparator();
		pagingToolbar.addItem(new ToolbarTextItem("Item per page : "));
		NumberField itemPerPageField = new NumberField();
		itemPerPageField.setWidth(40);
		itemPerPageField.setSelectOnFocus(true);
		itemPerPageField.addListener(new FieldListenerAdapter() {
			@Override
			public void onSpecialKey(Field field, EventObject e) {
				// TODO Auto-generated method stub
				boolean isKeyEnter = e.getKey() == EventObject.ENTER;
				if (isKeyEnter) {
					String values = field.getValueAsString();
					double temp = Double.parseDouble(values);
					int newPageSize = (int) temp;
					setPageSize(newPageSize);
				}
			}
		});
		pagingToolbar.addField(itemPerPageField);

		setPageSize(3);
		gridPanel.setBottomToolbar(pagingToolbar);
		gridPanel.setLoadMask(true);
		setAutoExpandColumn(2);

	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;

		if (pagingEnable) {
			pagingToolbar.setPageSize(pageSize);
			store.load(0, pageSize);
		} else {
			store.load();
		}
	}

	public void setGridWidth(int width) {
		gridPanel.setWidth(width);
	}

	public void setGridHeight(int height) {
		gridPanel.setHeight(height);
	}

	public void setPagingToolbarEnable(boolean enable) {
		pagingEnable = enable;
		setPageSize(pageSize);
		pagingToolbar.setVisible(pagingEnable);
	}

	public void setData(String[][] data) {
		if (isNewData == true) {
			this.data.clear();
			for (int i = 0; i < data.length; i++) {
				ArrayList<String> row = new ArrayList<String>();
				for (int j = 0; j < data[i].length; j++) {
					row.add(data[i][j]);
				}
				this.data.add(row);
			}
		} else {
			isNewData = true;
		}

		proxy = new PagingMemoryProxy(data);
		store.setDataProxy(proxy);
		setPageSize(pageSize);
	}

	private void setData(ArrayList<ArrayList<String>> data) {
		String[][] dataInString = new String[data.size()][];
		for (int i = 0; i < dataInString.length; i++) {
			ArrayList<String> row = data.get(i);
			dataInString[i] = new String[row.size()];
			for (int j = 0; j < dataInString[i].length; j++) {
				dataInString[i][j] = row.get(j);
			}
		}
		setData(dataInString);
		isNewData = false;
	}

	public void addRecord(String[] data) {
		addRecord(data, store.getTotalCount());
	}

	public void addRecord(String data[], int position) {
		ArrayList<String> newRow = new ArrayList<String>();
		for (int i = 0; i < data.length; i++) {
			newRow.add(data[i]);
		}
		this.data.add(position, newRow);
		setData(this.data);
	}

	public void removeRecord(int position) {
		if (position < data.size()) {
			data.remove(position);
			setData(data);
		}
	}

	public int getRecordsCount() {
		return data.size();
	}

	public int getColumnCount() {
		if (data.size() == 0) {
			return 0;
		}
		return data.get(0).size();
	}

	public void setAutoExpandColumn(int index) {
		gridPanel.setAutoExpandColumn(index + "");
	}

	public GridPanel getGridPanel() {
		return gridPanel;
	}

	private static FieldDef[] getRecordDef(int[] columnFieldType) {
		FieldDef[] fieldDef = new FieldDef[columnFieldType.length];
		for (int i = 0; i < columnFieldType.length; i++) {
			try {
				fieldDef[i] = getFieldDef(columnFieldType[i], i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fieldDef;
	}

	private static FieldDef getFieldDef(int fieldType, int position)
			throws Exception {
		switch (fieldType) {
		case FIELD_STRING:
			return new StringFieldDef(position + "");
		case FIELD_DATE:
			return new DateFieldDef(position + "", DATE_FORMAT);
		default:
			throw new Exception("Field Type is incorrect");
		}
	}

	private static ColumnConfig[] getColumnConfig(String[] columnHeader) {
		ColumnConfig columnConfig[] = new ColumnConfig[columnHeader.length];
		for (int i = 0; i < columnConfig.length; i++) {
			columnConfig[i] = new ColumnConfig(columnHeader[i], i + "");
		}
		return columnConfig;
	}
}