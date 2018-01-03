Project Name: Cy’s Rides

Team Members:
Adam Hauge, 
Claudia Athens, 
Ryan Cerveny, 
Zachery Knoll 

Description of project: 
For this project, we will be creating a ridesharing app which works specifically for ISU students. This app will allow students who have cars to offer rides to students heading to various locations such as home for a holiday break. This app will be utilized both by students offering rides and by students looking for rides. Drivers will be able to request a certain amount of payment per ride, specifically to cover gas money. Our app will also include a private messaging system to allow users to more closely coordinate rides with each other and can be used by as many people as the driver can fit in their car. This app will be specific to Iowa State so that all rides must either start or end in Ames, IA and all user accounts will require an Iowa State email address to ensure that only ISU students utilize the app. Map locations and directions will be implemented through the use of the Google Maps API in order to calculate travel time and distance. Our app will also include a calendar system to allow for ride schedules and a reminder system to both the driver and the rider. 
 
All user accounts will require their name, their phone number, whether or not they plan on offering rides or are looking for rides, and preferred method of communication. All accounts may also be attached to a Venmo or paypal account to accommodate payment options, though users will be given the option to opt out of linking through a payment account and will be responsible for finding another form of payment. All members of our user database will connect to our ride database to include which users are involved with which ride system.
 
The three types of users for this app will consist of:
- Moderators who will be able to look at and report/remove all inappropriate messages and ride requests.
- Users who are looking for rides
- Users who are offering rides
 
Users looking for rides and users offering rides may become interchangeable through account settings.
  
This project will be created using android studio. We will work together to create a central page to use for the app, then each feature will branch from that main page. From the main page, the user will be able to log in to their student account, offer/request a ride, search current ride offerings depicted on a map to check for anyone going in their general direction, message other users, and check the status of their current/previous requests/offers. All current rides will be stored in an SQL database for easy searching and retrieval. All ride offers will include time, date, destination, how many people can join in the ride, and a fee for gas money. All ride requests will consist of time, date, location, and an optional value of how much the user is willing to pay. When creating an offer/request, we will implement the Google Maps API in order to calculate time and distance and the desired route will be displayed on a map for the user to see. This route may also be seen by students searching for and offering rides. Because this is an ISU specific app, we will make sure that all rides start or end in Ames to ensure it is only being utilized for Iowa State student purposes. All users will have a rating associated with them that is used by others to judge their reliability with rides and delivering payments.

Language/platform/libraries: Java, MySQL, PHP, Volley, Android SDK, Android Studio, Google Maps API 

Large/Complex:
This  project will include a large amount of android mobile development programming. Programming mobile applications is something very new to all of us and we will all need to learn how to utilize it efficiently. On top of this, most of our group is unfamiliar with SQL and we will need to learn how to use it in order to keep track of user profiles as well as all ride offers and requests. We all need to learn how to utilize the Google Maps API and how to implement it largely throughout our application. We will also all need to learn how to create a database to allow us to keep track of all of the users of our application. Our app will consist of a large series of pages extending from a central menu of our app. We will all need to learn how to create this app in android studio so that the control flow of the pages is smooth and easily navigated for all users. On top of this, we need a way to learn how to implement a private messaging system which allows users to collaborate with each other in order to better coordinate ridesharing and this interactivity needs to be limited to always include someone offering a ride and someone looking for a ride. We will also need to implement a search feature which allows users to find rides going in their general direction. This search feature will be complex because we need a way to implement it so that the user can find anyone going in a certain direction within a certain time frame. This will largely include implementing a function with a map and calendar system to check for matches in our rideshare database. We will also need to come up with a system to let users rate their experience with other users to hold people accountable for payments and rides. We also need to create a way to pay others in the app whether that consists of setting up Venmo  or paypal accounts through the app or through setting up our own payment system. Finally, we are going to need to implement a mobile notification system to remind users of their upcoming rides.
