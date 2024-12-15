import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    idea
    java
    id("io.freefair.lombok") version ("8.11")
    id("io.qameta.allure") version ("2.12.0")
}

idea {
    project {
        languageLevel = IdeaLanguageLevel(17)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

allure {
    report {
        version.set("2.29.0")
    }
    adapter {
        aspectjWeaver.set(true)
        frameworks {
            junit5 {
                adapterVersion.set("2.29.0")
            }
        }
    }
}

group = "guru.qa"
repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("io.rest-assured:rest-assured:5.5.0")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testImplementation("ch.qos.logback:logback-classic:1.5.6")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.18.1")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")
    testImplementation("io.qameta.allure:allure-rest-assured:2.29.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    systemProperties(
        System.getProperties()
            .mapKeys { it.key.toString() }
            .mapValues { it.value?.toString() ?: "" }
    )
    useJUnitPlatform {
        System.getProperty("includeTags")?.let {
            includeTags(it)
        }
    }

    testLogging {
        lifecycle {
            events = setOf(
                TestLogEvent.STARTED, TestLogEvent.SKIPPED, TestLogEvent.FAILED,
                TestLogEvent.STANDARD_ERROR, TestLogEvent.STANDARD_OUT
            )
            exceptionFormat = TestExceptionFormat.SHORT
        }
    }
}