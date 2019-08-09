package com.vencuts.boot.utils;

import static com.vencuts.boot.utils.Constants.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xmlconfig.NamespaceList.Member2.Item;

import com.vencuts.boot.dto.Record;
import com.vencuts.boot.dto.Records;

public class Utilities {

	public static <T> Object parseXml(StringReader fileContent, Class<T> clazz) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(fileContent);
	}

	public static Records parseCSVJ8(InputStream recordStream) throws IOException {
		Records records = new Records();
		BufferedReader br = new BufferedReader(new InputStreamReader(recordStream));
		List<Record> list = br.lines().skip(1).map(mapToRecord).collect(Collectors.toList());
		records.setRecord(list);
		return records;
	}
	
	public static Function<String, Record> mapToRecord = (line) -> {
		  String[] p = line.split(COMMA);// a CSV has comma separated lines
		  Record record = new Record();
		  record.setReference(p[0]);
		  record.setAccountNumber(p[1]);
		  record.setDescription(p[2]);
		  record.setStartBalance(p[3]);
		  record.setMutation(p[4]);
		  record.setEndBalance(p[5]);
		  return record;
		};
	// Detailed EXCEL report with 3 sheet showing valid, invalidcomputation and
	// dupicate Reference. working locally jar call
	public static void writeExcel(Map<String, List<Record>> result) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		CellStyle style = workbook.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		style.setFillPattern(FillPatternType.FINE_DOTS);
		CellStyle style2 = workbook.createCellStyle();
		style2.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
		style2.setFillPattern(FillPatternType.FINE_DOTS);
		CellStyle style3 = workbook.createCellStyle();
		style3.setFillBackgroundColor(IndexedColors.RED1.getIndex());
		style3.setFillPattern(FillPatternType.FINE_DOTS);
		XSSFSheet sheet = null;
		Set<String> keyset = result.keySet();
		int rownum = 0;
		for (String key : keyset) {
			if (key.equals(VALID_REC)) {
				sheet = workbook.createSheet(VALID_REC);
				sheet.setColumnWidth(2, 8000);
				Row header = sheet.createRow(rownum++);
				Cell cell1 = header.createCell(1);
				cell1.setCellValue(REFERENCE);
				cell1.setCellStyle(style);
				Cell cell2 = header.createCell(2);
				cell2.setCellValue(DESCRIPTION);
				cell2.setCellStyle(style);
			} else if (key.equals(Constants.INVALID_COMP)) {
				sheet = workbook.createSheet(Constants.INVALID_COMP);
				sheet.setColumnWidth(2, 8000);
				Row header = sheet.createRow(rownum++);
				Cell cell1 = header.createCell(1);
				cell1.setCellValue(REFERENCE);
				cell1.setCellStyle(style2);
				Cell cell2 = header.createCell(2);
				cell2.setCellValue(DESCRIPTION);
				cell2.setCellStyle(style2);
			} else if (key.equals(Constants.DUPLICATE_REF)) {
				sheet = workbook.createSheet(Constants.DUPLICATE_REF);
				sheet.setColumnWidth(2, 8000);
				Row header = sheet.createRow(rownum++);
				Cell cell1 = header.createCell(1);
				cell1.setCellValue(REFERENCE);
				cell1.setCellStyle(style3);
				Cell cell2 = header.createCell(2);
				cell2.setCellValue(DESCRIPTION);
				cell2.setCellStyle(style3);
			}
			List<Record> record = result.get(key);
			for (Record rec : record) {
				Row row = sheet.createRow(rownum++);
				row.createCell(1).setCellValue(rec.getReference());
				row.createCell(2).setCellValue(rec.getDescription());
			}
			rownum = 0;
		}
		try {
			// Write the workbook in file system String home =
			String home = System.getProperty("user.home");
			FileOutputStream out = new FileOutputStream(
					new File(home + File.separator + "Documents" + File.separator + "records.xlsx"));
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workbook.close();

		}
	}

}
