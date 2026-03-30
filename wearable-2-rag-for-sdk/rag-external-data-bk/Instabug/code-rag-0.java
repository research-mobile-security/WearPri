//Java
Instabug.getUserUUID((uuid)->{
//use the uuid
 });

//Kotlin
Instabug.getUserUUID { uuid -> 
//use the uuid
}

curl -XDELETE 'https://api.instabug.com/api/web/public/users/v1'\?application_token\="app_token"\&email\="email"

curl --location --request DELETE 'https://api.instabug.com/api/web/public/users/uuid/v1?application_token=app_token&uuid=uuid' \
--header 'Content-Type: application/json' \
--data-raw '{
    "token" : "SECRET_TOKEN"
}'
