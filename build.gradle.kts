plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    id("io.quarkus")
    id("org.openapi.generator") version "5.4.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
val quarkusAdocPoTranslatorVersion: String by project

dependencies {
    implementation("org.fedorahosted.tennera:jgettext:0.15.1")
    implementation("org.asciidoctor:asciidoctorj:2.5.3")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation(platform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkus:quarkus-picocli")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-resteasy")


    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.assertj:assertj-core")
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

tasks.withType<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>{
    this.generatorName.set("kotlin")
    this.inputSpec.set("$rootDir/src/main/resources/globalese.openapi.yml")
    this.outputDir.set("$buildDir/generated/openapi")
    this.apiPackage.set("net.sharplab.translator.generated.api")
    this.invokerPackage.set("net.sharplab.translator.generated.invoker")
    this.modelPackage.set("net.sharplab.translator.generated.model")
    this.configOptions.set(mapOf("dateLibrary" to "java8"))
}
