# WeatherAppComposeMVP 🌦

Kotlin + Jetpack Compose + **MVP** app that uses **OpenWeather** for current weather, **Firebase Auth** for Sign In / Register, and **Room** for local weather history. Two tabs: **Current** and **History**.

## Features
- Email/Password Registration & Sign In (Firebase Authentication)
- Current weather tab shows:
  - City & Country
  - Current temperature (°C)
  - Sunrise & Sunset times
  - Weather icon:
    - Sun if sunny before 6 PM
    - Moon if sunny after 6 PM
    - Umbrella if raining/drizzle/thunderstorm
- History tab shows an entry **every time the app opens** (first composition triggers a fetch & save).

## Notes
- **Location permissions** will be requested on first launch. If location isn't available, app falls back to **Manila**.
- The app loads current weather on startup and saves it to Room → visible in the **History** tab.
- If it's after **6:00 PM** local time, a **moon** icon shows for clear skies.
- Current weather endpoint used:
  `https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API_KEY}&units=metric`
- Replace weather_api_key from `res/string` 

## Tech

- Jetpack Compose (Material 3) - It introduces a comprehensive set of design principles and components that prioritize personalization, expressiveness, and adaptability.
- MVP (simple presenters for Auth & Weather) -  (Model-View-Presenter) is a UI-focused architectural pattern that promotes separation of concerns by dividing an application into three parts: the Model (data and business logic), the View (user interface), and the Presenter (the mediator between the Model and View). 
- Retrofit + Gson - to simplify network requests and data serialization/deserialization.
- Room - is an Android Jetpack library that provides a convenient, abstraction layer over SQLite to enable offline data storage and access, acting as a persistent, local database for caching data. It simplifies database operations using annotations to reduce boilerplate code, provides compile-time checks for database queries, and supports reactive programming with features like LiveData and Flow, allowing apps to display data even when offline and sync changes when a network connection is restored. 
- Coroutines - provide a framework for asynchronous programming, allowing the creation of non-blocking, concurrent code that simplifies handling long-running operations like network requests or database interactions.
- Firebase Auth - provides backend services, easy-to-use SDKs, and ready-made UI libraries to authenticate users to your app.
- Google Play Services Location -  provide location-based functionalities to Android devices and applications.

<img width="200" height="400" alt="History" src="https://github.com/user-attachments/assets/98832433-e84d-4475-bc94-c30c674e3329" />
<img width="200" height="400" alt="Register" src="https://github.com/user-attachments/assets/cc74d5f2-9aec-45e2-88a3-26aa74ab2834" />
<img width="200" height="400" alt="Login" src="https://github.com/user-attachments/assets/a812171c-bb59-4bce-9e3c-15c807e2e20d" />
<img width="200" height="400" alt="Home" src="https://github.com/user-attachments/assets/02b39e3a-cd19-4776-92d1-ddfc2a06549e" />
