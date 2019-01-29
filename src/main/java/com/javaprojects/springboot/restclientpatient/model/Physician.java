package com.javaprojects.springboot.restclientpatient.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Physician {
	
	
	//define the fields

	private int id;
	private String physicianName;
	private String physicianPhone;
	private String physicianAddress;
	private String physicianSpecialty;
	
	/*****************************************************************/
	//Relationship with patients table
	//a doctor can have many patients, and  a patient can have many doctors
	//No delete cascade type.  Can't not delete doctor once a patient is deleted
	/*@ManyToMany(fetch = FetchType.EAGER,
				cascade= {CascadeType.PERSIST, CascadeType.MERGE,
						 CascadeType.DETACH, CascadeType.REFRESH})
	*/
	
	private Set<Patient> patients = new HashSet<>();
	
	public Set<Patient> getPatients() {
		return this.patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}
	
	
	//add a convenience method
	public void addPatientToPhysician(Patient thePatient) {
		if(patients == null) {
			patients = new HashSet<>();
		}
		patients.add(thePatient);
	}

	
	/*****************************************************************/
	//Constructors
	public Physician() {
		
	}

	/**
	 * @param physicanName
	 * @param physicianPhone
	 * @param physicianAddress
	 * @param physicianSpecialty
	 */
	public Physician(String physicianName, String physicianPhone, String physicianAddress, String physicianSpecialty) {
		this.physicianName = physicianName;
		this.physicianPhone = physicianPhone;
		this.physicianAddress = physicianAddress;
		this.physicianSpecialty = physicianSpecialty;
	}
	
	//Define getter and setter

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhysicianName() {
		return physicianName;
	}

	public void setPhysicianName(String physicanName) {
		this.physicianName = physicanName;
	}

	public String getPhysicianPhone() {
		return physicianPhone;
	}

	public void setPhysicianPhone(String physicianPhone) {
		this.physicianPhone = physicianPhone;
	}

	public String getPhysicianAddress() {
		return physicianAddress;
	}

	public void setPhysicianAddress(String physicianAddress) {
		this.physicianAddress = physicianAddress;
	}

	public String getPhysicianSpecialty() {
		return physicianSpecialty;
	}

	public void setPhysicianSpecialty(String physicianSpecialty) {
		this.physicianSpecialty = physicianSpecialty;
	}

	

	
	@Override
	public String toString() {
		return "Physician [id=" + id + ", physicianName=" + physicianName + ", physicianPhone=" + physicianPhone
				+ ", physicianAddress=" + physicianAddress + ", physicianSpecialty=" + physicianSpecialty + "]";
	}
	
	

}
