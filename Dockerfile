# Etapa de build
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copia el proyecto y compila
COPY . /app
RUN ./mvnw clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar jar desde builder
COPY --from=builder /app/target/*.jar app.jar

# Variables de entorno (pueden sobrescribirse desde docker-compose o GitHub Actions)
ENV P12_PATH=/certs/certificado.p12
ENV P12_PASSWORD=changeme
ENV AFIP_CUIT=20301234567
ENV SPRING_PROFILES_ACTIVE=prod

# Copiar el certificado si lo empaquetas
# COPY certificado-prod.p12 /certs/certificado-prod.p12

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]