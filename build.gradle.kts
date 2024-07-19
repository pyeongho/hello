plugins {
    `java-library`
    `maven-publish`
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    group = "com.pyeongho.hello"
    version = "1.0.1"

    configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/pyeongho/hello")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
                }
            }
        }
        publications {
            register<MavenPublication>("gpr") {
                from(components["java"])
            }
        }
    }

    // jar task - manifest 파일에 라이브러리 이름과 버전을 포함  
    tasks.jar {
        manifest {
            attributes(mapOf("Implementation-Title" to project.name,
                "Implementation-Version" to project.version))
        }
    }
}
