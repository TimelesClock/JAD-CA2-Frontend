package util;

import javax.ws.rs.client.Client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jose4j.json.internal.json_simple.JSONObject;

import javax.servlet.http.HttpServletRequest;



public class AppUtil {
	private String hostname = "http://localhost:8081/";
	
	public Response get(String url) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname+url);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response res = invocationBuilder.get();
		
		return res;
	}
	
	public Response post(String url, JSONObject json) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(hostname+url);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response res = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
		
		return res;
	}
	
	public Response put(String url, JSONObject json) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(hostname + url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response res = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON));

        return res;
    }

    public Response delete(String url) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(hostname + url);
        Invocation.Builder invocationBuilder = target.request();
        Response res = invocationBuilder.delete();

        return res;
    }
}
