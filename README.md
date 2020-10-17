## General
    
This is a Springboot app that does not have a GUI and uses in-memory h2 database,and exposes the end points listed in the test scetion.

You can access the DB through URL http://localhost:8080/h2-console/

For convenience I am adding the jar file that could be used to run the application, to the repo as well. BookNow-1.0-SNAPSHOT.jar

## Run the applicaton
1. Assumimg you have maven installed run below to generate jar file from the project repository.
  mvn clean install
2.To run the application run below command.
java -jar BookNow-1.0-SNAPSHOT.jar


## Tests performed.
1. Get available table and slot for a input date - GET -  http://localhost:8080/v1/availableSlots{date} E.g - http://localhost:8080/v1/availableSlots2020-10-15
2. Get all reservation for a input date - GET -  http://localhost:8080/v1/reservations{date} E.g - http://localhost:8080/v1/reservations2020-10-15
3. get reservation by a specific id - GET - http://localhost:8080/v1/reservations/{id}
4. Make a reservation - POST - http://localhost:8080/v1/reservations 
5. Reservation should be for maximum of 2 hours
6. Booking can only be within restaurant open hours
7. For a give time you can only reserve a max of 20 tables.
8. Same above rules apply for updating a reservation  - PUT - http://localhost:8080/v1/reservations/{id} 
9. Delete a reservation - DELETE - http://localhost:8080/v1/reservations/{id}	
