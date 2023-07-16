import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
    kotlin("plugin.jpa") version "1.8.21"
    kotlin("plugin.allopen") version "1.6.21"
    kotlin("plugin.noarg") version "1.6.21"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.0"
    id("jacoco")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
// 	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
// 	runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
// 	implementation("org.modelmapper:modelmapper:2.4.2")
    implementation("mysql:mysql-connector-java:8.0.33")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

allOpen {
    annotation("javax.persistence.Entity")
}

noArg {
    annotation("javax.persistence.Entity")
}

val installLocalGitHook = tasks.register<Copy>("installLocalGitHook") {
    from("${rootProject.rootDir}/scripts/git-hooks")
    into(File("${rootProject.rootDir}/.git/hooks"))

    eachFile {
        mode = "755".toInt(radix = 8)
    }
}

tasks.build {
    dependsOn(installLocalGitHook)
}

subprojects {
    apply(plugin = "jacoco")

    jacoco {
        toolVersion = "0.8.10"
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("${rootProject.rootDir}/jacocoReport"))
    }

    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {

    val Qdomains = mutableListOf<String>()

    for (qPattern in 'A'..'Z') {
        Qdomains.add("*.Q$qPattern*")
    }

    violationRules {
        rule {
            element = "CLASS"

            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                // minimum = "0.8".toBigDecimal()
            }

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                // maximum = "0.8".toBigDecimal()
            }

            // 빈 줄을 제외한 코드의 라인수를 최대 200라인으로 제한합니다.
            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "200".toBigDecimal()
            }

            excludes = Qdomains
        }
    }
}
