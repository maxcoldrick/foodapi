# FoodAPI

A multi-container Kubernetes deployment.

## Usage
### Pre-Requisites
A Kubernetes Cluster

[Terraform](https://www.terraform.io/) installed and running.

To initiate the Kubernetes cluster according to the Terraform config file: `config.tf`:

`$ terraform apply`

## The Principle
Each of the following apps / services can run in their own containers / pods: `web`, `db`, `rabbitmq`,`server`, `java`.

`server` and `java` are mostly optional.

The `web` application initialises the tables in the PostgreSQL database that is running on `db`.

`web` uses a [Puma](https://github.com/puma/puma) webserver to expose an API with a RESTful interface (and [Devise](https://github.com/plataformatec/devise) token auth) to the DB and utilises ActiveRecord CRUD methods for database operations - the API returns JSON (the people's format).

The `web` application then exposes itself as 'ready for connections' to the `RabbitMQ` server, other apps and services can wait for this update if they are dependent on the API.

[`java`](https://github.com/maxcoldrick/foodapi/tree/master/databaseFillerUpper) acts as a testing suite for the API (WiP). `java` establishes a connection to `RabbitMQ` and waits for the API to say "I'm ready!". It then uses [JavaFaker](https://github.com/DiUS/java-faker) to generate some `valid-but-bogus` entries and POSTs them at the API (has to generate a token first).

The `server` container is an Nginx server which routes traffic away from the API if necessary.

For a viewpoint from an Infrastructure as Code perspective, you could look at the `config.tf` file.

The whole thing is stitched together with Kubernetes and utilises a [kompose](https://github.com/kubernetes/kompose) format for deployment. It's a a one-pod-per-container setup with services exposing ports for interconnectivity of the pods. The architecture of it looks a bit like this:

```
NAME                           READY     STATUS    RESTARTS   AGE
pod/db-657cdb6db-k5qn8         1/1       Running   0          22h
pod/java-5b887c598c-zxqlg      1/1       Running   0          22h
pod/rabbitmq-5d7d7ff66-b8bjd   1/1       Running   0          22h
pod/server-754f8b969c-96bnq    1/1       Running   0          22h
pod/web-f889b58cd-cnwvd        1/1       Running   0          22h

NAME                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)             AGE
service/db           ClusterIP   10.109.82.49    <none>        5432/TCP            22h
service/kubernetes   ClusterIP   10.96.0.1       <none>        443/TCP             4d
service/rabbitmq     ClusterIP   10.99.129.133   <none>        7000/TCP,7001/TCP   22h
service/server       ClusterIP   10.100.196.7    <none>        80/TCP,443/TCP      22h
service/web          ClusterIP   10.110.3.161    <none>        3000/TCP            22h
```

### Interacting with the API
#### Example node
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

###### Some Useful Commands
```
# Get events for container startup. Hanging ContainerCreating
kc describe pods

# Tail logs on pod
kc logs --follow $pod

# sh shell usually only installed on alpine images
- kubectl exec -it $pod -- /bin/sh
-- apk update && apk add curl

# Scale down & up
kubectl scale deployment java --replicas=0
kubectl scale deployment java --replicas=1
```
