package cn.com.taiji.demo.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模仿SQL的ResultSet by zjy 2017/05/09
 */
public class ResultSet {

	private Map<String, Integer> headMap;

	private List<String[]> data;

	private int cursor = 0;

	public ResultSet(Map<String, Integer> headMap, List<String[]> data) {
		if (null != headMap) {
			this.headMap = headMap;
		} else {
			String[] head = data.get(0);
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (int i = 0; i < head.length; i++) {
				map.put(head[i], i + 1);
			}
			this.headMap = map;
		}
		this.data = data;

	}

	public void setHeadMap(Map<String, Integer> headMap) {
		this.headMap = headMap;
	}

	public void setData(List<String[]> data) {
		this.data = data;
	}

	public boolean next() {
		if (null != data && data.size() > 0 && cursor < data.size() - 1) {
			cursor++;
			return true;
		}
		return false;
	}

	public String getString(int columnIndex) {
		String result = data.get(cursor)[columnIndex - 1];
		return result;
	}

	public byte getByte(int columnIndex) {
		String result = data.get(cursor)[columnIndex - 1];
		return Byte.parseByte(result);
	}

	public short getShort(int columnIndex) {
		String result = data.get(cursor)[columnIndex - 1];
		return Short.parseShort(result);
	}

	public int getInt(int columnIndex) {
		String result = data.get(cursor)[columnIndex - 1];
		return Integer.parseInt(result);
	}

	public long getLong(int columnIndex) {
		String result = data.get(cursor)[columnIndex - 1];
		return Long.parseLong(result);
	}

	public float getFloat(int columnIndex) {
		String result = data.get(cursor)[columnIndex - 1];
		return Float.parseFloat(result);
	}

	public double getDouble(int columnIndex) {
		String result = data.get(cursor)[columnIndex - 1];
		return Double.parseDouble(result);
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale) {
		String result = data.get(cursor)[columnIndex - 1];
		return new BigDecimal(result);
	}

	public Date getDate(int columnIndex) {
		String result = data.get(cursor)[columnIndex - 1];
		return new Date(result);
	}

	public String getString(String columnLabel) {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		return result;
	}

	public byte getByte(String columnLabel) {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		return Byte.parseByte(result);
	}

	public short getShort(String columnLabel) {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		return Short.parseShort(result);
	}

	public int getInt(String columnLabel) {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		return Integer.parseInt(result);
	}

	public long getLong(String columnLabel) {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		return Long.parseLong(result);
	}

	public float getFloat(String columnLabel) {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		return Float.parseFloat(result);
	}

	public double getDouble(String columnLabel) {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		return Double.parseDouble(result);
	}

	public BigDecimal getBigDecimal(String columnLabel, int scale) {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		return new BigDecimal(result);
	}

	public Date getDate(String columnLabel) {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		return new Date(result);
	}

	public Date getDate(int columnIndex, String dateFormat) throws ParseException {
		String result = data.get(cursor)[columnIndex - 1];
		DateFormat df = new SimpleDateFormat(dateFormat);
		return df.parse(result);
	}

	public Date getDate(String columnLabel, String dateFormat) throws ParseException {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		DateFormat df = new SimpleDateFormat(dateFormat);
		return df.parse(result);
	}

	public BigDecimal getBigDecimal(int columnIndex) {
		String result = data.get(cursor)[columnIndex - 1];
		return new BigDecimal(result);
	}

	public BigDecimal getBigDecimal(String columnLabel) {
		String result = data.get(cursor)[headMap.get(columnLabel) - 1];
		return new BigDecimal(result);
	}

	public boolean isFirst() {
		return (cursor == 0);
	}

	public boolean isLast() {
		return (cursor > 0 && cursor == data.size() - 1);
	}

	public void close() {
		this.data = null;
		System.gc();
	}
}
