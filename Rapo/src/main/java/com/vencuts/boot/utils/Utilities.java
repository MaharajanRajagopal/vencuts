package com.vencuts.boot.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.vencuts.boot.dto.Record;
import com.vencuts.boot.dto.Records;

public class Utilities {
	
	
	
	public static <T> Object parseXml(StringReader fileContent, Class<T> clazz) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(fileContent);
	}
	
	public static Records parseCSV(StringReader inputData) throws IOException {

		Records records = new Records();
		System.out.println("inside parse");
		
		try (CSVParser csvParser = new CSVParser(inputData,
				CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<Record> record = new ArrayList<>();
			csvParser.forEach(csvRecord -> record.add(
					new Record(csvRecord.get("Reference"),csvRecord.get("Account Number"),csvRecord.get("Description"),csvRecord.get("Start Balance"),csvRecord.get("Mutation"), csvRecord.get("End Balance")
							)));
			records.setRecord(record);
		}
		return records;
	}
	
	public static void writeExcel(Map<String, List<Record>> result) {
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
			if (key.equals(Constants.VALID_REC)) {
				sheet = workbook.createSheet(Constants.VALID_REC);
				sheet.setColumnWidth(2, 8000);
				Row header = sheet.createRow(rownum++);
				Cell cell1 = header.createCell(1);
				cell1.setCellValue("Reference");
				cell1.setCellStyle(style);
				Cell cell2 = header.createCell(2);
				cell2.setCellValue("Description");
				cell2.setCellStyle(style);
			} else if (key.equals(Constants.INVALID_COMP)) {
				sheet = workbook.createSheet(Constants.INVALID_COMP);
				sheet.setColumnWidth(2, 8000);
				Row header = sheet.createRow(rownum++);
				Cell cell1 = header.createCell(1);
				cell1.setCellValue("Reference");
				cell1.setCellStyle(style2);
				Cell cell2 = header.createCell(2);
				cell2.setCellValue("Description");
				cell2.setCellStyle(style2);
			} else if (key.equals(Constants.DUPLICATE_REF)) {
				sheet = workbook.createSheet(Constants.DUPLICATE_REF);
				sheet.setColumnWidth(2, 8000);
				Row header = sheet.createRow(rownum++);
				Cell cell1 = header.createCell(1);
				cell1.setCellValue("Reference");
				cell1.setCellStyle(style3);
				Cell cell2 = header.createCell(2);
				cell2.setCellValue("Description");
				cell2.setCellStyle(style3);
			}

			List<Record> record = result.get(key);
			int cellnum = 0;

			for (Record rec : record) {
				Row row = sheet.createRow(rownum++);
				row.createCell(1).setCellValue(rec.getReference());
				row.createCell(2).setCellValue(rec.getDescription());
			}
			rownum = 0;
		}
		try {
			// Write the workbook in file system
			String home = System.getProperty("user.home");
			FileOutputStream out = new FileOutputStream(new File(home + "/Downloads/" + "records.xlsx"));
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
