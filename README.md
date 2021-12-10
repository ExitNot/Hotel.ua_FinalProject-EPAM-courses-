# Hotel.ua
### Final project for EPAM java course

------------------------

This project is a web application created for booking rooms in a hotel.
Customers can create a personal account, which gave them the ability to make a booking request or book a specific room.
But customers can not book any room without a personal account.

The request consists of such parameters:
- Number of guests
- Apartment class
- Arrival date
- Amount of days

Another role in Hotel.ua is a manager.
Manager main task is monitoring new requests, choosing the most suitable rooms for request,
and sending back a request to confirm the booking.

After successful booking the system creates a bill that has to be paid up, otherwise, it would be canceled in 2 days.

## Technologies

------------------------

- Java 11
- Gradle
- PostgreSQL 14.0
- JDBC
- Java servlets and JSP
- JSTL
- Log4j 2.14
- Mockito

## Installation

------------------------

- Clone this repository
- Launch PostgreSQL database (For db initialisation you have to use db/db-create.sql script).
- Please modify webapp/META-INF/context.xml resource credentials corresponding to your db.
- Use TomCat version 8.
- Download postgresql42.3.1 (Data source) and javax.activation-1.2.0 jar's and put them to $CATALINA_HOME/lib folder
(This versions was used in development process. Possibly can use higher or even lower versions.)

## My database

------------------------

![alt text](https://github.com/ExitNot/Hotel_FinalProject_EPAM_courses/blob/master/db/db-image.png?raw=true)