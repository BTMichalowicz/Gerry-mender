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
* Microsoft Azure - project is deployed into Azure to make it a functioning webapp
* Java - used in the algorithm that redistricts


![image](https://github.com/BTMichalowicz/Gerry-mender/blob/master/State%20page.png)
## How to use
### Menu bar
1. Home - restore page to default setting, e.g. clear the coefficient slider and input coefficient  value, map level set to state level.
2. Export - user can select to save their result as .csv, .jpeg or .pdf file.
3. Help menu - some basic instruction and user guide

### User Controls Panel(left)
1. Check box - user can select which element will be involved to calculate redistrict result
2. Slider/input text - user can use slider to adjust coefficient for each element or input the coefficient value directly
3. Time iteration - user is able to determine how long graph annealing is done
4. Apply - collect user setting and calculate the redistrict result

### Map Panel(center)
1. Map - interactive map, e.g. user can click or hover the area to see the detail information, double click current state to go district level
2. Drop-down menu - user can select state level, district level and precinct level manually
3. R/B view - map will show the election result with red(Republican Party) and blue(Democratic Party) colors
4. Year selection - user can select to use 2014, 2016 or 2018 election data to do redistrict

### Info Panel(right)
This part will show the detail information of user selection, such as population, election result.



## Contact info

Dinnerbone@stonybrook.edu

