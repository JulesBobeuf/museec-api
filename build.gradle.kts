plugins {
	java
	war
	jacoco
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
    maven {
        url = uri("https://repo.spring.io/release")
    }
    maven {
        url = uri("https://repository.jboss.org/maven2")
    }
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

springBoot {
    mainClass.set( "fr.univartois.butinfo.s5a01.musicmatcher.MusicMatcherApplication")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sonar {
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

System.setProperty("http.proxyHost", "http://cache-iutl.univ-artois.fr")
System.setProperty("http.proxyPort", "3128")
System.setProperty("https.proxyHost", "http://cache-iutl.univ-artois.fr")
System.setProperty("https.proxyPort", "3128")




