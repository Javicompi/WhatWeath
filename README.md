# WhatWeath
WhatWeath is a small weather Android app focused on Jetpack Compose using [OpenWeatherMap API](https://openweathermap.org/api). The app uses Clean Architecture, SOLID principles and MVVM with repository pattern. Single Activity, no Fragments.

![alt text](https://github.com/Javicompi/WhatWeath/blob/master/previews/Presentation.png "Preview")

The app shows a main screen with a bottom navigation bar and drawer. Bottom bar has 3 screens. First for current and next 24 hours weather, second for next days weather, and third to search for a location. The drawer shows a list of all the entries saved, showing a small peak of the current weather, and from which user can select the location to view in detail. From the top app bar is possible to change the units of measurement (standard, metric and imperial).

Current and Search screens are similar and show a bottomsheet with current and next hours weather. Week screen is a list of the current and next 7 days weather. These items are expandable to show the details.

The Current weather is scheduled to update every hour at least. And 24 hours and 7 days on demand, when the user navigates to Current or Week screens.

### How to build the app
Add your [OpenWeatherMapp API Key](https://home.openweathermap.org/api_keys) in gradle.properties.

```
API_KEY="your_api_key"
```

### Tech stack & libraries
* Kotlin based, [Flows](https://kotlinlang.org/docs/flow.html) and [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for asynchronous work.

<img align="right" width="180" height="360" src="https://github.com/Javicompi/WhatWeath/blob/master/previews/video_preview.gif">

* Android Jetpack:
  
  -[Compose](https://developer.android.com/jetpack/compose) for building the UI.
  
  -[Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection.
  
  -[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) for UI state holder and lifecycle aware.
  
  -[Room](https://developer.android.com/training/data-storage/room) for local persistence.
  
  -[WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) for background asynchronous scheduled tasks.
  
  -[Datastore](https://developer.android.com/topic/libraries/architecture/datastore) for settings persistence.
  
  -[Navigation](https://developer.android.com/jetpack/compose/navigation) for handling navigation within the app.
  
* [Retrofit](https://square.github.io/retrofit/) for consuming REST API.
* [Moshi](https://github.com/square/moshi) for JSON parsing.
* [NetworkResponseAdapter](https://github.com/haroldadmin/NetworkResponseAdapter) for handling API responses.

### Architecture

![alt text](https://github.com/Javicompi/WhatWeath/blob/master/previews/Architecture.png "Architecture")
