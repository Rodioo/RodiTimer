import org.jetbrains.compose.desktop.application.dsl.TargetFormat

// KMM Compose version which is compatible with Kotlin 1.8.22
// https://developer.android.com/jetpack/androidx/releases/compose-kotlin#pre-release_kotlin_compatibility
compose {
    kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:1.4.8")
}

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.5.10-beta01"
    id("app.cash.sqldelight") version "2.0.0"
}

group = "com.tonio.rodi_timer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

    //Google Icons
    implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.4.3")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")

    //SQLDelight
    implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
    
    //Material3
    implementation("org.jetbrains.compose.material3:material3:1.5.10-beta01")

    implementation("com.godaddy.android.colorpicker:compose-color-picker-jvm:0.7.0")

}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.tonio.database")
        }
    }
}

compose.desktop {
    application {
        mainClass = "ui.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

            //Module needed for SQLDelight compatibility with Compose Desktop
            modules("java.sql")

            packageName = "RodiTimer"
            packageVersion = "1.0.0"
        }
    }
}
