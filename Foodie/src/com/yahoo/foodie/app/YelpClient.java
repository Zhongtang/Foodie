package com.yahoo.foodie.app;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class YelpClient {
	public static final Class<? extends Api> REST_API_CLASS = YelpApi2.class;
    public static final String REST_URL = "http://api.yelp.com/v2"; 
    private static final String REST_CONSUMER_KEY = "8Bo_iY7MRX7IfsYZ8U17IA";
    private static final String REST_CONSUMER_SECRET = "4q-sNU4wiOdWGZ-GqgyvUjvxNSE";
    private static final String REST_TOKEN = "e5f1p_bSihbhzqND2VytNHN06d5BDvbh";
    private static final String REST_TOKEN_SECRET = "csu-3Ns91hUFuc1nrVAOsR1mojg";
    //private static final String REST_CALLBACK_URL = "oauth://foodieapp";
    
    
    private final OAuthService service;
    private final Token accessToken;
    
    public YelpClient() {
    	this(REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_TOKEN, REST_TOKEN_SECRET);
    }
    
    /**
  * Setup the Yelp API OAuth credentials.
  *
  * OAuth credentials are available from the developer site, under Manage API access (version 2 API).
  *
  * @param consumerKey Consumer key
  * @param consumerSecret Consumer secret
  * @param token Token
  * @param tokenSecret Token secret
  * 
  */
  private YelpClient(String consumerKey, String consumerSecret, String token, String tokenSecret) {
      this.service = new ServiceBuilder().provider(YelpApi2.class)
    		  			.apiKey(consumerKey)
    		  			.apiSecret(consumerSecret)
    		  			.build();
      this.accessToken = new Token(token, tokenSecret);
    }

    /**
  * Search with term and location.
  *
  * @param term Search term
  * @param latitude Latitude
  * @param longitude Longitude
  * @return JSON string response
  */
    public String search(String term, double latitude, double longitude) {
      OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
      request.addQuerystringParameter("term", term);
      request.addQuerystringParameter("ll", latitude + "," + longitude);
      this.service.signRequest(this.accessToken, request);
      Response response = request.send();
      return response.getBody();
    }
}
