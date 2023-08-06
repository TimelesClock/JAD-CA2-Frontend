# Java Dynamic Web Project - SP Books - README

## Prerequisites
Java Development Kit (JDK) 17 or higher installed
Apache Tomcat or any other servlet container installed
MySQL server installed

Getting Started

1. Clone the repository to your local machine:

```bash
git clone <repository_url>
cd <project_directory>
```
2. Create the required property files:

Cloudinary API Keys: In the src/main/java/cloudinary directory, you'll find a file named cloudinary.example.properties. Rename this file to cloudinary.properties. Open the cloudinary.properties file in a text editor and replace the placeholders with your actual Cloudinary API keys:

```properties
cloudinary.cloud_name=your_cloud_name
cloudinary.api_key=your_api_key
cloudinary.api_secret=your_api_secret
```
MySQL Database Connection Credentials: In the src/main/java/dbaccess directory, you'll find a file named DBConnection.example.properties. Rename this file to DBConnection.properties. Open the DBConnection.properties file in a text editor and provide your MySQL database connection details:

```properties
db.url=jdbc:mysql://your_mysql_host:your_mysql_port/your_database_name
db.username=your_mysql_username
db.password=your_mysql_password
```
Save the changes to both property files.

3. Import the project files into eclipse and deploy it into the Apache Tomcat server 9

