package com.javaprojects.springboot.restclientpatient.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pharmacy {
	
	//define the fields
		
		private int id;
		private String pharmacyName;
		private String pharmacyPhone;
		private String pharmacyAddress;
		private Set<Patient> patients = new HashSet<>();
		
		public Set<Patient> getPatients() {
			return this.patients;
		}


		public void setPatients(Set<Patient> patients) {
			this.patients = patients;
		}

		//add convenience methods for bi-directional relationship
		public void addPatientToPharmacy(Patient tempPatient) {
			if (patients == null) {
				patients = new HashSet<>();
			}
			patients.add(tempPatient);
			
		}
		
		
		
		/***************************************************************************/
		
		//create constructors
		
		public Pharmacy() {
		
		}


		public Pharmacy(String pharmacyName, String pharmacyPhone, String pharmacyAddress) {
			this.pharmacyName = pharmacyName;
			this.pharmacyPhone = pharmacyPhone;
			this.pharmacyAddress = pharmacyAddress;
			
		}
		
		//generate getter/setter methods
		
		public int getId() {
			return this.id;
		}
		

		public void setId(int id) {
			this.id = id;
		}


		public String getPharmacyName() {
			return pharmacyName;
		}


		public void setPharmacyName(String pharmacyName) {
			this.pharmacyName = pharmacyName;
		}


		public String getPharmacyPhone() {
			return pharmacyPhone;
		}


		public void setPharmacyPhone(String pharmacyPhone) {
			this.pharmacyPhone = pharmacyPhone;
		}


		public String getPharmacyAddress() {
			return pharmacyAddress;
		}


		public void setPharmacyAddress(String pharmacyAddress) {
			this.pharmacyAddress = pharmacyAddress;
		}




		//generate toString() method
		@Override
		public String toString() {
			return "Pharmacy [id=" + id + ", pharmacyName=" + pharmacyName + ", pharmacyPhone=" + pharmacyPhone
					+ ", pharmacyAddress=" + pharmacyAddress + ", patientId_FK=" + patients + "]";
		}	
	

}
