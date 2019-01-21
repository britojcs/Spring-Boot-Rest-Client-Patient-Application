package com.javaprojects.springboot.restclientpatient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Medication {

	private int id;
	private String medicationName;
	private String medicationStrength;
	private String medicationDosage;
	private Patient patient;
	
	
	
	
	//Constructors
	public Medication() {
		
	}
	
	/**
	 * @param medicationName
	 * @param medicationStrength
	 * @param medicationDosage
	 */
	public Medication(String medicationName, String medicationStrength, String medicationDosage ) {
		this.medicationName = medicationName;
		this.medicationStrength = medicationStrength;
		this.medicationDosage = medicationDosage;
	}


	public int getId() {
		return this.id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMedicationName() {
		return medicationName;
	}


	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}


	public String getMedicationStrength() {
		return medicationStrength;
	}


	public void setMedicationStrength(String medicationStrength) {
		this.medicationStrength = medicationStrength;
	}


	public String getMedicationDosage() {
		return medicationDosage;
	}


	public void setMedicationDosage(String medicationDosage) {
		this.medicationDosage = medicationDosage;
	}

	@Override
	public String toString() {
		return "Medication [id=" + id + ", medicationName=" + medicationName + ", medicationStrength="
				+ medicationStrength + ", medicationDosage=" + medicationDosage + ", patient=" + patient + "]";
	}
	
	
}
