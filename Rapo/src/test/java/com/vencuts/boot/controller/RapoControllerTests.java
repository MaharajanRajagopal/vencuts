package com.vencuts.boot.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.vencuts.boot.controller.RaboController;
import com.vencuts.boot.reboservice.ReboServiceImpl;

@RunWith(SpringRunner.class)
public class RapoControllerTests {
	
	@InjectMocks
	RaboController rebocontroller;
	
	@Mock
	MultipartFile file;
	
	@Mock
	ReboServiceImpl reboService;
	
	@Mock
	HttpServletResponse httpResponse;
	

	@Test
	public void repoApplication_emptyfile_Test() {
		when(file.isEmpty()).thenReturn(true);
		assertNotNull(rebocontroller.uploadFile(file,httpResponse));
	}
	
	@Test
	public void repoApplication_xmlfile_Test() throws IOException {
		when(file.isEmpty()).thenReturn(false);
		when(file.getBytes()).thenReturn(getxmlRecords().getBytes());
		when(file.getOriginalFilename()).thenReturn("records.xml");
		assertNotNull(rebocontroller.uploadFile(file, httpResponse));
	}
	
	@Test
	public void repoApplication_csvfile_Test() throws IOException {
		when(file.isEmpty()).thenReturn(false);
		when(file.getBytes()).thenReturn(getcsvRecords().getBytes());
		when(file.getOriginalFilename()).thenReturn("records.csv");
		assertNotNull(rebocontroller.uploadFile(file, httpResponse));
	}
	
	static String getxmlRecords() {
		String s ="<records><record reference=\"187997\"><accountNumber>NL91RABO0315273637</accountNumber><description>Clothes for Rik King</description> <startBalance>57.6</startBalance> <mutation>-32.98</mutation><endBalance>24.62</endBalance></record><record reference=\"154270\"><accountNumber>NL56RABO0149876948</accountNumber><description>Candy for Peter de Vries</description><startBalance>5429</startBalance><mutation>-939</mutation><endBalance>6368</endBalance></record>";
		return s;
	}
	
	
	static String getcsvRecords() {
		String s ="Reference,AccountNumber,Description,Start Balance,Mutation,End Balance\n187997,NL91RABO0315273637,Clothes from Rik King,57.6,-32.98,24.62";
		return s;
	}
}
