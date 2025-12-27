FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Expose port (optional but good)
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/*.jar"]
