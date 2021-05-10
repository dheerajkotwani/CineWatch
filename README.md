![](./Assets/header.png)

# CineWatch 🎥

A minimalistic movie listing app to browse movies using TMDB Api, built to demonstrate MVVM Architecture with latest hot-trending Android development tools including Dependency Injection, Room and many more.

[![License](https://img.shields.io/badge/license-MIT-%2397ca00.svg)](https://github.com/dheerajkotwani/CineWatch/blob/master/LICENSE)
![UI Library shield](https://img.shields.io/badge/LibraryType-UI-blue.svg)
![Sample App shield](https://img.shields.io/badge/App-Sample-green.svg)
![Artifact badge local, or jcenter or clonerepo](https://img.shields.io/badge/Artifact-JCenter-green.svg)
![Minimum API Level](https://img.shields.io/badge/Min%20API%20Level-21-green)
![Maximum API Level](https://img.shields.io/badge/Max%20API%20Level-30-orange)
![GitHub repo size](https://img.shields.io/github/repo-size/dheerajkotwani/CineWatch)

## Features 🚀

- Explore the Upcoming, Popular and Top Rated Movies.
- Get detailed decription of all the movies.
- Get details about the Cast and Crew members.
- Bookmark your favourite movies.
- Get movies trailer within the application.
- Search for movies.

## Built With 🛠

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
  - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The Paging library helps you load and display pages of data from a larger dataset from local storage or over network. 
- [Navigation Graph](https://developer.android.com/guide/navigation/navigation-design-graph) - The Navigation component uses a navigation graph to manage your app's navigation.  
- [Dagger 2](https://dagger.dev/) - Dependency Injection Framework
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Coil](https://github.com/coil-kt/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

## Download 📥
- Download latest APK from [here](https://github.com/dheerajkotwani/CineWatch/releases/download/refs%2Fheads%2Fmaster/app.apk)

## Screenshots 📷 
![light_screenshot](Assets/screen_shot1.png)

## Dark Mode Available 🌙
![dark_screenshots](Assets/screen_shot2.png)

## Architecture 🗼

This project follows the famous MVVM architecture and best practices from Google's 
[GithubBrowserSample](https://github.com/android/architecture-components-samples/tree/master/GithubBrowserSample)
![architecture](Assets/mvvm_architecture.png)

## Project Structure 📂

```
.
├── CineWatch.kt
├── data
|   |
|   ├── api
|   |   ├── NetworkService.kt
|   |   └── SafeApiRequest.kt
│   ├── local
│   │   ├── BookmarkDatabase.kt
│   │   └── dao
│   │     └── BookmarkDao.kt
│   ├── model
│   │   ├── Actor.kt
│   │   ├── Cast.kt
│   │   ├── CastCreditResponse.kt
│   │   ├── Genre.kt
│   │   ├── Movie.kt
│   │   ├── MovieCreditsResponse.kt
│   │   ├── MovieDB.kt
│   │   ├── MovieResponse.kt
│   │   ├── Resourse.kt
│   │   ├── State.kt
│   │   ├── Video.kt
│   │   └── VideoResponse.kt
│   └── repository
│       └── NetworkRepository.kt
├── di
│   └── module
│       ├── ApiModule.kt
│       └── DatabaseModule.kt
├── ui
│   ├── SplashScreenActivity.kt
│   ├── adapter
│   |   ├── BestMoviesRecyclerViewAdapter.kt
│   |   ├── BookmarkRecyclerViewAdapter.kt
│   |   ├── CastRecyclerViewAdapter.kt
│   |   ├── HomeRecyclerViewAdapter.kt
│   |   ├── HomeViewPagerAdapter.kt
│   |   ├── SearchRecyclerViewAdapter.kt
│   |   ├── SimilarMoviesRecyclerViewAdapter.kt 
│   |   └── ViewAllRecyclerViewAdapter.kt
│   ├── details
│   |   ├── ActorDetailsFragment.kt
│   |   ├── ActorDetailsViewModel.kt
│   |   ├── MovieDetailsFragment.kt
│   |   └── MovieDetailsViewModel.kt
│   ├── dialog
│   |   └── VideoPlayer.kt
│   ├── home
│   |   ├── HomeFragment.kt
│   |   └── HomeViewModel.kt
│   ├── list
│   |   ├── ViewAllFragment.kt
│   |   └── ViewAllViewModel.kt
│   ├── main
│   |   ├── MainActivity.ky
│   |   └── viewpager
│   |       └── HomeViewPagerFragment.kt
│   ├── paging
│   |   ├── PopularPagingSource.kt
│   |   ├── SearchPagingSource.kt
│   |   ├── TopRatedPagingSource.kt
│   |   └── UpcomingPagingSource.kt 
│   └── search
│       ├── SearchFragment.kt
│       └── SearchViewModel.kt
└── utils
    ├── ActivityUtils.kt 
    ├── CONSTANTS.kt
    ├── FragmentUtils.kt
    ├── NetworkUtils.kt
    ├── PlaceHolderUtils.kt
    ├── TimeUtils.kt
    └── ViewUtils.kt
```

## Requirements 🎯 
- Android 5.0 and Above
- Min sdk version 21

## Permissions 💻
- Internet

## Donate 💲
##### If you like the project and wanna support me develop new stuff, You can buy me a coffee.
<a href="https://www.buymeacoffee.com/dheerajkotwani" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-orange.png" alt="Buy Me A Coffee" align='center' height='60'></a>

### If you liked the project don't forget to star 🌟 and fork 🍽 the project.
![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)
![ForTheBadge ANDROID](https://forthebadge.com/images/badges/built-for-android.svg)
![ForTheBadge GIT](https://forthebadge.com/images/badges/uses-git.svg)
