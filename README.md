# TheMovies2
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Build Status](https://travis-ci.org/skydoves/TheMovies2.svg?branch=master)](https://travis-ci.org/skydoves/TheMovies2)
<a href="https://github.com/skydoves"><img alt="License" src="https://img.shields.io/static/v1?label=GitHub&message=skydoves&color=C51162"/></a>

A simple project using [The Movie DB](https://www.themoviedb.org) based on Kotlin MVVM architecture and material designs & animations.<br>

![preview0](https://user-images.githubusercontent.com/24237865/64071378-4082bc00-ccb4-11e9-8ceb-56e52c223ac8.gif)
![preview1](https://user-images.githubusercontent.com/24237865/64071373-e41f9c80-ccb3-11e9-996b-888b5bf9877d.gif)

## How to build on your environment
Add your [The Movie DB](https://www.themoviedb.org)'s API key in your `local.properties` file.
```xml
tmdb_api_key=YOUR_API_KEY
```

## Tech stack & Open-source libraries
- Minimum SDK level 16
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- JetPack
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct database.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
  - Hilt - dependency injection
  - DataBinding with [Bindables](https://github.com/skydoves/bindables) - Android DataBinding kit for notifying data changes to UI layers.
- Material Design & Animations
- [Retrofit2 & Gson](https://github.com/square/retrofit) - constructing the REST API
- [OkHttp3](https://github.com/square/okhttp) - implementing interceptor, logging and mocking web server
- [Sandwich](https://github.com/skydoves/Sandwich) - constructing lightweight API response and handling error responses
- [Glide](https://github.com/bumptech/glide) - loading images
- [BaseRecyclerViewAdapter](https://github.com/skydoves/BaseRecyclerViewAdapter) - implementing adapters and viewHolders
- [WhatIf](https://github.com/skydoves/whatif) - checking nullable object and empty collections more fluently
- [Bundler](https://github.com/skydoves/bundler) - Android Intent & Bundle extensions that insert and retrieve values elegantly.
- [Mockito-kotlin](https://github.com/nhaarman/mockito-kotlin) - Junit mock test
- [Timber](https://github.com/JakeWharton/timber) - logging
- [Stetho](https://github.com/facebook/stetho) - debugging persistence data & network packets
- Ripple animation, Shared element transition
- Custom Views [AndroidTagView](https://github.com/whilu/AndroidTagView), [ExpandableTextView](https://github.com/Manabu-GT/ExpandableTextView)

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/TheMovies2/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/skydoves)__ me for my next creations! 🤩

## Supports :coffee:
If you feel like support me a coffee for my efforts, I would greatly appreciate it. <br><br>
<a href="https://www.buymeacoffee.com/skydoves" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/purple_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

# License
```xml
Designed and developed by 2019 skydoves (Jaewoong Eum)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
