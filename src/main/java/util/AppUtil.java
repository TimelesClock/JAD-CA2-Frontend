package util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;

public class AppUtil {
	public Response get(String url,HttpServletRequest request) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8081/"+url);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response res = invocationBuilder.get();
		if (res.getStatus() != Response.Status.OK.getStatusCode()) {
			request.setAttribute("err", "Could not get books");
		}
		
		return res;
	}
}
