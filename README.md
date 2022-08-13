# FakeGlucoseReadings

Fake glucose readings application is a very (very very) simple clone of the application we are currently working on.

However, the project is not yet finished, it is missing a few things:

- At startup the user is forced to view a blank white screen, which looks very bad. Therefore, implement ```Splash Screen```.

- If the sensor reads a glucose value less than, or equal to, 40 it means that the user is at risk. Therefore, in such a case, an ```Alert Dialog``` should be displayed on the home screen to inform the user of this risk.

- Sensor connection status in ```SensorRepository``` is handled by LiveData. In a real application, there are several steps that have to succeed in order for us to be sure that the connection has been established (this is done on a separate thread). Therefore, the existing implementation should be rewritten so that the repository returns an ```Observable``` that will be subscribed to in the ViewModel.

- Even once the application is completed it will not be released for use. This is due to the fact that medical apps must successfully pass certification by the relevant government departments. Unfortunately, by the fact that the app does not have any tests we will not be able to get certification. Therefore, create tests that cover the entire app. Fragments should be tested with instrumented tests, while the rest of the application should be tested with ordinary unit tests. Remember to add appropriate dependencies to the project (use a framework called [Mockk](https://mockk.io/) for mocking).

For each task, create a new branch on which you will add changes. 

## Very important:
If any task is not clear to you, or you have a problem with it - just ask for help.