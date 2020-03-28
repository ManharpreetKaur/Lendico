package com.plan.generator.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.plan.generator.beans.Plan;
import com.plan.generator.beans.Planpayload;

@Service
public class PlanService {

	public static final int month = 30;
	public static final int year = 360;

	public List<Plan> generate(Planpayload payload) {
		List<Plan> generatedPlan = new ArrayList<>();
		double initialOutstandingPrinciple = Double.parseDouble(payload.getLoanAmount());
		double rate = Double.parseDouble(payload.getNominalRate());
		int duration = payload.getDuration();

		double periodicRate = rate / (12 * 100);
		double annuity = (periodicRate * initialOutstandingPrinciple)
				/ (1 - (1 / (Math.pow(1 + periodicRate, duration))));

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		LocalDate date = LocalDate.parse(payload.getStartDate(), inputFormatter);
		
		System.out.println(date);
		
		generatedPlan = calculatePlansData(initialOutstandingPrinciple, rate, annuity, date);

		return generatedPlan;
	}

	private List<Plan> calculatePlansData(double initialOutstandingPrinciple, double rate, double annuity,
			LocalDate date) {
		List<Plan> generatedPlan = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("0.00");
		
		while (initialOutstandingPrinciple > 0) {
			double borrowerPaymentAmount;
			Plan plan = new Plan();
			plan.setDate(date.toString());
			plan.setInitialOutstandingPrincipal(String.valueOf(df.format(initialOutstandingPrinciple)));

			double interest = (rate * month * initialOutstandingPrinciple) / (year * 100);
			double principal = annuity - interest;
			if (principal > initialOutstandingPrinciple) {
				borrowerPaymentAmount = initialOutstandingPrinciple + interest;
				principal = initialOutstandingPrinciple;
			} else {
				borrowerPaymentAmount = principal + interest;
			}

			double remainingOutstandingPrincipal = (initialOutstandingPrinciple - borrowerPaymentAmount) < 0 ? 0
					: initialOutstandingPrinciple - borrowerPaymentAmount;

			plan.setBorrowerPaymentAmount(String.valueOf(df.format(borrowerPaymentAmount)));
			plan.setInterest(String.valueOf(df.format(interest)));
			plan.setPrincipal(String.valueOf(df.format(principal)));
			plan.setRemainingOutstandingPrincipal(String.valueOf(df.format(remainingOutstandingPrincipal)));
			generatedPlan.add(plan);

			initialOutstandingPrinciple = remainingOutstandingPrincipal;
			date = date.plusMonths(1);
		}
		return generatedPlan;
	}

}
