# web-service
RESTful web service

# Task description

Design and implement a RESTful service for managing user data.

The user data entity includes:

Name
E-mail
Avatar (image)
The service shoud provide a CRUD operations for the whole user data as well as for distinct properties.

You can store data in any place (database,  memory, etc.)

Optional: Use Swagger for documentation the service.

# Usage

start server **mvn tomcat7:run** <br/>
start client **mvn package install exec:java -pl rest-client**
