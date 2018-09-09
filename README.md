# Get Your Guide Challenge

## First impressions

First of all thanks for considering this code, it was very pleasant to make it, and I've learned a lot!

## Architectural decisions

The App is using MVVM with Databinding, today is the "go-to" architecture for me in Android, in some simple Apps, can be too much, but I think in this case, using it I can show things that I know and if the App was made in a simpler way, maybe I don't have ways to show that.

The main reason for using MVVM is making logic, models and layers of the aplication more testable, together with dependency injection the classes became even more apart from eatch other.

## App structure

Although the app is very simple I've choose to go with a Main Activity and replace tha central content with two fragments, the reviews list and the favorites reviews list, according with the option selected on a BottonNavigationView.

## React and LiveData

The main comunication between layers of the app occur using RxJava and LiveData, all the data from "outside the app", Database and Network, goes via a Stream until reach the viewmodel Layer that uses a LiveData to comunicate with the views.

### Why not use RxJava all the way ?

The reason to use LiveData from the ViewModel foward is that LiveData is Lifecycle Aware, meaning that he knows wich state the Activity and/or Fragment of the user is, so that way, if something happens, the Stream of data can act properly with the current conditions of the app, for example when the user pauses the Activity by pressing the home button twice.

## Unit tests

I've made basic unit tests for the ReviewRowViewModel and the ListFragmentViewModel, just to show that I know how to do them.

The Other tests possibles are:

- Test the Dao from ROOM.
- Test the MainActivityViewModel and the FavoritesFragmentViewmodel ( that will be almost the same as ListFragmentViewModel Tests )

## Persistance

I have implemented a list of favorite reviews with Room.

## Another possible ideas

The reviews can be sorted by some categories, so when the user favorite a review, a tag can be added to the review, so the user can categorize the reason why the review was favorite, for example:

- Good food Tip
- Best way to move around
- Business tip / discounts
- Etc..

### Foreword

It was very nice to work with a realm problem of the tourism area, I love to travel and it was very interesting for me.

Thanks for reaching here.

Regards,

*Samuel Filizzola*
