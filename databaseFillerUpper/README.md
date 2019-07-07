# DatabaseFillerUpper
The database lives behind a RESTful API which has support for simple token authentication.

So, we hit the log-in endpoint of the API first to get a token.

Then we build some dummy-data to fill the database with (https://github.com/DiUS/java-faker).

Then we POST it at the API with the auth creds we got in step 1.

## Usage
Pull this and `docker-compose up` it: https://github.com/maxcoldrick/foodapi 

If that works (which would be a complete miracle and unexplainable by science) - it'll start an API on localhost.

Then, in the DatabaseFillerUpper directory, you can just `$ mvn clean install` and `java -jar target/1-0.0-etcetc.jar`.