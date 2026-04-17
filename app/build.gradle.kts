plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.streaktodolistapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.streaktodolistapp"
        minSdk = 23   // You can keep 21 if you want desugaring, or 24+ if you want to raise minSdk
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

        // Enable core library desugaring
        isCoreLibraryDesugaringEnabled = true
    }

    // No kotlinOptions block needed since this is a Java project
}

dependencies {
    // Core AndroidX libraries
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.jakewharton.threetenabp:threetenabp:1.3.1")

    // MaterialCalendarView (AndroidX compatible)
    implementation("com.github.prolificinteractive:material-calendarview:2.0.1")

    // Core library desugaring dependency
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}


