plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.security:spring-security-crypto:6.2.2")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.2.5")
	implementation("org.flywaydb:flyway-core")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")
	implementation("org.apache.commons:commons-lang3:3.14.0")
	implementation("org.apache.commons:commons-collections4:4.4")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	implementation("com.auth0:java-jwt:4.4.0")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")

	compileOnly("org.projectlombok:lombok")

	implementation("org.postgresql:postgresql")

	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")

	testCompileOnly("org.projectlombok:lombok")

	testAnnotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("org.springframework.security:spring-security-test")

	testImplementation("io.rest-assured:rest-assured:5.4.0")
	testImplementation("org.assertj:assertj-core:3.25.0")
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")


}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
	inputs.dir(project.extra["snippetsDir"]!!)
	dependsOn(tasks.test)
}
