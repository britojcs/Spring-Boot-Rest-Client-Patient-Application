package com.javaprojects.springboot.restclientpatient.service;

import java.util.List;

import com.javaprojects.springboot.restclientpatient.model.Patient;

public interface PatientRestClientService {
	
	public List<Patient> getAllPatients();
	
	public void saveOrUpdatePatient(Patient thePatient);
	
	public Patient getPatientById(int patientId);
	
	public void deletePatient(int patientId);
}
