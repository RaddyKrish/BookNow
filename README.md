## General
    
This is a Springboot app that does not have a GUI and uses in-memory h2 database,and exposes following end points.

You can access the DB through URL http://localhost:8080/h2-console/

## Run the applicaton



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