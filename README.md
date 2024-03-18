# KMM Prototype

This project serves as a prototype for exploring the Kotlin Multiplatform Mobile (KMM) framework and Compose Multiplatform.

## About the Project

This project was created as part of my master's project at the University of Applied Sciences Erfurt. 
The goal was to implement and evaluate some basic functionalities of KMM and Compose Multiplatform exemplarily.

The prototype includes the following functionalities:

- Shared Kotlin code for Android and iOS
- SQLite database with SQLDelight
- Ktor for network requests
- Compose UI for Android
- SwiftUI for iOS

## Getting Started

The following steps are necessary to run the project locally:

1. Clone the repository
2. Open Android Studio and import the project
3. Synchronize dependencies with Gradle
4. Start the Android app
5. Open and start the iOS app in Xcode

## Architecture

The project utilizes the following architecture:

- Shared Kotlin code in `shared/src`
- Platform-specific code under `androidApp` and `iosApp`
- Dependency injection with Koin
- MVVM architecture
- Repository pattern for database and network access

## Conclusion

With this prototype, I was able to gain an initial insight into KMM and Compose Multiplatform. 
The basic concepts have been implemented, and the apps work on Android and iOS. However, 
there is still much room for improvements and extensions for a real project. Overall, 
I am positively surprised by how productive one can be with KMM.

Please note that certain features such as camera capturing on iOS and location saving on both devices are currently not working. 
This will need to be addressed in future updates or iterations of the project.