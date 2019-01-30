package com.javaprojects.springboot.restclientpatient.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaprojects.springboot.restclientpatient.model.Medication;
import com.javaprojects.springboot.restclientpatient.model.Physician;

@Service
public class PhysicianRestClientServiceImpl implements PhysicianRestClientService {
	
	private RestTemplate physicianRestTemplate;
	
	@Value("${patients.spring.data.rest.url}")
	private String patientSpringDataRestUrl;
	
	@Value("${physicians.spring.data.rest.url}")
	private String physicianSpringDataRestUrl;
	
	private ObjectMapper physicianObjectMapper;
	
	private Logger physicianLogger = LoggerFactory.getLogger(getClass());
	
	//Inject Constructor
	@Autowired
	public PhysicianRestClientServiceImpl(RestTemplateBuilder physicianRestTemplateBuilder) {
		physicianRestTemplate = physicianRestTemplateBuilder.build();
		physicianObjectMapper = new ObjectMapper();		
	}

	@Override
	public List<Physician> getAllPhysicians() throws RuntimeException{

		
		try {
			//make REST call
			ResponseEntity<String> entity = physicianRestTemplate.getForEntity(physicianSpringDataRestUrl + "?sort=physicianName, asc",String.class);
			
			//get the response body
			String body = entity.getBody();
			
			//grab the node for _embedded comes from HAL_Spring Data REST
			JsonNode node = physicianObjectMapper.readTree(body).get("_embedded");
			
			//get the list of physicians as json node
			JsonNode physicianNode = node.get("physicians");
			
			//write contents of Json node to Json string
			String value = physicianObjectMapper.writeValueAsString(physicianNode);
			
			//convert json string to java collection List
			List<Physician> physicianList = physicianObjectMapper.readValue(value, 
					new TypeReference<List<Physician>>() {});
			
			return physicianList;
			
		} catch (Exception exc) {
			physicianLogger.error(exc.getMessage(), exc);
			throw new RuntimeException(exc);
		}
		
	}

	@Override
	public void saveOrUpdatePhysician(Physician thePhysician) {


		int physicianId = thePhysician.getId();
		
		if (physicianId == 0) {
			//that means new physician need to be added
			physicianRestTemplate.postForEntity(physicianSpringDataRestUrl, thePhysician, String.class);
		}else {
			//that means update current physician
			physicianRestTemplate.put(physicianSpringDataRestUrl + "/" + physicianId,  thePhysician);
		}
	}

	@Override
	public Physician getPhysicianById(int physicianId) {

		try {
			//make REST call
			ResponseEntity<String> entity = physicianRestTemplate.getForEntity(physicianSpringDataRestUrl + "/" + physicianId,
					String.class);
			
			//get the response body
			String body = entity.getBody();
			
			//convert json string to physician
			Physician thePhysician = physicianObjectMapper.readValue(body,  new TypeReference<Physician>() {});
			
			return thePhysician;
			
		}catch (Exception exc) {
			physicianLogger.error(exc.getMessage(), exc);
			throw new RuntimeException(exc);
		}
	}

	@Override
	public void deletePhysician(int physicianId) {

		// make REST call
		physicianRestTemplate.delete(physicianSpringDataRestUrl + "/" + physicianId);
	}

	

}
