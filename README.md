# Dinnerbone presents Gerry-mender

### This is Stony Brook University CSE308 team project in the fall of 2019 which is created by Jing Zhang, Veronica Quintana, Ian Peitzsch, Benjamin Michalowicz

## General info

**Gerry-mender** is an online 

**Celeste_Jotto** is an online man-machine interaction Jotto game webapp. User can play Jotto with our game AI.

Every user is required to register a unique user account with legal username and password. Also, Celeste_Jotto will record finished game automatically, so users can look back their game details at any time.

	
## Technologies
Project is created with:
* Java Spring Boot - the back end of this webapp, handling webpage request and return data to front end
* JSP - the front end of this webapp, the User interface and send request to back end
* JQuery - write for web page function
* MySQL - Celeste_Jotto's database, stored the user information, such as username, password and each game details
* Microsoft Azure - deploy whole project into Azure to make a real online webapp
* Java - the game program, to determine the winner and return feedback of each guess
	

## Setup
To set up and deploy this project, you need:

* Create/Have your Azure account 
* Set up the Azure MySQL database
* change the setting up information under \src\main\resources\application.properties
* Deploy this project into your Azure space


## How to use

1. Create your own account
2. After login successfully, choose new game button
3. Choose your own 5-letters secret word, then start the game
4. In the game page, you can select to guess by your own or let computer choose a random word for you
5. After your send the guess word, you will get the feedback of this turn, using feedback to make a better guess until winner is produced

## Contact info

samzhang3442@gmail.com
