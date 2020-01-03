package com.intuit.developer.helloworld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PushingToDbService {
	
	@Autowired
	@Qualifier("adapterWSTemplate")
	RestTemplate restTemplateConfig;
	
	public void putInvoice(Object object,String realmId) {
		HttpEntity<Object> request = new HttpEntity<>(object);
		Object response  = restTemplateConfig.postForObject("/addInvoices?companyId="+realmId, request, Object.class);
		System.out.println(response.toString());
	}
	
	
	public void putBills(Object object,String realmId) {
		HttpEntity<Object> request = new HttpEntity<>(object);
		Object response  = restTemplateConfig.postForObject("/addBills?companyId="+realmId, request, Object.class);
		System.out.println(response.toString());
	}

	public void addChartOfAccounts(Object object,String realmId){
		HttpEntity<Object> request = new HttpEntity<>(object);
		Object response = restTemplateConfig.postForObject("http://localhost:8081/addAccounts?companyId="+realmId,request,Object.class);
		System.out.println(response);

	}

	public void addUserCompany(Object object,String userId,String realmId){
		HttpEntity<Object> request= new HttpEntity<>(object);
	Object response = restTemplateConfig.postForObject("/companyInfo?userId="+ userId + "?companyId="+realmId,request,Object.class);
	}
}
