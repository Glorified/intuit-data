package com.intuit.developer.helloworld.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.intuit.developer.helloworld.client.OAuth2PlatformClientFactory;
import com.intuit.developer.helloworld.helper.QBOServiceHelper;
import com.intuit.developer.helloworld.service.PushingToDbService;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.exception.OAuthException;

/**
 * @author dderose
 *
 */
@Controller
public class CallbackController {
    
	@Autowired
	OAuth2PlatformClientFactory factory;
	@Autowired
	PushingToDbService pdbs;

	@Autowired
    public QBOServiceHelper helper;

    private static final Logger logger = Logger.getLogger(CallbackController.class);
    
    /**
     *  This is the redirect handler you configure in your app on developer.intuit.com
     *  The Authorization code has a short lifetime.
     *  Hence Unless a user action is quick and mandatory, proceed to exchange the Authorization Code for
     *  BearerToken
     *      
     * @param auth_code
     * @param state
     * @param realmId
     * @param session
     * @return
     */
    @RequestMapping("/oauth2redirect")
    public ResponseEntity<Object> callBackFromOAuth(@RequestParam("code") String authCode, @RequestParam("state") String state, @RequestParam(value = "realmId", required = false) String realmId, HttpSession session) {   
        logger.info("inside oauth2redirect of sample"  );
        try {
	        String csrfToken = (String) session.getAttribute("csrfToken");
	        if (csrfToken.equals(state)) {
	            session.setAttribute("realmId", realmId);
	            session.setAttribute("auth_code", authCode);
	
	            OAuth2PlatformClient client  = factory.getOAuth2PlatformClient();
	            String redirectUri = factory.getPropertyValue("OAuth2AppRedirectUri");
	            logger.info("inside oauth2redirect of sample -- redirectUri " + redirectUri  );
	            
	            BearerTokenResponse bearerTokenResponse = client.retrieveBearerTokens(authCode, redirectUri);
				 
	            session.setAttribute("access_token", bearerTokenResponse.getAccessToken());
	            session.setAttribute("refresh_token", bearerTokenResponse.getRefreshToken());
	    
	            // Update your Data store here with user's AccessToken and RefreshToken along with the realmId
	        //	CompletableFuture.runAsync(() -> {
	    			

	    	//	});
	    			
	    			
	    			try {
						DataService service = helper.getDataService(realmId, bearerTokenResponse.getAccessToken());
					
						if(pdbs.addUserCompany("",session.getAttribute("userId").toString(),realmId).getBody().equalsIgnoreCase("failed")) {return null;};
						String sql1 = "select * from invoice";
						QueryResult queryResult1 = service.executeQuery(sql1);
						pdbs.putInvoice(queryResult1.getEntities(),realmId);
						
						String sql2 = "select * from bill";
						QueryResult queryResult2 = service.executeQuery(sql2);
						pdbs.putBills(queryResult2.getEntities(),realmId);
						
						String sql3 = "select * from Account";
						QueryResult queryResult3= service.executeQuery(sql3);
						pdbs.addChartOfAccounts(queryResult3.getEntities(), realmId);
						
					} catch (FMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			URI yahoo = new URI("http://localhost:3000/sb2");
	    		    HttpHeaders httpHeaders = new HttpHeaders();
	    		    httpHeaders.setLocation(yahoo);
	    		    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	        }
	        logger.info("csrf token mismatch " );
        } catch (OAuthException e) {
        	logger.error("Exception in callback handler ", e);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return null;
    }


}
