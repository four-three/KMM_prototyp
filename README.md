# KMM Prototyp

Dieses Projekt dient als Prototyp für die Erforschung des Kotlin Multiplatform Mobile (KMM) Frameworks und Compose Multiplattform.

## Über das Projekt

Dieses Projekt wurde als Teil meines Masterprojekts an der Fachhochschule Erfurt erstellt. Ziel war es, einige grundlegende Funktionen von KMM und Compose Multiplatform exemplarisch umzusetzen und zu evaluieren.

Folgende Funktionen sind im Prototyp enthalten:

- Shared Kotlin Code für Android und iOS
- SQLite Datenbank mit SQLDelight
- Ktor für Netzwerkanfragen
- Compose UI für Android
- SwiftUI für iOS

## Getting Started

Folgende Schritte sind notwendig, um das Projekt lokal auszuführen:

1. Repository klonen
2. Android Studio öffnen und das Projekt importieren
3. Abhängigkeiten mit Gradle synchronisieren
4. Android App starten
5. iOS App in Xcode öffnen und starten

## Architektur

Das Projekt nutzt folgende Architektur:

- Shared Kotlin Code in `shared/src`
- Plattformspezifischer Code unter `androidApp` und `iosApp`
- Dependency Injection mit Koin
- MVVM Architektur
- Repository Pattern für Datenbank und Netzwerkzugriffe

## Fazit

Mit diesem Prototyp konnte ich einen ersten Einblick in KMM und Compose Multiplatform erhalten. Die grundlegenden Konzepte sind umgesetzt und die Apps funktionieren auf Android und iOS. Für ein reales Projekt gibt es jedoch noch viel Raum für Verbesserungen und Erweiterungen. Insgesamt bin ich aber positiv überrascht, wie produktiv man mit KMM arbeiten kann.
