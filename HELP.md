# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.5/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.5/gradle-plugin/packaging-oci-image.html)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

---


```yaml
FROM amazoncorretto:17-alpine-jdk

ARG API_VERSION=1.0.0
ARG SPRING_PROFILES_ACTIVE=dev

ENV API_VERSION=${API_VERSION}
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

WORKDIR /app

COPY build/libs/tabnews.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

```yaml
FROM amazoncorretto:17-alpine-jdk

ARG DB_NAME
ARG DB_PASSWORD
ARG DB_USERNAME
ARG DB_HOST=mysql
ARG DB_PORT=3306
ARG SPRING_PROFILES_ACTIVE
ARG API_VERSION

ENV DB_NAME=${DB_NAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_HOST=${DB_HOST}
ENV DB_PORT=${DB_PORT}
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ENV API_VERSION=${API_VERSION}

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

#### Exemplo:

1. Dockerfile:
```yaml
FROM openjdk:17-jdk-alpine

ARG DB_NAME
ARG DB_USERNAME
ARG DB_PASSWORD

ENV DB_NAME=${DB_NAME}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DOCKERIZE_VERSION=v0.7.0

WORKDIR /app

COPY pom.xml .

COPY . .

RUN apk --no-cache add maven

RUN mvn package -DskipTests

EXPOSE 8080

RUN apk update --no-cache \
&& apk add --no-cache wget openssl \
  && wget -O - https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz | tar xzf - -C /usr/local/bin \
  && apk del wget

```

2. Compose:

```yaml
services:
  database:
    image: "mysql:8.2.0-oraclelinux8"
    container_name: conectar_doacoes_mysql_db
    environment:
      - MYSQL_DATABASE=${DB_NAME}
      - MYSQL_ROOT_PASSWORD=${DB_PASSWORD}
    ports:
      - "3307:3306"
    volumes:
      - db-mysql-conectar-doacoes:/var/lib/mysql

  conectar-doacoes-app:
    image: diegoneves/conectar-doacoes:latest
    container_name: conectar_doacoes_api
    ports:
      - "8080:8080"
      - "8081:5005"
    depends_on:
      - database
    environment:
      - DB_HOST=database
      - DB_PORT=3306
    entrypoint: sh -c "dockerize -wait tcp://$${DB_HOST}:$${DB_PORT} -timeout 20s && java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar target/conectar-doacoes.jar"

volumes:
  db-mysql-conectar-doacoes:

```

