package org.robertux.financeAnalytics.FinanceAnalyticsServer.security;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robertux.financeAnalytics.server.security.CryptoService;

public class CryptoServiceTest {
	private CryptoService cryptoService;
	private String input;

	@Before
	public void setUp() throws Exception {
		this.cryptoService = new CryptoService();
		this.input = "TestInput";
	}

	@After
	public void tearDown() throws Exception {
		this.cryptoService = null;
	}

	@Test
	public void test() {
		String encrypted = this.cryptoService.encrypt(input);
		assertTrue("Valor encriptado debe ser válido", this.cryptoService.isValid(input, encrypted));
	}

}
