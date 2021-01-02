# quarkus-adoc-po-translator

[![Actions Status](https://github.com/i18n-quarkusio/quarkus-adoc-po-translator/workflows/CI/badge.svg)](https://github.com/i18n-quarkusio/quarkus-adoc-po-translator/actions)

quarkus-adoc-po-translator is an utility to translate .adoc.po files extracted from quarkus documentations asciidoctor files by po4a.

This project uses Quarkus, the Supersonic Subatomic Java Framework.
If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Build

### Prerequisites

- JDK 11
- GraalVM (If you want to build a native image directly)
- Docker or Podman (If you want to build a native image in a container)

### Creating an uber-jar


The application can be packaged using following command:

```
./gradlew quarkusBuild --uber-jar
```

It produces the `quarkus-adoc-po-translator-runner.jar` file in the `build` directory.

### Creating a native executable

You can also create a native executable in this way:
```
 ./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using following command:
```
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

It produces the `quarkus-adoc-po-translator-runner` file in the `build` directory.

## Configuration

place application.yml to `<quarkus-adoc-po-translator working directory>/config/application.yml`

#### application.yml

```
translator:
  deepL:
    apiKey: <put your api key here>
  language:
    source: en        # default source language
    destination: ja   # default destination language
```

## Execution

uber-jar

```
java -jar quarkus-adoc-po-translator.jar --path <path to source po file> \
[--srcLang <source language>] [--dstLang <destination language>]
```

executable

```
./quarkus-adoc-po-translator-runner --path <path to source epub file> \
[--srcLang <source language>] [--dstLang <destination language>]
```
