plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.myfinances"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myfinances"
        minSdk = 21
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
//    implementation("org.slf4j:slf4j-api:2.0.11")
//    implementation("org.slf4j:slf4j-simple:1.7.30")
//    implementation("org.apache.logging.log4j:log4j-api:2.22.1")
//    implementation("org.apache.logging.log4j:log4j-core:2.22.1")
//    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.22.1")
//    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("com.jakewharton.timber:timber:4.7.1")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.projectlombok:lombok:1.18.30")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}