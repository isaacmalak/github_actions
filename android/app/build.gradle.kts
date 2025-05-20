import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("kotlin-android")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")
}

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

val myKeyAlias = keystoreProperties["keyAlias"] ?: System.getenv("KEY_ALIAS")
val myKeyPassword = keystoreProperties["keyPassword"] ?: System.getenv("KEY_PASSWORD")
val myStoreFile = (keystoreProperties["storeFile"] ?: System.getenv("STORE_FILE"))?.let { file(it) } 
val myStorePassword = keystoreProperties["storePassword"] ?: System.getenv("STORE_PASSWORD")

if (myKeyAlias == null || myKeyPassword == null || myStoreFile == null || myStorePassword == null) {
    throw NullPointerException("Signing configuration is missing\nPlease check key.properties file or environment variables")
}

android {
    namespace = "com.example.flutter_application_1"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = flutter.ndkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

     signingConfigs {
        create("release") {
            storeFile = myStoreFile as File
            storePassword = myStorePassword as String
            keyAlias = myKeyAlias as String
            keyPassword = myKeyPassword as String
        }
    }
    defaultConfig {
        // TODO: Specify your own unique Application ID (https://developer.android.com/studio/build/application-id.html).
        applicationId = "com.example.flutter_application_1"
        // You can update the following values to match your application needs.
        // For more information, see: https://flutter.dev/to/review-gradle-config.
            minSdk = flutter.minSdkVersion
            targetSdk = flutter.targetSdkVersion
            versionCode = flutter.versionCode
            versionName = flutter.versionName
        }

        buildTypes {
            release {
                // TODO: Add your own signing config for the release build.
                // Signing with the debug keys for now, so `flutter run --release` works.
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }

    flutter {
        source = "../.."
    }
