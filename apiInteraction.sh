# sign_up / add user to db
curl -X POST \
  http://localhost:3000/auth \
  -H 'Accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "email": "pete@pete.pete",
  "password": "petepete",
  "password_confirmation": "petepete"
}'

# sign_in
curl -X POST \
  http://localhost:3000/auth/sign_in \
  -H 'Content-Type: application/json' \
  -d '{
  "email": "pete@pete.pete",
  "password": "petepete"
}'

# Get
curl -X GET \
  http://localhost:3000/food \
  -H 'Content-Type: application/json' \
  -H 'access-token: za8MK8YD5bWpm0C4bFb27w' \
  -H 'client: PYgt4zJMeuLC6DQFNk2gbg' \
  -H 'expiry: 1563570160' \
  -H 'token-type: Bearer' \
  -H 'uid: pete@pete.pete'
