# Gerrymender, presented by Dinnerbone

### This is Stony Brook University CSE308 Fall 2019 team project, created by Benjamin Michalowicz, Ian Peitzsch, and Veronica Quintana.

## General info

**Gerrymender** is an online election analysis application. Using voting and demographic data for Florida, North Carolina, and Texas, a user can redraw those states' district lines to maximize the number of Minority/Majority districts.
	
## Technologies
Our project is created with:
* Java Spring Boot - the back end of this webapp, handling webpage requests and returning data to the front end
* HTML - the front end of this webapp, handling the user interface and sending requests to the back end
* JQuery - written for web page functions
* AJAX - front end content partial update 
* MySQL - the database, storing the state, district, and precinct information
* Java - used in the algorithm that redistricts

## The Application
![image](https://github.com/BTMichalowicz/Gerry-mender/blob/master/State%20page.png)
## How to use
### Navbar
1. Menu - opens the sliding menu of user controls.
2. Back - returns the user to the homepage, where they can select a different state.

### User Controls Panel(left)
The sliding menu has four tabs:
* Help - This displays a help menu, which gives information about what each tab entails.
* Data - Shows the summary voting and demographic data for the current state. The user can change which election they want to view using the radial buttons.
* Bloc - Contains the user controls for determining what precincts in the state are voting as a bloc.
* Redistrict - Has the user controls to redistrict the state to maximize the number of Minority/Majority districts. 


### Map Panel(center)
The interactive map allows a user to see the state selected and it's current voting districts. The user can click and drag the map, zoom in to view the precincts of the state, and hover over each area to see summary information for that precinct or district. Clicking a feature will also bring up that information in a panel on the right.

### Info Panel(right)
The info panel is activated when a district or precinct is selected, and displays the summary voting and demographic data for the feature.


