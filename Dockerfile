# Use an image with Maven installed, e.g., maven:3.8.5-openjdk-11
FROM maven:3.8.5-openjdk-17

# Set the working directory
WORKDIR /app

# Copy the project files to the container
COPY . /app

# Ensure Maven dependencies are downloaded (optional, speeds up builds)
RUN mvn dependency:resolve

# Define the command to run your tests
CMD ["mvn", "clean", "test"]