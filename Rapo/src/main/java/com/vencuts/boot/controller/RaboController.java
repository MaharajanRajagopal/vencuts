package com.vencuts.boot.controller;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.vencuts.boot.dto.Record;
import com.vencuts.boot.dto.Records;
import com.vencuts.boot.reboservice.ReboServiceImpl;
import com.vencuts.boot.utils.Utilities;


/**
 * @author Maharajan Rajagopal
 *
 */
@RestController
public class RaboController {

	Logger logger = LoggerFactory.getLogger(RaboController.class);
	
	@Autowired
	public ReboServiceImpl reboService;

	public ReboServiceImpl getReboService() {
		return reboService;
	}

	public void setReboService(ReboServiceImpl reboService) {
		this.reboService = reboService;
	}
	
	@RequestMapping("/")
	public String index() {
		return "Welcome to RABO BANK Customer Statement Validator -- Use Postman ";
	}
	/**
	 * @param Multipart File Input CSV or XML file
	 * produces xml 
	 * @return map of valid and invalid Records
	 */
	
	@PostMapping(path="/rabobank/uploadfile" ,produces = {MediaType.APPLICATION_XML_VALUE})
    public Map<String, List<Record>> uploadFile(@RequestParam("file") MultipartFile file) {
		
		Map<String, List<Record>> result = new HashMap<>();
		if (file.isEmpty()) {
			return result;
		}
		try {
			StringReader fileContent = new StringReader(new String(file.getBytes()));
			Records records = null;

			if (file.getOriginalFilename().endsWith(".xml")) {
				/*
				 * DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
				 * DocumentBuilder docBuilder = dbfac.newDocumentBuilder(); Document doc =
				 * docBuilder.parse((InputStream) file); TransformerFactory factory =
				 * TransformerFactory.newInstance(); Source xslt = new StreamSource(new
				 * File("recordValidator.xsl")); Transformer transformer =
				 * factory.newTransformer(xslt); Source xml = new StreamSource(new
				 * String(file.getBytes())); Document transformedDoc
				 * =transformer.transform(xml);
				 */
				records = (Records) Utilities.parseXml(fileContent, Records.class);
				System.out.println("xml file upload");
			} else {
				System.out.println("other file upload");
				records = Utilities.parseCSV(fileContent);
				
			}

			  result = reboService.validateRecords(records);
			  Utilities.writeExcel(result);
		} catch (Exception e) {

		}
		return result;
    }
	
	@RequestMapping("/dummy/{id}")
	public String getRecords(@PathVariable String id) {
		
		
		return "Success "+id;
	}
}
