plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.allopen") version "1.3.72"
    id("io.quarkus")
    id("org.openapi.generator") version "5.0.0"
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
    implementation("org.threeten:threetenbp:1.4.0")
    implementation("com.squareup.okhttp3:okhttp")
    implementation("org.fedorahosted.tennera:jgettext:0.15.1")
    implementation("org.asciidoctor:asciidoctorj:2.4.2")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    implementation(platform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("com.squareup.okhttp3:okhttp:4.2.2")
    implementation("com.squareup.moshi:moshi-kotlin:1.9.2")
    implementation("com.squareup.moshi:moshi-adapters:1.9.2")
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
version = "${quarkusAdocPoTranslatorVersion}"

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

//tasks.withType<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>{
//    generatorName = "kotlin"
//    inputSpec = "$rootDir/specs/petstore-v3.0.yaml".toString()
//    outputDir = "$buildDir/generated".toString()
//    apiPackage = "org.openapi.example.api"
//    invokerPackage = "org.openapi.example.invoker"
//    modelPackage = "org.openapi.example.model"
//    configOptions = [
//        dateLibrary: "java8"
//    ]
//}
