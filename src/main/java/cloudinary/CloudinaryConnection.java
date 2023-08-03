package cloudinary;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
            // Get the file name and extension from the Part
            String fileName = imageFile.getSubmittedFileName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

            // Create a temporary File on the server
            File tempFile = File.createTempFile("temp", "." + fileExtension);

            // Copy the content of the Part to the temporary File
            try (InputStream inputStream = imageFile.getInputStream()) {
                Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // Upload the temporary File to Cloudinary
            Map<?, ?> uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());

            // Get the secure URL of the uploaded image
            String secureUrl = (String) uploadResult.get("secure_url");

            // Delete the temporary File
            tempFile.delete();

            return secureUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static void deleteFromCloudinary(Cloudinary cloudinary,String url)throws IOException {
		String id = "";
		int lastSlashIndex = url.lastIndexOf('/');
		int lastDotIndex = url.lastIndexOf('.');
		if (lastSlashIndex >= 0 && lastDotIndex >= 0 && lastSlashIndex < lastDotIndex) {
            id = url.substring(lastSlashIndex + 1, lastDotIndex);
        }

		cloudinary.uploader().destroy(id,
				  ObjectUtils.asMap("resource_type","image"));
	}
}

