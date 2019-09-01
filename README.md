# TheMovies
![license](https://img.shields.io/badge/license-MIT%20License-blue.svg)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Build Status](https://travis-ci.org/skydoves/TheMovies2.svg?branch=master)](https://travis-ci.org/skydoves/TheMovies2)

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
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct database.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- Material Design & Animations
- The Movie DB API
- [Retrofit2 & Gson](https://github.com/square/retrofit) - constructing the REST API
- [OkHttp3](https://github.com/square/okhttp) - implementing interceptor, logging and mocking web server
- [Glide](https://github.com/bumptech/glide) - loading images
- [BaseRecyclerViewAdapter](https://github.com/skydoves/BaseRecyclerViewAdapter) - implementing adapters and viewHolders
- [Mockito-kotlin](https://github.com/nhaarman/mockito-kotlin) - Junit mock test
- [Timber](https://github.com/JakeWharton/timber) - logging
- [Stetho](https://github.com/facebook/stetho) - debugging persistence data & network packets
- Ripple animation, Shared element transition
- Custom Views [AndroidTagView](https://github.com/whilu/AndroidTagView), [ExpandableTextView](https://github.com/Manabu-GT/ExpandableTextView)

## Find this library useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/TheMovies/stargazers2)__ for this repository. :star:

## Supports :coffee:
If you feel like support me a coffee for my efforts, I would greatly appreciate it. <br><br>
<a href="https://www.buymeacoffee.com/skydoves" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/purple_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

# License
```xml
The MIT License (MIT)

Designed and developed by 2019 skydoves (Jaewoong Eum)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
