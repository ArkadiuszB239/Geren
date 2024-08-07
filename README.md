# Geren - project for learning purposes

Main goal of this project is improve as a developer. 
Use new technologies, write some (at leas try to) clean code and have fun :P

For now the main subject of this application is to get data from Google Calendar,
store it somewhere e.g. database and send sms notification base on it.

## Why?

Because I needed some goal to achieve, something simple yet doing something.
Funny story, I heard that my barber is manually sending SMS reminders to customers every morning.
They keep their schedules in Google Calendar, so I thought: 
> I have my goal

Maybe one day he will use this application. Who knows... :)

I invite you for my journey with this project. I will try to keep adding new ideas and 
keep expanding this project. From time to time in case of adding some new technologies or 
doing significant changes I will add new version of this application.



# Project description

Currently, application have two functions.
* Collecting events from google calendar
  * Pulling events from provided period
  * Storing events in database with accurate state
* Sending sms notifications
  * Retrieving data from database
  * Creating context and sending messages
  * Updates processed entries states

My goal is to deploy this application on AWS. Currently, I'm able to connect to PostgreSQL
instance deployed on AWS from my local environment and send SMS messages through AWS
Simple Notification Service (SNS) sandbox.


# How to run it

1. Create project on Google Cloud Platform and enable [Google Calendar Api](https://developers.google.com/calendar/api/quickstart/java?hl=en)
2. Get credentials.json file and put it under google-calendar-api/src/main/resources
3. Prepare local or remote database instance and provide connection data to application.yml
   * Database schema init script is located in: /postgres-data-access-layer/src/main/resources/init-db-schema.sql
4. Create event using google calendar ui
   * Remember: Calendar event description is intended for phone number for sms notification
   * Currently accepting and validating only polish formats of phone numbers. You can change default region in MeetEvent and MeetEventsValidator.
   * You can support yourself with python scripts in Scripts catalog. Those scripts also requires credential.json file in mentioned location
5. Trigger collecting and processing events endpoints in EventsController

