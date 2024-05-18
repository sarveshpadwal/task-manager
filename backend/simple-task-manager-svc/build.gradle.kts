plugins {
	java
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.springdoc.openapi-gradle-plugin") version "1.8.0"

	application
	id("checkstyle")
	id("jacoco")
}

group = "com.sp.simpletaskmanager"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val modelMapperVersion: String by extra("3.2.0")
val esapiVersion: String by extra("2.5.3.1")
val antisamyVersion: String by extra("1.7.5")
val logstashLogbackEncoderVersion: String by extra("7.2")
val springdocOpenapiStarterWebmvcUiVersion: String by extra("2.5.0")
val testContainerVersion: String by extra("1.19.7")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.liquibase:liquibase-core")
	implementation("org.modelmapper:modelmapper:$modelMapperVersion")
	implementation("org.owasp.esapi:esapi:$esapiVersion:jakarta") {
		exclude(group = "org.owasp.antisamy", module = "antisamy")
	}
	implementation("org.owasp.antisamy:antisamy:$antisamyVersion")
	implementation("io.micrometer:micrometer-tracing-bridge-brave")
	implementation("io.zipkin.reporter2:zipkin-reporter-brave")
	implementation("net.logstash.logback:logstash-logback-encoder:$logstashLogbackEncoderVersion")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenapiStarterWebmvcUiVersion")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:postgresql:$testContainerVersion")
	testImplementation("org.testcontainers:junit-jupiter:$testContainerVersion")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
	mainClass.set("com.sp.simpletaskmanager.SimpleTaskManagerSvcApplication")
}

springBoot {
	buildInfo()
}

jacoco {
	applyTo(tasks.run.get())
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
	reports {
		xml.required = false
		csv.required = false
		html.required = true
	}
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				minimum = "0.9".toBigDecimal()
			}
		}

		rule {
			isEnabled = false
			element = "CLASS"
			includes = listOf("com.sp.simpletaskmanager.*")

			limit {
				counter = "LINE"
				value = "TOTALCOUNT"
				maximum = "0.3".toBigDecimal()
			}
		}
	}
}

checkstyle {
	configProperties["checkstyleDir"] = file("config/checkstyle").absolutePath
}
