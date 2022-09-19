plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    id("io.quarkus") version "2.9.2.Final"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusAdocPoTranslatorVersion: String by project
val deepl4jVersion = "0.1.2.RELEASE"
val jgettextVersion = "0.15.1"
val asciidoctorjVersion = "2.5.6"
val jsoupVersion = "1.15.1"

dependencies {
    implementation(platform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:2.8.2.Final"))
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkus:quarkus-picocli")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-resteasy")

    implementation("com.github.sharplab:deepl4j:${deepl4jVersion}"){
        exclude("jakarta.annotation", "jakarta.annotation-api")
    }
    implementation("org.fedorahosted.tennera:jgettext:${jgettextVersion}")
    implementation("org.asciidoctor:asciidoctorj:${asciidoctorjVersion}")
    implementation("org.jsoup:jsoup:${jsoupVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

group = "net.sharplab.translator"
version = quarkusAdocPoTranslatorVersion

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

quarkus {
    setFinalName("quarkus-adoc-po-translator")
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}