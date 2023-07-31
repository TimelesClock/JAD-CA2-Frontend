package util;

import java.io.StringReader;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.core.Response;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class JsonUtil {
	public static JsonObject getData(Response res) {
		String result = (String) res.readEntity(String.class);
    	JsonReader jsonReader = Json.createReader(new StringReader(result));
		JsonObject data = jsonReader.readObject();
		return data;
	}
	
}
