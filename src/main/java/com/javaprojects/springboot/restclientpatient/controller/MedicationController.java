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
import com.javaprojects.springboot.restclientpatient.service.MedicationRestClientService;

@Controller
@RequestMapping("/medication")
public class MedicationController {
	
	private MedicationRestClientService medicationRestClientService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//inject constructor
	@Autowired
	public MedicationController(MedicationRestClientService theMedicationRestClientService) {
		
		medicationRestClientService = theMedicationRestClientService;
	}
	
	/********Mapping*********/
	
	@GetMapping("/medication-list")
	public String listMedication(Model theModel) {
		logger.info("Listing Medications");
		
		//get medications from service
		List<Medication> theMedications = medicationRestClientService.getAllMedications();
		
		logger.info("theMedications: " + theMedications);
		
		//add the medications to model
		theModel.addAttribute("medicationList", theMedications);
		theModel.addAttribute("breadcrumbItem", "List of Medications");
		
		return "/admin/view-all-medications";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		logger.info("Showing form for add");
		
		//create model attribute to bind form data
		Medication theMedication = new Medication();
		
		theModel.addAttribute("medication", theMedication);
		theModel.addAttribute("breadcrumbItem", "Add Medication");
		
		return "/admin/medication-form";
	}
	
	@PostMapping("/saveMedication")
	public String saveMedication(@ModelAttribute("medication") Medication theMedication) {
		
		if (theMedication.getId() == 0) {
			logger.info("Adding medication: " + theMedication);
		}
		else {
			logger.info("updating medication: " + theMedication);
			
		}
		
		//save the patient using service
		medicationRestClientService.saveOrUpdateMedication(theMedication);
		
		return "redirect:/medication/list";
				
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("medicationId") int theId, Model theModel) {
		
		logger.info("Show form for update, medicationId = " + theId);
		
		//get the patient from service
		Medication theMedication = medicationRestClientService.getMedicationById(theId);
		
		logger.info("The Medication: " + theMedication);
		
		//set patient as a model attribute to pre-populate to the form
		theModel.addAttribute("medication", theMedication);
		theModel.addAttribute("breadcrumbItem", "Update Medication");
		
		//Now send over to the form
		return "/admin/medication-form";
	}
	
	@GetMapping("/delete")
	public String deleteMedication(@RequestParam("medicationId") int theId) {
		
		logger.info("deleting medication: " + theId);
		
		//delete the patient
		medicationRestClientService.deleteMedication(theId);
		
		return "redirect:/medication/list";
	}

}
