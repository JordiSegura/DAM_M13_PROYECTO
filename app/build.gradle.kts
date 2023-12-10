plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.dam_m13_proyecto"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.dam_m13_proyecto"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    configurations.all {
        resolutionStrategy {
            force( "com.google.android.gms:play-services-location:15.0.1");
        }
    }
}

dependencies {

    //noinspection GradleCompatible
    implementation ("com.google.android.material:material:1.8.0")
    implementation (files("libs\\mysql-connector-java-5.1.4-bin.jar"))
    implementation ("com.google.android.gms:play-services-location:15.0.1")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
}