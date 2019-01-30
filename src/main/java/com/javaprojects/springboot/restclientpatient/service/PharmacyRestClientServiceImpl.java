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
import com.javaprojects.springboot.restclientpatient.model.Pharmacy;


@Service
public class PharmacyRestClientServiceImpl implements PharmacyRestClientService {
	
	private RestTemplate pharmacyRestTemplate;
	
	@Value("${patients.spring.data.rest.url}")
	private String patientSpringDataRestUrl;
	
	@Value("${pharmacies.spring.data.rest.url}")
	private String pharmacySpringDataRestUrl;
	
	private ObjectMapper pharmacyObjectMapper;
	
	private Logger pharmacyLogger = LoggerFactory.getLogger(getClass());
	
	//Inject Constructor
	@Autowired
	public PharmacyRestClientServiceImpl(RestTemplateBuilder pharmacyRestTemplateBuilder) {
		pharmacyRestTemplate = pharmacyRestTemplateBuilder.build();
		pharmacyObjectMapper = new ObjectMapper();
	}
		

	@Override
	public List<Pharmacy> getAllPharmacies() {
		
		try {
			//make REST call
			ResponseEntity<String> entity = pharmacyRestTemplate.getForEntity(pharmacySpringDataRestUrl + "?sort=pharmacyName, asc",String.class);
			
			//get the response body
			String body = entity.getBody();
			
			//grab the node for _embedded comes from HAL_Spring Data REST
			JsonNode node = pharmacyObjectMapper.readTree(body).get("_embedded");
			
			//get the list of pharmacys as json node
			JsonNode pharmacyNode = node.get("pharmacies");
			
			//write contents of Json node to Json string
			String value = pharmacyObjectMapper.writeValueAsString(pharmacyNode);
			
			//convert json string to java collection List
			List<Pharmacy> pharmacyList = pharmacyObjectMapper.readValue(value, 
					new TypeReference<List<Pharmacy>>() {});
			
			return pharmacyList;
			
		} catch (Exception exc) {
			pharmacyLogger.error(exc.getMessage(), exc);
			throw new RuntimeException(exc);
		}
	}

	@Override
	public void saveOrUpdatePharmacy(Pharmacy thePharmacy) {

		int pharmacyId = thePharmacy.getId();
		
		if (pharmacyId == 0) {
			//that means new pharmacy need to be added
			pharmacyRestTemplate.postForEntity(pharmacySpringDataRestUrl, thePharmacy, String.class);
		}else {
			//that means update current pharmacy
			pharmacyRestTemplate.put(pharmacySpringDataRestUrl + "/" + pharmacyId,  thePharmacy);
		}

	}

	@Override
	public Pharmacy getPharmacyById(int pharmacyId) {

		try {
			//make REST call
			ResponseEntity<String> entity = pharmacyRestTemplate.getForEntity(pharmacySpringDataRestUrl + "/" + pharmacyId,
					String.class);
			
			//get the response body
			String body = entity.getBody();
			
			//convert json string to pharmacy
			Pharmacy thePharmacy = pharmacyObjectMapper.readValue(body,  new TypeReference<Pharmacy>() {});
			
			return thePharmacy;
			
		}catch (Exception exc) {
			pharmacyLogger.error(exc.getMessage(), exc);
			throw new RuntimeException(exc);
		}
	}

	@Override
	public void deletePharmacy(int pharmacyId) {
		// make REST call
		pharmacyRestTemplate.delete(pharmacySpringDataRestUrl + "/" + pharmacyId);
	}


}
