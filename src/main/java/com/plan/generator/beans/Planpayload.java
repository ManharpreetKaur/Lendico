package com.plan.generator.beans;

import javax.validation.constraints.NotNull;

public class Planpayload {

	@NotNull
	String loanAmount;

	@NotNull
	String nominalRate;

	@NotNull
	int duration;

	@NotNull
	String startDate;

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getNominalRate() {
		return nominalRate;
	}

	public void setNominalRate(String nominalRate) {
		this.nominalRate = nominalRate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	
}
