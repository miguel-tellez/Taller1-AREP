# AREP_Taller1

## Diseño y estructuración de aplicaciones distribuidas en internet
En este taller usted explorará la arquitectura de las aplicaciones distribuidas. Concretamente, exploraremos la arquitectura de  los servidores web y el protocolo http sobre el que están soportados. 

# Project Structure

![image](https://github.com/user-attachments/assets/9b899527-4b83-4949-a0c3-e3e5f475af41)

#  Prerequisites
Before running the project, make sure you have the following installed:
- [JDK 21](https://www.oracle.com/co/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

1. **Ensure You Have Java Installed:**
   ```sh
   java -version
   ```
2. **Ensure You Have Maven Installed:**
   ```sh
   mvn -version
   ```
###  Instalación

1. **Clone this repository:**
   ```sh
   git clone https://github.com/TU_USUARIO/AREP_Taller1.git
   ```

2. **Navigate to the workshop directory:**
   ```sh
   cd AREP_Taller1
   ```

2. **By running the following command create the project:**
   ```sh
   mvn clean install
   ```

3. **run the server:**
   ```sh
   java -cp target/AREP_Taller1-1.0-SNAPSHOT.jar edu.escuelaing.arep.WebServer
   ```

4. **Access the web application in your browser:**
   ```
   http://localhost:32500
   ```

![image](https://github.com/user-attachments/assets/bf5b44d1-57ee-4c51-93d0-12fefae46c5e)

![image](https://github.com/user-attachments/assets/602a46e4-5edf-464a-9760-3ffa7e7336e0)


**Get all restaurants**
```sh
http://localhost:32500/api/restaurants
```

##  Web interface
The interface allows you to view restaurants dynamically. Use **HTML, CSS and JavaScript** to interact with the server.


##  Authors
- **Miguel Camilo Tellez** - [miguel-tellez](https://github.com/miguel-tellez)
  
