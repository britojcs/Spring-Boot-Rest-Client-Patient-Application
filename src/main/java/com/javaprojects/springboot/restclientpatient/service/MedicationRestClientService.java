package com.javaprojects.springboot.restclientpatient.service;

import java.util.List;

import com.javaprojects.springboot.restclientpatient.model.Medication;

public interface MedicationRestClientService {
	
	public List<Medication> getAllMedications();
	
	public void saveOrUpdateMedication(Medication theMedication);
	
	public Medication getMedicationById(int medicationId);
	
	public void deleteMedication(int medicationId);
	
	public void addMedicationToPatient(int patientId, int medicationId);
		

}
