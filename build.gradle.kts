configurations {
    create("dev")
    create("prod")
}

plugins {
	java
	jacoco
	war
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.sonarqube") version "4.3.1.3277"
	id("com.diffplug.eclipse.apt") version "3.26.0"
}

group = "fr.univartois.butinfo.s5a01"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	compileOnly("org.projectlombok:lombok:1.18.30")
}

tasks.register<Copy>("copyProperties") {
    from("src/main/resources/${project.findProperty("env") ?: "dev"}")
    into("build/resources/main")
}

tasks.named("bootJar") {
    dependsOn("copyProperties")
}

tasks.named("war") {
    dependsOn("copyProperties")
}

tasks.getByName("compileTestJava") {
    dependsOn(":copyProperties")
}

tasks.named("resolveMainClassName") {
    dependsOn("copyProperties")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

sonarqube {
    properties {
        property("sonar.projectKey", "sae5")
        property("sonar.host.url", "https://sonarqube.univ-artois.fr")
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required = true
        csv.required = true
    }
}