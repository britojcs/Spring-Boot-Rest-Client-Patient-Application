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

import com.javaprojects.springboot.restclientpatient.model.Pharmacy;
import com.javaprojects.springboot.restclientpatient.service.PharmacyRestClientService;

@Controller
@RequestMapping("/pharmacy")
public class PharmacyController {
	
	private PharmacyRestClientService pharmacyRestClientService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//inject constructor
	@Autowired
	public PharmacyController(PharmacyRestClientService thePharmacyRestClientService) {
		pharmacyRestClientService = thePharmacyRestClientService;
	}
	
	/*************************************************/
	
	//Mapping
	@GetMapping("/list")
	public String listPharmacy(Model theModel) {
		logger.info("Listing Pharmacies");
		
		//get pharmacies from service
		List<Pharmacy> thePharmacies = pharmacyRestClientService.getAllPharmacies();
		
		logger.info("thePharmacies: " + thePharmacies);
		
		//add the pharmacies to model
		theModel.addAttribute("pharmacyList", thePharmacies);
		
		return "/admin/view-all-pharmacies";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		logger.info("Showing form for add");
		
		//create model attribute to bind form data
		Pharmacy thePharmacy = new Pharmacy();
		
		theModel.addAttribute("pharmacy", thePharmacy);
		
		return "/admin/pharmacy-form";
	}
	
	@PostMapping("/savePharmacy")
	public String savePharmacy(@ModelAttribute("pharmacy") Pharmacy thePharmacy) {
		
		if (thePharmacy.getId() == 0) {
			logger.info("Adding pharmacy: " + thePharmacy);
		}
		else {
			logger.info("updating pharmacy: " + thePharmacy);
			
		}
		
		//save the patient using service
		pharmacyRestClientService.saveOrUpdatePharmacy(thePharmacy);
		
		return "redirect:/pharmacy/list";
				
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("pharmacyId") int theId, Model theModel) {
		
		logger.info("Show form for update, pharmacyId = " + theId);
		
		//get the patient from service
		Pharmacy thePharmacy = pharmacyRestClientService.getPharmacyById(theId);
		
		logger.info("The Pharmacy: " + thePharmacy);
		
		//set patient as a model attribute to pre-populate to the form
		theModel.addAttribute("pharmacy", thePharmacy);
		
		//Now send over to the form
		return "/admin/pharmacy-form";
	}
	
	@GetMapping("/delete")
	public String deletePharmacy(@RequestParam("pharmacyId") int theId) {
		
		logger.info("deleting pharmacy: " + theId);
		
		//delete the patient
		pharmacyRestClientService.deletePharmacy(theId);
		
		return "redirect:/pharmacy/list";
	}
	
}
	
	
	