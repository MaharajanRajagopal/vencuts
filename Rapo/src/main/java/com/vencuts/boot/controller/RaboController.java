package com.vencuts.boot.controller;

import static com.vencuts.boot.utils.Constants.FILE_EMPTY;
import static com.vencuts.boot.utils.Constants.INVALID_FILE_FORMAT;
import static com.vencuts.boot.utils.Constants.IS_CSV;
import static com.vencuts.boot.utils.Constants.IS_XML;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
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
	
	@PostMapping(path="/uploadfile")
    public String uploadFile(@RequestParam("file") MultipartFile file,HttpServletResponse response) {
		Map<String, List<Record>> result = new HashMap<>();
		if (file.isEmpty()) {
			return FILE_EMPTY;
		}
		try {
			StringReader fileContent = new StringReader(new String(file.getBytes()));
			Records records = null;
			String filename = file.getOriginalFilename();
			if (filename.endsWith(IS_XML)) {
				records = (Records) Utilities.parseXml(fileContent, Records.class);
			} else if(filename.endsWith(IS_CSV)){
				InputStream inputStream = new ByteArrayInputStream(file.getBytes());
				records = Utilities.parseCSVJ8(inputStream);
				
			}
			else {
				return INVALID_FILE_FORMAT;
			}
			  result = reboService.validateRecords(records);
			  File f = Utilities.writeExcel(result);
			  response.setContentType("application/vnd.ms-excel");
			  response.setHeader("Content-Disposition", "attachment; filename ="+f.getName());
			  FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream());
		} catch (Exception e) {

		}
		return "";
    }
	@RequestMapping("/dummy/{id}")
	public String getRecords(@PathVariable String id) {

		return "Success " + id;
	}
}
