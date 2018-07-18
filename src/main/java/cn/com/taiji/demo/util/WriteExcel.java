package cn.com.taiji.demo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WriteExcel
{
    private static final Logger logger = LoggerFactory.getLogger(WriteExcel.class);
    public static final String NO_DEFINE = "no_define";// 未定义的字段
    public static final String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";// 默认日期格式
    public static final int DEFAULT_COLOUMN_WIDTH = 17;

    /**
     * 导出Excel 2003(.xls)格式 ，少量数据
     * 
     * @param title
     *            标题行
     * @param headMap
     *            属性-列名
     * @param jsonArray
     *            数据集
     * @param datePattern
     *            日期格式，null则用默认日期格式
     * @param colWidth
     *            列宽 默认 至少17个字节
     * @param out
     *            输出流
     */
    public static void exportExcel2003(String title, Map<String, String> headMap, JSONArray jsonArray,
            String datePattern, OutputStream out)
    {

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 表头样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight((short) 700);
        titleStyle.setFont(titleFont);
        // 列头样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        // 单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont cellFont = workbook.createFont();
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
        HSSFSheet sheet = workbook.createSheet();
        // 产生表格标题行
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];
        int ii = 0;
        for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();)
        {
            String fieldName = iter.next();
            properties[ii] = fieldName;
            headers[ii] = headMap.get(fieldName);
            ii++;
        }
        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        for (Object obj : jsonArray)
        {
            if (rowIndex == 65535 || rowIndex == 0)
            {
                if (rowIndex != 0)
                {
                    // 自动调整宽度
                    for (int i = 0; i < headers.length; i++)
                    {
                        sheet.autoSizeColumn(i);
                    }
                    // 新的一页--如果数据超过了，则在第二页显示
                    sheet = workbook.createSheet();
                }
                HSSFRow titleRow = sheet.createRow(0);// 表头 rowIndex=0
                titleRow.createCell(0).setCellValue(title);
                titleRow.getCell(0).setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));

                HSSFRow headerRow = sheet.createRow(1); // 列头 rowIndex =1
                for (int i = 0; i < headers.length; i++)
                {
                    headerRow.createCell(i).setCellValue(headers[i]);
                    headerRow.getCell(i).setCellStyle(headerStyle);

                }
                rowIndex = 2;// 数据内容从 rowIndex=2开始
            }
            JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
            HSSFRow dataRow = sheet.createRow(rowIndex);
            for (int i = 0; i < properties.length; i++)
            {
                HSSFCell newCell = dataRow.createCell(i);

                Object o = jo.get(properties[i]);
                String cellValue = "";
                if (o == null)
                {
                    cellValue = "";
                }
                else if (o instanceof Date)
                {
                    String tempDatePattern = StringUtils.isEmpty(datePattern) ? DEFAULT_DATE_PATTERN : datePattern;
                    cellValue = new SimpleDateFormat(tempDatePattern).format(o);
                }
                else
                {
                    cellValue = o.toString();
                }

                newCell.setCellValue(cellValue);
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
        // 自动调整宽度
        for (int i = 0; i < headers.length; i++)
        {
            sheet.autoSizeColumn(i);
        }
        try
        {
            workbook.write(out);
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 导出Excel 2007 OOXML (.xlsx)格式
     * 
     * @param title
     *            标题行
     * @param headMap
     *            属性-列头
     * @param jsonArray
     *            数据集
     * @param datePattern
     *            日期格式，传null值则默认 年月日
     * @param colWidth
     *            列宽 默认 至少17个字节
     * @param out
     *            输出流
     */
    public static void exportExcel2007(String title, Map<String, String> headMap, JSONArray jsonArray,
            String datePattern, OutputStream out)
    {
        if (datePattern == null)
            datePattern = DEFAULT_DATE_PATTERN;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);// 缓存
        workbook.setCompressTempFiles(true);
        // 表头样式
        CellStyle titleStyle = null;
        if (StringUtils.isNotBlank(title))
        {
            titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Font titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 20);
            titleFont.setBoldweight((short) 700);
            titleStyle.setFont(titleFont);
        }
        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        // 单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
        Sheet sheet = workbook.createSheet();
        // 产生表格标题行
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];
        int ii = 0;
        for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();)
        {
            String fieldName = iter.next();

            properties[ii] = fieldName;
            headers[ii] = headMap.get(fieldName);
            ii++;
        }
        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        for (Object obj : jsonArray)
        {
            if (rowIndex == 65535 || rowIndex == 0)
            {
                if (rowIndex != 0)
                {
                    // 自动调整宽度
                    for (int i = 0; i < headers.length; i++)
                    {
                        sheet.autoSizeColumn(i);
                    }
                    // 新的一页--如果数据超过了，则在第二页显示
                    sheet = workbook.createSheet();
                }
                Row headerRow = null;
                if (StringUtils.isNotBlank(title))
                {
                    Row titleRow = sheet.createRow(0);// 表头 rowIndex=0
                    titleRow.createCell(0).setCellValue(title);
                    titleRow.getCell(0).setCellStyle(titleStyle);
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));
                    headerRow = sheet.createRow(1); // 列头 rowIndex =1
                }
                else
                {
                    headerRow = sheet.createRow(0); // 列头 rowIndex =1
                }
                for (int i = 0; i < headers.length; i++)
                {
                    headerRow.createCell(i).setCellValue(headers[i]);
                    headerRow.getCell(i).setCellStyle(headerStyle);
                }
                rowIndex = 2;// 数据内容从 rowIndex=2开始
            }
            JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
            Row dataRow = sheet.createRow(rowIndex);
            for (int i = 0; i < properties.length; i++)
            {
                Cell newCell = dataRow.createCell(i);
                Object o = jo.get(properties[i]);
                String cellValue = "";
                if (o == null)
                    cellValue = "";
                else if (o instanceof Date)
                    cellValue = new SimpleDateFormat(datePattern).format(o);
                else if (o instanceof Float || o instanceof Double)
                    cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                else
                    cellValue = o.toString();
                newCell.setCellValue(cellValue);
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
        // 自动调整宽度
        for (int i = 0; i < headers.length; i++)
        {
            sheet.autoSizeColumn(i);
        }
        try
        {
            workbook.write(out);
            out.close();
            workbook = null;
        }
        catch (IOException e)
        {
            logger.error("exportExcel2007 exception ", e);
        }
    }

    /**
     * 导出Excel 2007 OOXML (.xlsx)格式
     * 
     * @param title
     *            标题行
     * @param headMap
     *            属性-列头
     * @param jsonArray
     *            数据集
     * @param datePattern
     *            日期格式，传null值则默认 年月日
     * @param colWidth
     *            列宽 默认 至少17个字节
     * @param out
     *            输出流
     */
    public static void exportExcel2007NotTitle(String title, Map<String, String> headMap, JSONArray jsonArray,
            String datePattern, OutputStream out)
    {
        if (datePattern == null)
            datePattern = DEFAULT_DATE_PATTERN;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);// 缓存
        workbook.setCompressTempFiles(true);
        // 表头样式
        CellStyle titleStyle = null;
        if (StringUtils.isNotBlank(title))
        {
            titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Font titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 20);
            titleFont.setBoldweight((short) 700);
            titleStyle.setFont(titleFont);
        }
        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        // 单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
        Sheet sheet = workbook.createSheet();
        // 产生表格标题行
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];
        int ii = 0;
        for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();)
        {
            String fieldName = iter.next();

            properties[ii] = fieldName;
            headers[ii] = headMap.get(fieldName);
            ii++;
        }
        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        if (jsonArray != null)
        {
            for (Object obj : jsonArray)
            {
                if (rowIndex == 65535 || rowIndex == 0)
                {
                    if (rowIndex != 0)
                    {
                        // 自动调整宽度
                        for (int i = 0; i < headers.length; i++)
                        {
                            sheet.autoSizeColumn(i);
                        }
                        // 新的一页--如果数据超过了，则在第二页显示
                        sheet = workbook.createSheet();
                    }
                    Row headerRow = null;
                    headerRow = sheet.createRow(0); // 列头 rowIndex =1
                    for (int i = 0; i < headers.length; i++)
                    {
                        headerRow.createCell(i).setCellValue(headers[i]);
                        headerRow.getCell(i).setCellStyle(headerStyle);
                    }
                    rowIndex = 1;// 数据内容从 rowIndex=2开始
                }
                JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
                Row dataRow = sheet.createRow(rowIndex);
                for (int i = 0; i < properties.length; i++)
                {
                    Cell newCell = dataRow.createCell(i);
                    Object o = jo.get(properties[i]);
                    String cellValue = "";
                    if (o == null)
                        cellValue = "";
                    else if (o instanceof Date)
                        cellValue = new SimpleDateFormat(datePattern).format(o);
                    else if (o instanceof Float || o instanceof Double)
                        cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                    else
                        cellValue = o.toString();
                    newCell.setCellValue(cellValue);
                    newCell.setCellStyle(cellStyle);
                }
                rowIndex++;
            }
        }
        // 自动调整宽度
        for (int i = 0; i < headers.length; i++)
        {
            sheet.autoSizeColumn(i);
        }
        try
        {
            workbook.write(out);
            out.close();
            workbook = null;
        }
        catch (IOException e)
        {
            logger.error("exportExcel2007 exception ", e);
        }
    }

    /**
     * Web 导出excel
     * 
     * @throws IOException
     * 
     */
    public static void downloadExcelFile(String title, Map<String, String> headMap, JSONArray jsonArray,
            String originalName, String excelType, HttpServletResponse response)
    {
        OutputStream out = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;

        if ("2003".equals(excelType))
        {
            originalName += ".xls";
        }
        else
        {
            originalName += ".xlsx";
        }
        try
        {
            originalName = URLEncoder.encode(originalName, "UTF-8");// 解决中文乱码问题
            File tempFile = File.createTempFile("excel", ".tmp");
            out = new FileOutputStream(tempFile);
            if ("2003".equals(excelType))
            {
                exportExcel2003(title, headMap, jsonArray, null, out);
            }
            else
            {
                exportExcel2007NotTitle(title, headMap, jsonArray, null, out);// 默认导出xlsx格式
            }
            fis = new FileInputStream(tempFile);
            bis = new BufferedInputStream(fis);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Length", String.valueOf(tempFile.length()));// 一定要设置长度，否则会出现文件错误
            response.setHeader("Content-Disposition", "attachment; filename=" + originalName);// 不是中文才可这样，否则得用上面那条
            os = response.getOutputStream();
            bos = new BufferedOutputStream(os);
            byte[] cbuf = new byte[4096];

            while (bis.read(cbuf) != -1)
            {
                bos.write(cbuf);
            }
            bos.flush();
            tempFile.delete();
        }
        catch (UnsupportedEncodingException ue)
        {
            logger.error("unsipported encoding exception, " + ue);
        }
        catch (IOException ioe)
        {
            logger.error("io exception, " + ioe);
        }
        finally
        {
            try
            {
                if (bos != null)
                {
                    bos.close();
                }
                if (os != null)
                {
                    os.close();
                }
                if (bis != null)
                {
                    bis.close();
                }
                if (fis != null)
                {
                    fis.close();
                }
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException ioe)
            {
                logger.error("io exception, " + ioe);
            }
        }

    }

    /**
     * 导出excel2003模板
     * 
     */
    public static void exportExcelModel2003(String[] headers, OutputStream out)
    {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 列头样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        // 产生表格标题行
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++)
        {
            headerRow.createCell(i).setCellValue(headers[i]);
            headerRow.getCell(i).setCellStyle(headerStyle);
            sheet.autoSizeColumn(i);
        }
        try
        {
            workbook.write(out);
            out.close();
            workbook = null;
        }
        catch (IOException e)
        {
            logger.error("export excel 2003 exception", e);
        }

    }

    /**
     * 导出excel2007模板
     * 
     */
    public static void exportExcelModel2007(String[] headers, OutputStream out)
    {
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);// 缓存
        workbook.setCompressTempFiles(true);
        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        // 生成一个表格
        Sheet sheet = workbook.createSheet();
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++)
        {
            headerRow.createCell(i).setCellValue(headers[i]);
            headerRow.getCell(i).setCellStyle(headerStyle);
            sheet.autoSizeColumn(i);
        }
        try
        {
            workbook.write(out);
            out.close();
            workbook = null;
        }
        catch (IOException e)
        {
            logger.error("export excel 2007 exception", e);
        }
    }

    /**
     * Web 导出excel模板
     * 
     * @throws IOException
     * 
     * @throws Exception
     * 
     * 
     */
    public static void dowloadExcelModel(String[] headers, String modelName, String excelType,
            HttpServletResponse response) throws IOException
    {
        if ("2003".equals(excelType))
        {
            modelName += ".xls";
        }
        else
        {
            modelName += ".xlsx";
        }
        modelName = URLEncoder.encode(modelName, "UTF-8");// 解决中文乱码问题
        File tempFile = File.createTempFile("excelModel", ".tmp");
        OutputStream out = new FileOutputStream(tempFile);
        if ("2003".equals(excelType))
        {
            exportExcelModel2003(headers, out);
        }
        else
        {
            exportExcelModel2007(headers, out);
        }
        FileInputStream fis = new FileInputStream(tempFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Length", String.valueOf(tempFile.length()));
        response.setHeader("Content-Disposition", "attachment; filename=" + modelName);
        OutputStream os = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        byte[] cbuf = new byte[4096];

        while (bis.read(cbuf) != -1)
        {
            bos.write(cbuf);
        }
        bos.flush();
        try
        {
            bos.close();
            bis.close();
        }
        catch (IOException e)
        {
            logger.error("download excel exception", e);
        }
        tempFile.delete();
    }
}