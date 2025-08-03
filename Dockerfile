# Usa como base OpenJDK 17 en Alpine Linux (liviana y optimizada)
FROM eclipse-temurin:17-jdk-alpine

# Crea el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia tu jar generado en el contenedor
# COPY target/personas-service-0.0.1-SNAPSHOT.jar app.jar
COPY target/*.jar app.jar


# Expone el puerto 8080 (ajústalo si tu app usa otro)
EXPOSE 8080

# Comando para ejecutar el jar cuando se inicie el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]


