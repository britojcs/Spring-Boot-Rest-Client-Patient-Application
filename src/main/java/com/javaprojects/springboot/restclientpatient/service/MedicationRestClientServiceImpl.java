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


@Service
public class MedicationRestClientServiceImpl implements MedicationRestClientService {

	private RestTemplate medicationRestTemplate;
	
	private RestTemplate patientRestTemplate;
	
	@Value("${patients.spring.data.rest.url}")  
	private String patientSpringDataRestUrl;
	
	@Value("${medications.spring.data.rest.url}")  
	private String medicationSpringDataRestUrl;
	
	private ObjectMapper medicationObjectMapper;
	
	private Logger medicationLogger = LoggerFactory.getLogger(getClass());
	
	//Inject Constructor
	@Autowired
	public MedicationRestClientServiceImpl(RestTemplateBuilder medicationRestTemplateBuilder) {
		medicationRestTemplate = medicationRestTemplateBuilder.build();
		medicationObjectMapper = new ObjectMapper();
	}
	
	
	@Override
	public List<Medication> getAllMedications() {
		
		try {
			//make REST call
			ResponseEntity<String> entity = medicationRestTemplate.getForEntity(medicationSpringDataRestUrl + "?sort=medicationName, asc",String.class);
			
			//get the response body
			String body = entity.getBody();
			
			//grab the node for _embedded comes from HAL_Spring Data REST
			JsonNode node = medicationObjectMapper.readTree(body).get("_embedded");
			
			//get the list of medications as json node
			JsonNode medicationNode = node.get("medications");
			
			//write contents of Json node to Json string
			String value = medicationObjectMapper.writeValueAsString(medicationNode);
			
			//convert json string to java collection List
			List<Medication> medicationList = medicationObjectMapper.readValue(value, 
					new TypeReference<List<Medication>>() {});
			
			return medicationList;
			
		} catch (Exception exc) {
			medicationLogger.error(exc.getMessage(), exc);
			throw new RuntimeException(exc);
		}
	}

	@Override
	public void saveOrUpdateMedication(Medication theMedication) {

		int medicationId = theMedication.getId();
		
		if (medicationId == 0) {
			//that means new medication need to be added
			medicationRestTemplate.postForEntity(medicationSpringDataRestUrl, theMedication, String.class);
		}else {
			//that means update current medication
			medicationRestTemplate.put(medicationSpringDataRestUrl + "/" + medicationId,  theMedication);
		}
	}

	@Override
	public Medication getMedicationById(int medicationId) {

		try {
			//make REST call
			ResponseEntity<String> entity = medicationRestTemplate.getForEntity(medicationSpringDataRestUrl + "/" + medicationId,
					String.class);
			
			//get the response body
			String body = entity.getBody();
			
			//convert json string to medication
			Medication theMedication = medicationObjectMapper.readValue(body,  new TypeReference<Medication>() {});
			
			return theMedication;
			
		}catch (Exception exc) {
			medicationLogger.error(exc.getMessage(), exc);
			throw new RuntimeException(exc);
		}
	}

	@Override
	public void deleteMedication(int medicationId) {
		
		// make REST call
		medicationRestTemplate.delete(medicationSpringDataRestUrl + "/" + medicationId);

	}

	@Override
	public List<Medication> getMedicationForPatient(int patientId) {
		
		try {
			//make REST call
			ResponseEntity<String> entity = patientRestTemplate.getForEntity(patientSpringDataRestUrl + "/" + patientId + "/" + "medications",
						String.class);
			
			//get the response body
			String body = entity.getBody();
			
			//grab the node for _embedded comes from HAL_Spring Data REST
			JsonNode node = medicationObjectMapper.readTree(body).get("_embedded");
			
			//get the list of medications as json node
			JsonNode medicationNode = node.get("medications");
			
			//write contents of Json node to Json string
			String value = medicationObjectMapper.writeValueAsString(medicationNode);
			
			//convert json string to java collection List
			List<Medication> medicationsForPatient = medicationObjectMapper.readValue(value, 
					new TypeReference<List<Medication>>() {});
			
			return medicationsForPatient;
			
		} catch (Exception exc) {
			medicationLogger.error(exc.getMessage(), exc);
			throw new RuntimeException(exc);
		}
	}

}
