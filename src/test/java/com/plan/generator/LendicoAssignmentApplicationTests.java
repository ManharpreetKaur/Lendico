package com.plan.generator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.plan.generator.beans.GeneratedPlan;
import com.plan.generator.beans.Planpayload;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LendicoAssignmentApplicationTests {

	private static final String PLAN_URL = "/plan";

	@Autowired
	TestRestTemplate resTemplate;

	@Test
	public void createPlanTest() {

		Planpayload payload = new Planpayload();
		payload.setDuration(24);
		payload.setLoanAmount("5000");
		payload.setNominalRate("5");
		payload.setStartDate("2018-01-01T00:00:01Z");

		ResponseEntity<GeneratedPlan> result = resTemplate.postForEntity(PLAN_URL, payload, GeneratedPlan.class);

		assertNotNull(result);
		assertNotNull(result.hasBody());
		Assert.assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
	}

	@Test
	public void createPlanValidationTest() {

		Planpayload payload = new Planpayload();
		payload.setDuration(24);
		payload.setNominalRate("5");
		payload.setStartDate("2018-01-01T00:00:01Z");

		ResponseEntity<GeneratedPlan> result = resTemplate.postForEntity(PLAN_URL, payload, GeneratedPlan.class);

		Assert.assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}
}
