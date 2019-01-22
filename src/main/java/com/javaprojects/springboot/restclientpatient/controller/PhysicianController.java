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

import com.javaprojects.springboot.restclientpatient.model.Physician;
import com.javaprojects.springboot.restclientpatient.service.PhysicianRestClientService;



@Controller
@RequestMapping("/physician")
public class PhysicianController {
	
	
	private PhysicianRestClientService physicianRestClientService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//inject constructor
	@Autowired
	public PhysicianController(PhysicianRestClientService thePhysicianRestClientService) {
		physicianRestClientService = thePhysicianRestClientService;
	}
	
	/*************************************************/
	
	//Mapping
	@GetMapping("/list")
	public String listPhysician(Model theModel) {
		logger.info("Listing Physicians");
		
		//get physicians from service
		List<Physician> thePhysicians = physicianRestClientService.getAllPhysicians();
		
		logger.info("thePhysicians: " + thePhysicians);
		
		//add the physicians to model
		theModel.addAttribute("physicianList", thePhysicians);
		
		return "/admin/view-all-physicians";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		logger.info("Showing form for add");
		
		//create model attribute to bind form data
		Physician thePhysician = new Physician();
		
		theModel.addAttribute("physician", thePhysician);
		
		return "/physician/physician-form";
	}
	
	@PostMapping("/savePhysician")
	public String savePhysician(@ModelAttribute("physician") Physician thePhysician) {
		
		if (thePhysician.getId() == 0) {
			logger.info("Adding physician: " + thePhysician);
		}
		else {
			logger.info("updating physician: " + thePhysician);
			
		}
		
		//save the patient using service
		physicianRestClientService.saveOrUpdatePhysician(thePhysician);
		
		return "redirect:/physician/list";
				
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("physicianId") int theId, Model theModel) {
		
		logger.info("Show form for update, physicianId = " + theId);
		
		//get the patient from service
		Physician thePhysician = physicianRestClientService.getPhysicianById(theId);
		
		logger.info("The Physician: " + thePhysician);
		
		//set patient as a model attribute to pre-populate to the form
		theModel.addAttribute("physician", thePhysician);
		
		//Now send over to the form
		return "/physician/physician-form";
	}
	
	@GetMapping("/delete")
	public String deletePhysician(@RequestParam("physicianId") int theId) {
		
		logger.info("deleting physician: " + theId);
		
		//delete the patient
		physicianRestClientService.deletePhysician(theId);
		
		return "redirect:/physician/list";
	}

}
