package cn.com.taiji.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ExcelExportUtil {
	public static void toDownload2003(String title, Map<String, String> headMap, List list, String originalName,
			HttpServletResponse response) throws IOException {
		String s = JSON.toJSONString(list, true);
		JSONArray jsonArray = JSON.parseArray(s);
		toDownload2003(title, headMap, jsonArray, originalName, response);
	}

	public static void toDownload2007(String title, Map<String, String> headMap, List list, String originalName,
			HttpServletResponse response) throws IOException {
		String s = JSON.toJSONString(list, true);
		JSONArray jsonArray = JSON.parseArray(s);
		toDownload2007(title, headMap, jsonArray, originalName, response);
	}

	public static void toSave(String title, Map<String, String> headMap, List list, String datePattern,
			String saveFilePath, String excelType) throws IOException {
		String s = JSON.toJSONString(list, true);
		JSONArray jsonArray = JSON.parseArray(s);
		toSave(title, headMap, jsonArray, datePattern, saveFilePath, excelType);
	}

	public static void toDownload2003(String title, Map<String, String> headMap, JSONArray jsonArray,
			String originalName, HttpServletResponse response) throws IOException {
		WriteExcel.downloadExcelFile(title, headMap, jsonArray, originalName, "2003", response);
	}

	public static void toDownload2007(String title, Map<String, String> headMap, JSONArray jsonArray,
			String originalName, HttpServletResponse response) throws IOException {
		WriteExcel.downloadExcelFile(title, headMap, jsonArray, originalName, "2007", response);
	}

	public static void toSave(String title, Map<String, String> headMap, JSONArray jsonArray, String datePattern,
			String saveFilePath, String excelType) throws IOException {
		File file = new File(saveFilePath);
		OutputStream out = new FileOutputStream(file);
		if ("2003".equals(excelType)) {
			WriteExcel.exportExcel2003(title, headMap, jsonArray, datePattern, out);
		} else {
			WriteExcel.exportExcel2007(title, headMap, jsonArray, datePattern, out);// 默认导出xlsx格式
		}
	}

	public static void toDownloadModel2003(String[] headers, String modelName, HttpServletResponse response)
			throws IOException {
		WriteExcel.dowloadExcelModel(headers, modelName, "2003", response);
	}

	public static void toDownloadModel2007(String[] headers, String modelName, HttpServletResponse response)
			throws IOException {
		WriteExcel.dowloadExcelModel(headers, modelName, "2007", response);
	}
}