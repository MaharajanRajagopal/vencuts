package com.vencuts.boot.Rapo;

import org.junit.Test;
import org.mockito.InjectMocks;

import com.vencuts.boot.rapo.RapoApplication;

public class RepoApplicationTests {

	@InjectMocks
	RapoApplication rapoApplication;

	

	@Test
	public void main() throws Exception {
		rapoApplication.main(new String[] {});
	}
}

