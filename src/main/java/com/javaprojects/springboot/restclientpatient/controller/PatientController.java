package com.javaprojects.springboot.restclientpatient.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaprojects.springboot.restclientpatient.model.Medication;
import com.javaprojects.springboot.restclientpatient.model.Patient;
import com.javaprojects.springboot.restclientpatient.model.Pharmacy;
import com.javaprojects.springboot.restclientpatient.model.Physician;
import com.javaprojects.springboot.restclientpatient.service.MedicationRestClientService;
import com.javaprojects.springboot.restclientpatient.service.PatientRestClientService;
import com.javaprojects.springboot.restclientpatient.service.PharmacyRestClientService;
import com.javaprojects.springboot.restclientpatient.service.PhysicianRestClientService;

@Controller
@RequestMapping("/patient")
public class PatientController {
	
	private PatientRestClientService patientRestClientService;
	private PharmacyRestClientService pharmacyRestClientService;
	private PhysicianRestClientService physicianRestClientService;
	private MedicationRestClientService medicationRestClientService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//Inject constructor
	@Autowired
	public PatientController(PatientRestClientService thePatientRestClientService,
							 PharmacyRestClientService thePharmacyRestClientService,
							 PhysicianRestClientService thePhysicianRestClientService,
							 MedicationRestClientService theMedicationRestClientService) {
		
		patientRestClientService = thePatientRestClientService;
		pharmacyRestClientService = thePharmacyRestClientService;
		physicianRestClientService = thePhysicianRestClientService;
		medicationRestClientService = theMedicationRestClientService;
	}
	
	/**************************************************/
	//Route 
	@GetMapping("/index")
	public String adminIndex() {
		return "admin/index";
	}
	
	@GetMapping("/list")
	public String listPatient(Model theModel) {
		logger.info("Listing Patients");
		
		//get patients from service
		List<Patient> thePatients = patientRestClientService.getAllPatients();
		
		logger.info("thePatients: " + thePatients);
		
		//add the patients to model
		theModel.addAttribute("patientList", thePatients);
		theModel.addAttribute("breadcrumbItem", "List of Patients");
		
		return "/admin/view-all-patients";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		logger.info("Showing form for add");
		
		//create model attribute to bind form data
		Patient thePatient = new Patient();
		
		theModel.addAttribute("patient", thePatient);
		theModel.addAttribute("breadcrumbItem", "Patient Name: " + thePatient.getPatientFullName());
		
		return "/admin/patient-form";
	}
	
	@PostMapping("/savePatient")
	public String savePatient(@ModelAttribute("patient") Patient thePatient) {
		
		if (thePatient.getId() == 0) {
			logger.info("Adding patient: " + thePatient);
		}
		else {
			logger.info("updating patient: " + thePatient);
			
		}
		
		//save the patient using service
		patientRestClientService.saveOrUpdatePatient(thePatient);
		
		return "redirect:/patient/list";
				
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("patientId") int theId, Model theModel) {
		
		logger.info("Show form for update, patientId = " + theId);
		
		//get the patient from service
		Patient thePatient = patientRestClientService.getPatientById(theId);
		
		logger.info("The Patient: " + thePatient);
		
		//set patient as a model attribute to pre-populate to the form
		theModel.addAttribute("patient", thePatient);
		theModel.addAttribute("breadcrumbItem", "Patient Name: " + thePatient.getPatientFullName());
		
		//Now send over to the form
		return "/admin/patient-form";
	}
	
	@GetMapping("/delete")
	public String deletePatient(@RequestParam("patientId") int theId) {
		
		logger.info("deleting patient: " + theId);
		
		//delete the patient
		patientRestClientService.deletePatient(theId);
		
		return "redirect:/patient/list";
	}
	
	/**********************************************************************
	@GetMapping("/viewPatientInfo")
	public String listPatientInfo(@RequestParam("patientId") int thePatientId,  Model theModel) {
		
		patient_id = thePatientId;
		
		//get medication list, physician list, and pharmacy list from corresponding services
		List<Medication> patientMedicationList = medicationService.getMedication(thePatientId);
		List<Pharmacy> patientPharmacyList = pharmacyService.getPharmacy(thePatientId);
		List<Physician> patientPhysicianList = physicianService.getPhysician(thePatientId);
		
		//get the patient from our service
		Patient thePatient = patientService.getPatientById(thePatientId);
		
				
		//set patient, medication, physician, and pharmacy as  model attributes to populate info
		theModel.addAttribute("selectedPatient", thePatient);
		theModel.addAttribute("medications", patientMedicationList);
		theModel.addAttribute("pharmacies", patientPharmacyList);
		theModel.addAttribute("physicians",  patientPhysicianList);
		
		//Send over to the patient info form
		return "patient-info";
	}
	
	/********************* Medication CRUD 	 * @param <CMDBean>**************************************/
	
	@GetMapping("/showPatientInfo")
	public String showPatientInfo(@RequestParam("patientId") int theId, Model theModel) {
		
		logger.info("Patient info, patientId = " + theId);
		
		//get the patient from service
		Patient thePatient = patientRestClientService.getPatientById(theId);
		
		//Get pharmacy and physician for patient
		List<Pharmacy> pharmacyList = patientRestClientService.getPharmacyForPatient(theId);
		List<Physician> physicianList = patientRestClientService.getPhysicianForPatient(theId);
		List<Medication> medicationList = patientRestClientService.getMedicationForPatient(theId);
		logger.info("The Patient: " + thePatient);
		
		//set patient as a model attribute to pre-populate to the form
		theModel.addAttribute("patientInfo", thePatient);
		theModel.addAttribute("pharmaciesForPatient", pharmacyList);
		theModel.addAttribute("physiciansForPatient", physicianList);
		theModel.addAttribute("medicationsForPatient", medicationList);
		theModel.addAttribute("breadcrumbItem", "Patient Name: " + thePatient.getPatientFullName());
		
		
		
		
		
		return "/admin/view-patient-info";
	}
	
}

	
