# WhatWeath
Compose WhatWeath is an small weather Android app focused on Jetpack Compose using OpenWeatherMap API. The app uses Clean Architecture, SOLID and MVVM with repository pattern. Single Activity, no Fragments.

![alt text](https://github.com/Javicompi/WhatWeath/blob/master/previews/Presentation.png "Preview")

### How to build the app
Add your [OpenWeatherMapp API Key](https://home.openweathermap.org/api_keys) in gradle.properties.

```
API_KEY="your_api_key"
```

### Tech stack & libraries
* Kotlin based, [Flows](https://kotlinlang.org/docs/flow.html) and [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for asynchronous work.
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
