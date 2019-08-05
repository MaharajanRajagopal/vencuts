package com.vencuts.boot.reboservice;

import static com.vencuts.boot.utils.Constants.DUPLICATE_REF;
import static com.vencuts.boot.utils.Constants.INVALID_COMP;
import static com.vencuts.boot.utils.Constants.VALID_REC;
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
		records.getRecord().forEach(record -> {
			double endBalenceActual = Double.parseDouble(record.getStartBalance())
					+ Double.parseDouble(record.getMutation());
			endBalenceActual = BigDecimal.valueOf(endBalenceActual).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			double currentendBalence = Double.parseDouble(record.getEndBalance());
			boolean isDuplicate = validRecords.stream()
					.anyMatch(validRecord -> validRecord.getReference().equals(record.getReference()));
			if (endBalenceActual == currentendBalence && !isDuplicate) {
				validRecords.add(record);
			} else if (!isDuplicate && endBalenceActual != currentendBalence) {
				inValidComputation.add(record);
			} else {
				duplicateReference.add(record);
			}
		});
		result.put(DUPLICATE_REF, duplicateReference);
		result.put(INVALID_COMP, inValidComputation);
		result.put(VALID_REC, validRecords);
		return result;
	}
}