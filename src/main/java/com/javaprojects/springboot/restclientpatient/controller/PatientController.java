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

import com.javaprojects.springboot.restclientpatient.model.Patient;
import com.javaprojects.springboot.restclientpatient.service.PatientRestClientService;

@Controller
@RequestMapping("/patient")
public class PatientController {
	
	private PatientRestClientService patientRestClientService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//Inject constructor
	@Autowired
	public PatientController(PatientRestClientService thePatientRestClientService) {
		patientRestClientService = thePatientRestClientService;
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
		
		return "/admin/view-all-patients";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		logger.info("Showing form for add");
		
		//create model attribute to bind form data
		Patient thePatient = new Patient();
		
		theModel.addAttribute("patient", thePatient);
		
		return "/patient/patient-form";
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
		
		//Now send over to the form
		return "/patient/patient-form";
	}
	
	@GetMapping("/delete")
	public String deletePatient(@RequestParam("patientId") int theId) {
		
		logger.info("deleting patient: " + theId);
		
		//delete the patient
		patientRestClientService.deletePatient(theId);
		
		return "redirect:/patient/list";
	}
	
}

	
