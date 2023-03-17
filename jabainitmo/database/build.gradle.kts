/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("jabainitmo.java-application-conventions")
    id("io.freefair.lombok") version "6.6.2"
}

dependencies {
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("org.hibernate:hibernate-core:6.1.7.Final")
    implementation("org.mybatis:mybatis:3.5.13")
}

application {
    // Define the main class for the application.
    mainClass.set("jabainitmo.database.App")
}
