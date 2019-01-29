package com.javaprojects.springboot.restclientpatient.service;

import java.util.List;

import com.javaprojects.springboot.restclientpatient.model.Physician;

public interface PhysicianRestClientService {

	public List<Physician> getAllPhysicians();
	
	public void saveOrUpdatePhysician(Physician thePhysician);
	
	public Physician getPhysicianById(int physicianId);
	
	public void deletePhysician(int physicianId);
	
	public List<Physician> getPhysicianForPatient(int patientId);
}
