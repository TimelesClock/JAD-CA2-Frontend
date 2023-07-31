package util;

import javax.ws.rs.client.Client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;

import org.jose4j.json.internal.json_simple.JSONObject;

import java.util.List;
import java.util.Map;





public class AppUtil {
	private String hostname = "http://localhost:8081/";
	
	public Response get(String url) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname+url);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response res = invocationBuilder.get();
		
		
		return res;
	}
	
	//Overload cause java doesnt support optional params
	public Response get(String url, Map<String, Object> headers) {
	    Client client = ClientBuilder.newClient();
	    WebTarget target = client.target(hostname + url);
	    Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

	    if (headers != null) {
	        for (Map.Entry<String, Object> entry : headers.entrySet()) {
	            String headerName = entry.getKey();
	            Object headerValue = entry.getValue();
	            invocationBuilder.header(headerName, headerValue);
	        }
	    }

	    Response res = invocationBuilder.get();
	    return res;
	}
	
	public Response post(String url,JSONObject json) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname+url);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response res = invocationBuilder.post(Entity.entity(json,MediaType.APPLICATION_JSON));
		
		
		return res;
	}
	
	public Response post(String url, JSONObject json, Map<String, Object> headers) {
	    Client client = ClientBuilder.newClient();
	    WebTarget target = client.target(hostname + url);
	    Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

	    // Adding headers to the request
	    if (headers != null) {
	        for (Map.Entry<String, Object> entry : headers.entrySet()) {
	            String headerName = entry.getKey();
	            Object headerValue = entry.getValue();
	            invocationBuilder.header(headerName, headerValue);
	        }
	    }

	    Response res = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
	    return res;
	}
}
