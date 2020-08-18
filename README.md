# Storage Manager


Application for managing warehouses for rent. The customer has the option of searching for a warehouse, booking and extending the rental period.
It is used by the owner to monitor the booking status of his storages.


## Technologies (Prerequisites)
Project is created with:
- JDK 11
- Hibernate 
- MySQL Connector

You should have access to MySQL Database. Access to the database is controlled via configuration file in resources.

## What does it do?

This application has following funcionalites:

- connecting to database
- adding new and deleting storages
- adding new and deleting users
- listing storages
- listing users
- showing reservations
- searching for storage with chosen size or specification
- searching for user by Surname, telepho number, id
- managing rental periods
- preparation of daily/weekly/monthly report
- the ability to give prices in the promotion


## What is to be done?
- create parsers
- searching for storage with chosen size or specification
- managing rental periods
- preparation of daily/weekly/monthly report
- the ability to give prices in the promotion
- test it


## How to bulid?

To build this application:
```
mvn package
```

to run this aplication:

```
cd target
java - jar  storageManager.jar

```

## Author
Marta Kowalczyk @Ciechmar
