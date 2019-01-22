package com.javaprojects.springboot.restclientpatient.service;

import java.util.List;

import com.javaprojects.springboot.restclientpatient.model.Pharmacy;



public interface PharmacyRestClientService {

	public List<Pharmacy> getAllPharmacies();
	
	public void saveOrUpdatePharmacy(Pharmacy thePharmacy);
	
	public Pharmacy getPharmacyById(int pharmacyId);
	
	public void deletePharmacy(int pharmacyId);

}
