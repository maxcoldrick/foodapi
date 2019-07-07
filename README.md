# FoodAPI

Containerised (Docker) implementation of an API (ruby) to a PostgreSQL db, with a component to fill
the database with entries via POST to the API (Java - /databaseFillerUpper).

## Usage
Should be as simple as: `docker-compose up`
This will start three containers: 
- The API
- The Database
- The databaseFillerUpper

### Example node
```
{
    "id": 102,
    "dish": "Red Lentils",
    "measurement": "1/3 teaspoon",
    "description": "Cupiditate esse vel praesentium ut est.",
    "ingredient": "Flour",
    "spice": "Nigella"
}
```

### Interacting with the API
#### Authentication
Handled by the Devise gem, an authentication token is required in the header of each request.
Authentication lives under the /auth/ route.

##### Step 1
Add a user to the database.
Example request:
```
curl -X POST \
  http://localhost:3000/auth \
  -H 'Accept: */*' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:3000' \
  -d '{
  "email": "petepete@pete.pete",
  "password": "petepete",
  "password_confirmation": "petepete"
}'
```
Example response:
```
{
    "status": "success",
    "data": {
        "id": 2,
        "provider": "email",
        "uid": "petepete@pete.pete",
        "allow_password_change": false,
        "name": null,
        "nickname": null,
        "image": null,
        "email": "petepete@pete.pete",
        "created_at": "2019-07-07T14:50:05.664Z",
        "updated_at": "2019-07-07T14:50:05.930Z"
    }
}
```


##### Step 2
Sign in.
Example request:
```
curl -X POST \
  http://localhost:3000/auth/sign_in \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:3000' \
  -d '{
  "email": "pete@pete.pete",
  "password": "petepete"
}
'
```
Example response (success):
```
{
    "data": {
        "id": 1,
        "email": "pete@pete.pete",
        "provider": "email",
        "uid": "pete@pete.pete",
        "allow_password_change": false,
        "name": null,
        "nickname": null,
        "image": null
    }
}
```

#### Food
The food endpoint lives under the /food route.
##### Getting the food nodes
Example request:
```
curl -X GET \
  http://localhost:3000/food \
  -H 'Accept: */*' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:3000' \
  -H 'access-token: PLVj-IgfhVn2mduQRc4LPA' \
  -H 'client: dyD7PONfbPBaw9JMWhHjFw' \
  -H 'expiry: 1563720652' \
  -H 'token-type: Bearer' \
  -H 'uid: pete@pete.pete'
```
Example response:
```
{
    "status": "SUCCESS",
    "message": "Loaded meal details",
    "data": [
        {
            "id": 102,
            "dish": "Red Lentils",
            "measurement": "1/3 teaspoon",
            "description": "Cupiditate esse vel praesentium ut est.",
            "ingredient": "Flour",
            "spice": "Nigella"
        }
}        
```
##### Adding a new entry to the /food endpoint
Example request:
```
curl -X POST \
  http://localhost:3000/food \
  -H 'Accept: */*' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:3000' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'access-token: PLVj-IgfhVn2mduQRc4LPA' \
  -H 'client: dyD7PONfbPBaw9JMWhHjFw' \
  -H 'expiry: 1563720652' \
  -H 'token-type: Bearer' \
  -H 'uid: pete@pete.pete' \
  -d '{
  "dish": "Red Lentils",
  "measurement": "1/3 teaspoon",
  "description": "Cupiditate esse vel praesentium ut est.",
  "spice": "Nigella",
  "ingredient": "Flour"
}'
```
Example response:
```
{
    "status": "SUCCESS",
    "message": "Saved meal",
    "data": {
        "id": 103,
        "dish": "Red Lentils",
        "measurement": "1/3 teaspoon",
        "description": "Cupiditate esse vel praesentium ut est.",
        "ingredient": "Flour",
        "spice": "Nigella"
    }
}
```
###### Alternatives
You could also use this Postman collection: https://www.getpostman.com/collections/71d21d4a010bc0528206
