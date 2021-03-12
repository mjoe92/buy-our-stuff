# Buy Our Stuff Webshop

## How To Run in IntelliJ

!!!!!!!!!!!!!!!!!!!Choose FileDao2 branch! Development branch not working!!!!!!!!!!!!!!!!!!!
Setting environmental variables:

DAO_TYPE:
    to choose PSQL Database DAO implementation type: database
    to choose file DAO implementation type: file
    to choose memory DAO implementation type: >>leave blank<< (default setting)
        
PSQL_USER_NAME:
    type your PSQL username

PSQL_PASSWORD:
    type your PSQL password

PSQL_DB_NAME:
    type database name (the database should be created manually before run)
  
example:
    DAO_TYPE=database;PSQL_USER_NAME=user1;PSQL_PASSWORD=asdf;PSQL_DB_NAME=buy_our_stuff
    

- import as a Maven project
- click the `Maven` label
- click the project's name
- click `Plugins`
- click `jetty`
- double click `jetty:run`
- open `localhost:8888` in the browser






