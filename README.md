# OnTheBench

>An Android app to follow your favourite NHL teams and players, 
>complete with up-to-date rosters, games, and standings. Created with 
>Kotlin and Jetpack Compose.

---

## Features

- User authentication using Firebase Authentication
- Daily NHL game scores
- Current NHL standings
- Up-to-date NHL rosters
- Ability to save favourite players/teams to cloud storage
- **Player Cards** displaying a given player's statistics
- **Team Cards** displaying up-to-date team statistics

## Tech Stack

- **UI**: Jetpack Compose & Kotlin
- **Authentication**: Firebase Authentication
- **API**:
    - Retrofit for API calls to the [NHL API](https://github.com/Zmalski/NHL-API-Reference?tab=readme-ov-file)
    - Moshi for JSON serialization/deserialization
- **Data Storage**:
    - Room for local data caching and performance optimization
    - Firebase Firestore for storing favorite players and teams
- **Image Loading**: Coil

## App Structure

```
app/src/main/kotlin/com/cgadoury/onthebench/
├── api/                    # NHL API service and endpoints
├── db/                     # Room database setup
├── destinations/           # Navigation destinations
├── mvvm/                   # ViewModels and state management
├── navigation/             # Navigation configuration
├── repository/             # Data repositories
├── screens/                # Compose UI screens
├── ui/                     # Reusable UI components
├── utility/                # Helper functions and utilities
├── MainActivity.kt
└── SignInActivity.kt
```