# Reviewer (still on development stage)
This application is a travel guide platform that helps users find the best places to travel based on their budget. It includes a search engine for discovering restaurants, hotels, and places, a merchant database with reviews and ratings, a booking system, and an AI-based recommendation system.

# Local Deployment guidelines
#### Requirements:
* Java >= v18
* PostgreSQL DB running (Preferred running in 5432 port)
* Maven >=3.9

Make sure the environment variables are set correctly.

#### Run the server:
* Open `/Reviewer/src/main/resources/application.properties`
  * Set the port number to run the server
  * Set postgres credentials (url, username, password)
* Open `/Reviewer/src/`
  * Run command 'mvn clean install'
  * This will install required dependencies
* Open `/Reviewer/src/` and run the following command
  * Run command 'mvn compile'
  * After successfull compilation, it shows 'BUILD SUCCESS'
  * Run command 'java /main/java/com/reviewer/ReviewerApplicationBoot.java'
