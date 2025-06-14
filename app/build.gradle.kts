plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")

}

android {
    namespace = "edu.polytech.concertcare"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.polytech.concertcare"
        minSdk = 24
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
    }
}

dependencies {
    // Dépendances via le catalog libs.versions.toml
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.messaging)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Dépendances directes (non cataloguées)
    implementation("androidx.preference:preference:1.1.0")
    implementation("org.osmdroid:osmdroid-android:6.0.2")
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("com.squareup.retrofit2:converter-jackson:2.7.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.3")
    implementation("com.fasterxml.jackson.core:jackson-core:2.10.3")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.10.3")
    implementation("com.google.android.gms:play-services-maps:17.0.0")
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("androidx.palette:palette:1.0.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")


}
