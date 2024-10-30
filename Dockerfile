FROM openjdk:17

WORKDIR /root

# Copiar archivos necesarios
COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root
COPY ./src /root/src
COPY ./wait-for-it.sh /root

RUN chmod +x /root/wait-for-it.sh

# Descargar dependencias en modo offline
RUN ./mvnw dependency:go-offline

# Instalar el proyecto sin ejecutar las pruebas
RUN ./mvnw clean install -DskipTests

EXPOSE 8081

# Usar el script para esperar a PostgreSQL antes de iniciar la aplicación
ENTRYPOINT ["/root/wait-for-it.sh", "postgres:5432", "--timeout=60", "--", "java", "-jar", "/root/target/consumer-ms-0.0.1-SNAPSHOT.jar"]
