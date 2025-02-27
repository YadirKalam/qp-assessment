# Step 1: Use a base image with Java JDK
FROM openjdk:17-jdk-slim

# Step 2: Create a directory in the container for storing app files (optional but organized)
WORKDIR /app

# Step 3: Copy the JAR file of your Spring Boot application into the container
# Ensure your JAR file is correctly named; for example, `your-app-name.jar` is the built JAR file.
COPY target/your-app-name.jar app.jar

# Step 4: Expose the port your Spring Boot application runs on
EXPOSE 8080

# Step 5: Define the entry point for the container to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
