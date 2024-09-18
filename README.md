# Boimela Backend

This Spring Boot application uses `OpenJDK 17 (Liberica 17)` and `Docker`. Below are the setup instructions, including setting JAVA_HOME permanently and configuring the SDK in IntelliJ.

#### `Prerequisites: Java`

`Ensure you have Java 17 installed (Liberica 17).`

You can verify your Java version with:

```bash
java --version
```

The output should be similar to:

```code
openjdk 17.0.12 2024-07-16
OpenJDK Runtime Environment Liberica (build 17.0.12+7)
OpenJDK 64-Bit Server VM (build 17.0.12+7, mixed mode)
```

<br />

### Setting JAVA_HOME Permanently:

To set the `JAVA_HOME` environment variable permanently, follow these steps:

### `On macOS:`

Open your terminal. Edit your shell profile file, depending on the shell you are using:

`For bash: Open ~/.bash_profile:`

```bash
nano ~/.bash_profile
```

`For zsh (default on macOS Catalina and later): Open ~/.zshrc:`

```bash
nano ~/.zshrc
```

Add the following lines at the bottom of the file:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH
```

Save and close the file `(press CTRL + X, then Y, and Enter)`.

#### Apply the changes:

```bash
source ~/.bash_profile # If using bash source
source ~/.zshrc # If using zsh
```

### `On Windows:`

- Right-click on This PC and select Properties.
- Click on Advanced system settings.
- In the System Properties window, click the Environment Variables button.
- Under System Variables, click New and set:
  - Variable name: JAVA_HOME
  - Variable value: The path to your JDK 17 installation, e.g., C:\Program Files\Liberica\jdk-17.
- Click OK, then find the Path variable, select it, and click Edit.
- Add a new entry: %JAVA_HOME%\bin
- Click OK to save changes.
  <br />

### IntelliJ Setup

`Configuring the SDK in IntelliJ` :

After setting up JAVA_HOME, you need to configure the SDK in IntelliJ:

- Open IntelliJ IDEA.
- Go to File > Project Structure (or press Cmd + ;).
- In the Project Settings section, click Project.
- Under Project SDK, click New > JDK and navigate to the directory where you installed JDK 17.
- Select the folder and click OK.
- Once the SDK is set, ensure the Project language level is set to 17 (or Default SDK) to match the JDK version.
- Click Apply and OK to save changes.

### Docker setup

Ensure Docker is installed on your system. Verify the installation with:

```bash
docker --version
```

### Database Setup (Using Docker)

If you use a PostgreSQL database, start the container using Docker:

```bash
docker run --name your-postgres-container
-e POSTGRES_PASSWORD=yourpassword
-e POSTGRES_USER=youruser
-e POSTGRES_DB=yourdatabase
-p 5432:5432
-d postgres:latest Running Tests
```

### Project Setup

#### Clone the repository:

```bash
git clone https://github.com/mahjabeen-sust/boimela-backend.git

cd boimela-backend
```

#### Configuration

Edit the application.properties or application.yml file located in src/main/resources/ to update any configuration settings for your database or other services.

`Example configuration for PostgreSQL:`

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdatabase
spring.datasource.username=youruser
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

#### Install dependencies:

Run the following to install dependencies:

```bash
./mvnw clean install -DskipTests
```

#### Run the application:

Start the Spring Boot application with Maven:

```bash
./mvnw spring-boot:run
```

#### To run tests:

```bash
./mvnw test
```

#### Packaging the Application To build a .jar file:

```bash
./mvnw clean package
```

The .jar file will be located in the target/ directory.

#### Running the JAR To run the JAR:

```bash
java -jar target/your-app-name.jar
```

<br />

`Resources/Topics that could help you:`

1. [Learn DTO pattern](https://www.youtube.com/watch?v=THv-TI1ZNMk&t=1526s)
2. [How to connect PostgreSQL and Docker to Spring Boot application](https://www.youtube.com/watch?v=A8qZUF-GcKo)
