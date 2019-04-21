package org.robertux.financeAnalytics.FinanceAnalyticsServer.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robertux.financeAnalytics.server.security.JWTService;

import io.jsonwebtoken.Claims;

public class JWTServiceTest {
	private JWTService jwtService;
	private long userId;

	@Before
	public void setUp() throws Exception {
		this.jwtService = new JWTService();
		this.jwtService.setIssuer("robertux");
		this.jwtService.setKeyValue("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2JlcnR1eCIsImV4cCI6MTU1NTcwMDA4NCwiaXNzIjoib3JnLnJvYmVydHV4In0.TyINltn0QyT6-L9np7_uy-FmkmuSDz9CVLPe21PlyidlRPiBq0QMqIgAdEjJdV3P3t5QbMy5Fb8sdRiS_dFa1AOiJyb2JlcnR1eCIsImV4cCI6MTU1NTcwMDA4NCwiaXNzIjoib3JnLnJvYmVydHV4InbGA4t5z");
		this.jwtService.setExpiry(600);
		this.userId = 1;
	}

	@After
	public void tearDown() throws Exception {
		this.jwtService = null;
	}

	@Test
	public void test() {
		String token = this.jwtService.generateToken(this.userId);
		Claims result = this.jwtService.verifyToken(token);
		assertNotNull("Resultado no debe ser nulo", result);
		assertEquals("Resultado debe coincidir con el Id del usuario", String.valueOf(this.userId), result.getSubject());
	}

}
