# SmarTech-Assignment

## Background
This is an android app which displaying repos of Jake Wharton in just a recycler view, with pagination.

## Screenshots
<img src="https://user-images.githubusercontent.com/5509571/67144088-18a2e400-f273-11e9-9e86-9c6c4a78e608.png" alt="Splash screen" width="250"/> <img src="https://user-images.githubusercontent.com/5509571/67143975-ada4dd80-f271-11e9-94dc-5c816f44e0bc.png" alt="Repos List" width="250"/>
<img src="https://user-images.githubusercontent.com/5509571/67143988-d036f680-f271-11e9-8944-946b60abc09b.png" alt="Offline With cached data" width="250"/> <img src="https://user-images.githubusercontent.com/5509571/67143990-d4631400-f271-11e9-8fdd-8f079b2b9485.png" alt="Offline and cache has no data" width="250"/>
<img src="https://user-images.githubusercontent.com/5509571/67144021-2efc7000-f272-11e9-832b-138218eb0eb7.png" alt="failed to load next page" width="250"/> <img src="https://user-images.githubusercontent.com/5509571/67144020-2efc7000-f272-11e9-9511-62843f3ad43e.png" alt="Loading new page" width="250"/> 


## Technical overview
 1. I used MVVM with the modern architecture components in the architecture of the app:
 ![image](https://user-images.githubusercontent.com/5509571/62429675-c6413500-b711-11e9-8bb2-40828606f41d.png)
 and the new paging technology in the Repos list:
 ![alt](https://codelabs.developers.google.com/codelabs/android-paging/img/a4f392ad4ae49042.gif)
 because I'm using room,the data layer handled in Room itself the data source and it's factory, I just implemented the Boundary Callback.
 2. The connection state changes are observed, be noted that there are different states in offline mode:
  * There is no cached data: you will see a dionsaur in this state after you getting online automatically we try loading data 
  to get in this state make mobile offline in a fresh install and open the app
  * There is cached data: you will see a snack bar tells you you're offline and whenever  you get connected it will disappear 
  
3. Error handling, I didn't handled the error well, but I made two basic error handling: 
   * If any error occurred including the offline state while loading the list of repos, you will see error message with retry button
4. There is always a room for improvements I like to begin with more test cases to cover more scenarios.  
   
## Notes:   
   * There is a fast splash screen with default wait time of a half second. 
 
## Third-Party Libs:
* Retrofit
Used as RESTful client,also I made the ok http client logs the apis response in only debug builds!
* Anko
Used in:

1. Multithreading & Background Work e.g.
```
doAsync{
  .... coroutine to run in background thread
  uiThread{
  ....coroutine to run in main thread
  }
}
````

2. Start activties e.g.
```startActivity<MainActivity>()```
* Room
used to cache both of food tags and food items in a local sqlite database.
* Dagger2
I Used dagger to inject some of dependencies but not all, just as a concept.


## References 
### Paging Library:
* https://developer.android.com/topic/libraries/architecture/paging/
* https://codelabs.developers.google.com/codelabs/android-paging/index.html

### Anko:
* https://github.com/Kotlin/anko/wiki

### Room:
* https://developer.android.com/training/data-storage/room/index.html

### Architecture components:
* https://developer.android.com/jetpack/docs/guide

### Dagger2:
* https://www.simplifiedcoding.net/dagger-2-android-example/
* https://dagger.dev/users-guide
<br/>
Thank you! :wink:
