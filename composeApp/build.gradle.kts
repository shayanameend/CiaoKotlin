import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
  alias(libs.plugins.multiplatform)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.compose)
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.sqlDelight)
  alias(libs.plugins.apollo)
  alias(libs.plugins.buildConfig)
}

kotlin {
  jvmToolchain(17)

  androidTarget {
    // https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
  }

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "ComposeApp"
      isStatic = true
    }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.kermit)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.ktor.client.core)
      implementation(libs.ktor.client.content.negotiation)
      implementation(libs.ktor.client.serialization)
      implementation(libs.ktor.client.logging)
      implementation(libs.androidx.lifecycle.viewmodel)
      implementation(libs.androidx.lifecycle.runtime.compose)
      implementation(libs.androidx.navigation.composee)
      implementation(libs.kotlinx.serialization.json)
      implementation(libs.koin.core)
      implementation(libs.koin.compose)
      implementation(libs.coil)
      implementation(libs.coil.network.ktor)
      implementation(libs.multiplatformSettings)
      implementation(libs.kotlinx.datetime)
      implementation(libs.apollo.runtime)
      implementation(libs.kstore)
      implementation(libs.composeIcons.featherIcons)
    }

    commonTest.dependencies {
      implementation(kotlin("test"))
      @OptIn(ExperimentalComposeLibrary::class)
      implementation(compose.uiTest)
      implementation(libs.kotlinx.coroutines.test)
    }

    androidMain.dependencies {
      implementation(compose.uiTooling)
      implementation(libs.androidx.activityCompose)
      implementation(libs.kotlinx.coroutines.android)
      implementation(libs.ktor.client.okhttp)
      implementation(libs.sqlDelight.driver.android)
    }

    iosMain.dependencies {
      implementation(libs.ktor.client.darwin)
      implementation(libs.sqlDelight.driver.native)
    }

  }
}

android {
  namespace = "com.zedsols.ciao_kotlin"
  compileSdk = 35

  defaultConfig {
    minSdk = 21
    targetSdk = 35

    applicationId = "com.zedsols.ciao_kotlin.androidApp"
    versionCode = 1
    versionName = "1.0.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

// https://developer.android.com/develop/ui/compose/testing#setup
dependencies {
  androidTestImplementation(libs.androidx.uitest.junit4)
  debugImplementation(libs.androidx.uitest.testManifest)
}

buildConfig {
  // BuildConfig configuration here.
  // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

sqldelight {
  databases {
    create("MyDatabase") {
      // Database configuration here.
      // https://cashapp.github.io/sqldelight
      packageName.set("com.zedsols.ciao_kotlin.db")
    }
  }
}

apollo {
  service("api") {
    // GraphQL configuration here.
    // https://www.apollographql.com/docs/kotlin/advanced/plugin-configuration/
    packageName.set("com.zedsols.ciao_kotlin.graphql")
  }
}