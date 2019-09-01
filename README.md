# AppDev-As1
Matthew O'Connor
20048246

## Video
Video is available at: https://youtu.be/k9QlANRwKJw

'user@wit.ie' is pre registered
 password is '12345678'

## What is it?
Autofile is an app allowing users to record interesting vehicles they come across with entries like:
- Make, model... etc
- Images
- Location

## Features
- Custom Splash screen of appropriate design displayed for 5 seconds.
- Signup/Login with input validation and Firebase auth.
- Google signin also possible.
- CRUD: Add, Cancel, Delete, Logout, Settings in toolbar in different contexts.
  All work appropriately i.e. when you log out you cannot press back to log back in. 
- Confirmation toast popups on success sign in, deletion etc.
- Settings has statistics: username, the log of how visited forts and how many total forts.
- Vehicle List has Make, Model, image, rating and favourite checkbox.
- Each Entry has Make, Model, Image, Date seen, Rating, Location, Color and Favourite.
- Entries can be added, edited, deleted and both User and Entries have Firebase persistance.
- Images can be added direct from camera.
- User live location tracking (Can walk around and it updates realtime)
- Model View Presenter approach adopted.
- Search using keywords (for make, model, color)
- Floating button to add new entry.
- Favourites view toggle.
- Portrait views where appropriate
- Map of all entries with information when clicked on.

- Custom Styles & layouts.
- All strings called from strings.xml file.
- Github commit history with multiple releases and a branching model used.

## UML Diagram

<img width="650" alt="screen shot 2018-11-05 at 00 41 45" src="https://user-images.githubusercontent.com/16647291/64079311-0cbc8c00-ccde-11e9-90cd-cefc67e537d6.png">

## Screenshots
<img width="271" alt="screen shot 2018-11-05 at 00 44 47" src="https://user-images.githubusercontent.com/16647291/64079265-6cfefe00-ccdd-11e9-904a-2d3a10deecb8.jpg"><img width="269" alt="screen shot 2018-11-05 at 00 41 45" src="https://user-images.githubusercontent.com/16647291/64079266-6cfefe00-ccdd-11e9-8a2d-2dfa51fb5d4f.jpg">

<img width="269" alt="screen shot 2018-11-05 at 00 41 45" src="https://user-images.githubusercontent.com/16647291/64079267-6cfefe00-ccdd-11e9-92fc-686f12c05a0f.jpg"><img width="269" alt="screen shot 2018-11-05 at 00 41 45" src="https://user-images.githubusercontent.com/16647291/64079268-6d979480-ccdd-11e9-976a-75fc7d389b1f.jpg">

<img width="269" alt="screen shot 2018-11-05 at 00 41 45" src="https://user-images.githubusercontent.com/16647291/64079269-6d979480-ccdd-11e9-95cf-a627d0303585.jpg"><img width="269" alt="screen shot 2018-11-05 at 00 41 45" src="https://user-images.githubusercontent.com/16647291/64079270-6d979480-ccdd-11e9-8433-b99150627d33.jpg">

<img width="269" alt="screen shot 2018-11-05 at 00 41 45" src="https://user-images.githubusercontent.com/16647291/64079271-6d979480-ccdd-11e9-8753-5065fc4e8bba.jpg">

## Warning
google_maps_api.xml file has been excluded

Repo will not compile until the file is added with API key
