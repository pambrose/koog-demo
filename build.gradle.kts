plugins {
  kotlin("jvm") version "2.2.0"
}

group = "com.github.pambrose.common-utils"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation(libs.oshai.kotlin.logging)
  implementation("ai.koog:koog-agents:0.3.0")

  runtimeOnly(libs.slf4j.simple)
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}
kotlin {
  jvmToolchain(17)
}