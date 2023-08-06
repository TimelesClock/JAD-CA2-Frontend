package stripe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StripeConnection {
	public static String getStripeApiKey() {
        Properties properties = new Properties();
        String apiKey = "";
        
        try (InputStream inputStream = StripeConnection.class.getResourceAsStream("Stripe.properties")) {
            properties.load(inputStream);
            
            apiKey = properties.getProperty("apiKey");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apiKey;
    }
}
