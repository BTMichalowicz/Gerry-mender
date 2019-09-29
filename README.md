# Gerry-mender present by Dinnerbone

### This is Stony Brook University CSE308 team project in the fall of 2019 which is created by Jing Zhang, Veronica Quintana, Ian Peitzsch, Benjamin Michalowicz

## General info

**Gerry-mender** is an online election analysis appliction. We provide the Texas, Florida and North Carolina election result for 2014, 2016 and 2018. User can use Gerry-mender to simulate a "fairer" redistrct result.
	
## Technologies
Project is created with:
* Java Spring Boot - the back end of this webapp, handling webpage request and return data to front end
* HTML - the front end of this webapp, the User interface and send request to back end
* JQuery - write for web page function
* AJAX - front end content partial update 
* MySQL - database, stored the state, district and precinct information
* Microsoft Azure - deploy whole project into Azure to make a real online webapp
* Java - the backend program, to simulate the gerrymander and return the result


![image](https://github.com/BTMichalowicz/Gerry-mender/blob/master/Screen%20Shot%202019-09-29%20at%206.29.48%20PM.png)
## How to use
### Menu bar
1. Home - restore page to default setting, eg. clear the coeffient slider and input coeffient value, map level set to state level.
2. Export - user can select to save their result as .csv, .jpeg or .pdf file.
3. Help menu - some basic instruction and user guide

### Objective function part (left)
1. Check box - user can select which element will be involved to calculate redistrct result
2. Slider/input text - user can use slider to adjust coefficient for each element or input the coefficient value directly
3. Time interation - user is able to determine how long graph annealing is done
4. Apply - collect user setting and calculate the redistrct result

### Map part (center)
1. Map - interactive map, eg. user can click or hover the area to see the detail information, double click current state to go district level
2. Drop-down menu - user can select state level, distrct level and precinct levele manually
3. R/B view - map will show the election result with red(Republican Party) and blue(Democratic Party) colors
4. Year selection - user can select to use 2014, 2016 or 2018 election data to do redistrict

### Info content(right)
This part will show the detail information of user selection, such as population, election result.



## Contact info

jing.zhang.6@stonybrook.edu
