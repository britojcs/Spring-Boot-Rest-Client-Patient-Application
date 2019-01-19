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
import com.javaprojects.springboot.restclientpatient.model.Patient;

@Service
public class PatientRestClientServiceImpl implements PatientRestClientService {

	private RestTemplate patientRestTemplate;
	
	@Value("${patients.rest.api.url}")
	private String patientRestApiUrl;
	
	private ObjectMapper patientObjectMapper;
	
	private Logger patientLogger = LoggerFactory.getLogger(getClass());
	
	//Inject Constructor
	@Autowired
	public PatientRestClientServiceImpl(RestTemplateBuilder patientRestTemplateBuilder) {
		patientRestTemplate = patientRestTemplateBuilder.build();
		patientObjectMapper = new ObjectMapper();
	}
	
	//Methods
	@Override
	public List<Patient> getAllPatients() throws RuntimeException{
		
		try {
			//make REST call
			ResponseEntity<String> entity = patientRestTemplate.getForEntity(patientRestApiUrl + "?sort=lastName, asc",String.class);
			
			//get the response body
			String body = entity.getBody();
			
			//grab the node for _embedded comes from HAL_Spring Data REST
			JsonNode node = patientObjectMapper.readTree(body).get("_embedded");
			
			//get the list of patients as json node
			JsonNode patientNode = node.get("patients");
			
			//write contents of Json node to Json string
			String value = patientObjectMapper.writeValueAsString(patientNode);
			
			//convert json string to java collection List
			List<Patient> patientList = patientObjectMapper.readValue(value, 
					new TypeReference<List<Patient>>() {});
			
			return patientList;
			
		} catch (Exception exc) {
			patientLogger.error(exc.getMessage(), exc);
			throw new RuntimeException(exc);
		}
		
	}

	@Override
	public void saveOrUpdatePatient(Patient thePatient) {

		int patientId = thePatient.getId();
		
		if (patientId == 0) {
			//that means new patient need to be added
			patientRestTemplate.postForEntity(patientRestApiUrl, thePatient, String.class);
		}else {
			//that means update current patient
			patientRestTemplate.put(patientRestApiUrl + "/" + patientId,  thePatient);
		}

	}

	@Override
	public Patient getPatientById(int patientId) {
		try {
			//make REST call
			ResponseEntity<String> entity = patientRestTemplate.getForEntity(patientRestApiUrl + "/" + patientId,
					String.class);
			
			//get the response body
			String body = entity.getBody();
			
			//convert json string to Patient
			Patient thePatient = patientObjectMapper.readValue(body,  new TypeReference<Patient>() {});
			
			return thePatient;
			
		}catch (Exception exc) {
			patientLogger.error(exc.getMessage(), exc);
			throw new RuntimeException(exc);
		}
	}

	@Override
	public void deletePatient(int patientId) {
		// make REST call
		patientRestTemplate.delete(patientRestApiUrl + "/" + patientId);

	}

}
