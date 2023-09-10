plugins {
    id("java")
}

tasks.jar {
    manifest {
        attributes(mapOf("Main-Class" to "com.vinilemess.bigdecimalexpressioncalculator.ExpressionCalculatorApplication"))
    }

    from(
        configurations.runtimeClasspath.get().map {
            if (it.isDirectory) {
                it
            } else {
                zipTree(it)
            }
        }
    )
}

group = "com.vinilemess.bigdecimalexpressioncalculator"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks.test {
    useJUnitPlatform()
}