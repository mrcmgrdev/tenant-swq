plugins {
  java
  id("org.springframework.boot") version "4.0.4"
  id("io.spring.dependency-management") version "1.1.7"
  id("com.diffplug.spotless") version "8.4.0"
  id("com.github.spotbugs") version "6.4.8"
}

group = "com.example"

version = "0.0.1-SNAPSHOT"

description = "fh3"

java { toolchain { languageVersion = JavaLanguageVersion.of(25) } }

repositories { mavenCentral() }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-webmvc")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  developmentOnly("org.springframework.boot:spring-boot-docker-compose")
  runtimeOnly("org.postgresql:postgresql")
  testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
  testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
  testImplementation("com.tngtech.archunit:archunit-junit5:1.4.1")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> { useJUnitPlatform() }

spotless {
  java { googleJavaFormat("1.35.0") }
  kotlinGradle { ktfmt() }
  yaml {
    target("**/*.yaml", "**/*.yml")
    jackson()
  }
  format("markdown") {
    target("**/*.md")
    endWithNewline()
    trimTrailingWhitespace()
    leadingTabsToSpaces(2)
  }
  format("misc") {
    target(".gitignore", ".gitattributes")
    endWithNewline()
    trimTrailingWhitespace()
    leadingTabsToSpaces(2)
  }
}
