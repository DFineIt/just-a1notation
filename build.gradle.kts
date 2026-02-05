plugins {
    id("java")
    id("signing")
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "cloud.fine-it"
version = "1.0.3"

repositories {
    mavenCentral()
}

signing {
    useGpgCmd()
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(11)
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    pom {
        name.set("just-a1notation")
        description.set("Lightweight Java library for parsing and formatting spreadsheet addresses in A1 notation.")
        url.set("https://github.com/DFineIt/just-a1notation")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("DFineIt")
                name.set("Fine-It Cloud")
                email.set("info@fine-it.cloud")
            }
        }

        scm {
            url.set("https://github.com/DFineIt/just-a1notation")
            connection.set("scm:git:https://github.com/DFineIt/just-a1notation")
            developerConnection.set("scm:git:ssh://git@github.com/DFineIt/just-a1notation.git")
        }
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
