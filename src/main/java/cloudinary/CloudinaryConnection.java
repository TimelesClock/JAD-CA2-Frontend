package cloudinary;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.HashMap;
import javax.servlet.http.Part;

public class CloudinaryConnection {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Cloudinary getCloudinary() {
        Properties properties = new Properties();
        Cloudinary cloudinary = null;

        try (InputStream inputStream = CloudinaryConnection.class.getResourceAsStream("Cloudinary.properties")) {
            properties.load(inputStream);

            String cloudName = properties.getProperty("cloud_name");
            String apiKey = properties.getProperty("api_key");
            String apiSecret = properties.getProperty("api_secret");
            
            Map config = new HashMap();
            config.put("cloud_name", cloudName);
            config.put("api_key", apiKey);
            config.put("api_secret", apiSecret);

            cloudinary = new Cloudinary(config);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cloudinary;
    }
	
	public static String uploadImageToCloudinary(Cloudinary cloudinary, Part imageFile) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(imageFile.getInputStream(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
