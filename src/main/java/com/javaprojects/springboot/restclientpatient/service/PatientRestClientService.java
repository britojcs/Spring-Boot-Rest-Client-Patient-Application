package com.javaprojects.springboot.restclientpatient.service;

import java.util.List;

import com.javaprojects.springboot.restclientpatient.model.Medication;
import com.javaprojects.springboot.restclientpatient.model.Patient;
import com.javaprojects.springboot.restclientpatient.model.Pharmacy;
import com.javaprojects.springboot.restclientpatient.model.Physician;

public interface PatientRestClientService {
	
	public List<Patient> getAllPatients();
	
	public void saveOrUpdatePatient(Patient thePatient);
	
	public Patient getPatientById(int patientId);
	
	public void deletePatient(int patientId);
	
	public List<Medication> getMedicationForPatient(int patientId);
	
	public List<Pharmacy> getPharmacyForPatient(int patientId);
	
	public List<Physician> getPhysicianForPatient(int patientId);
}
