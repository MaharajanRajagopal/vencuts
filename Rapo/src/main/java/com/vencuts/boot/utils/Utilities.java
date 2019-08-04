package com.vencuts.boot.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

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
		for(Record re:records.getRecord()) {
			System.out.println("recorddd"+re.getAccountNumber());
		}
		return records;
	}
}
