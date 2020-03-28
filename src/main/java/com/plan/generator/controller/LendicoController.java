package com.plan.generator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.plan.generator.beans.GeneratedPlan;
import com.plan.generator.beans.Planpayload;
import com.plan.generator.service.PlanService;

@RestController
@RequestMapping("/plan")
public class LendicoController {
	
	@Autowired
	PlanService planService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public GeneratedPlan generatePlan(@RequestBody @Valid Planpayload payload) {
		//Method to get plans from service class.
		GeneratedPlan plans = new GeneratedPlan();
		plans.setData(planService.generate(payload));
		return plans;
	}

}
