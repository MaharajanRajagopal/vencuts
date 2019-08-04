package com.vencuts.boot.reboservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Service;
import com.vencuts.boot.dto.Record;
import com.vencuts.boot.dto.Records;

/**
 * @author Maharajan Rajagopal
 *
 */
@Service
public class ReboServiceImpl {
	
	//public Records records;

	/*
	 * public Records reboValidator(Records records){
	 * System.out.println("hellainside"); List<Record> record = records.getRecord();
	 * //record.forEach(r-> System.out.println(r.getReference()));
	 * System.out.println("hellainside"); List<Record> distinctElements =
	 * record.stream() .filter( distinctByKey(r -> r.getReference()) ) .collect(
	 * Collectors.toList() ); records.setRecord(distinctElements); return records; }
	 */
	
	/*
	 * Public Records reboXMLValidator() {
	 * 
	 * }
	 */
	
	/**
	 * @param records with list of record
	 * @return map with valid and invalid Record
	 * @throws JAXBException
	 */
	public Map<String, List<Record>> validateRecords(Records records) {
		Map<String, List<Record>> result = new HashMap<>();
		List<Record> validRecords = new ArrayList<>();
		List<Record> inValidComputation = new ArrayList<>();
		List<Record> duplicateReference = new ArrayList<>();
		records.getRecord().forEach(record ->
		{
			double endBalenceActual = Double.parseDouble(record.getStartBalance()) + Double.parseDouble(record.getMutation());
			endBalenceActual = BigDecimal.valueOf(endBalenceActual).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			double currentendBalence = Double.parseDouble(record.getEndBalance());
			boolean isDuplicate = validRecords.stream()
					.anyMatch(validRecord -> validRecord.getReference().equals(record.getReference()));
			if (endBalenceActual == currentendBalence && !isDuplicate) {
				validRecords.add(record);
			}
			else if (!isDuplicate && endBalenceActual != currentendBalence){
				inValidComputation.add(record);
			}else {
				duplicateReference.add(record);
			}
		});
		result.put("duplicateReference", duplicateReference);
		result.put("inValidComputation", inValidComputation);
		result.put("validRecords", validRecords);
		return result;
	}

	
	/*
	 * public static <T> Predicate<T> distinctByKey(Function<? super T, Object>
	 * keyExtractor) { Map<Object, Boolean> map = new ConcurrentHashMap<>(); // t ->
	 * map.computeIfPresent(arg0, arg1) return t ->
	 * map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null; }
	 */

}
