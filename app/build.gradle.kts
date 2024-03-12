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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
//    implementation("org.slf4j:slf4j-api:2.0.11")
//    implementation("org.slf4j:slf4j-simple:1.7.30")
//    implementation("org.apache.logging.log4j:log4j-api:2.22.1")
//    implementation("org.apache.logging.log4j:log4j-core:2.22.1")
//    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.22.1")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("org.mapstruct:mapstruct:1.6.0.Beta1")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0.Beta1")
    implementation("org.projectlombok:lombok:1.18.30")
    implementation("com.github.GoodieBag:Pinview:v1.4")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}