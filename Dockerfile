# Use the official Tomcat image from the Docker Hub
FROM tomcat:9.0-jdk17

# Copy the WAR file into the webapps directory of Tomcat
COPY target/myapp.war /usr/local/tomcat/webapps/

# Expose port 8080 to access the app
EXPOSE 8080


# Expose port 5005 for remote debugging
EXPOSE 5005

# Set the JAVA_OPTS environment variable to enable remote debugging
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

# Start Tomcat
CMD ["catalina.sh", "run"]
