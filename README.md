# Product Import Api
> Rest API to upload product details from excel.

## Built with
* Spring Boot

## Other technologies/frameworks used
* Spring Security
* Spring Rest
* Spring JPA
* JWT
* Postgres
* Docker

## Getting Started

To Run this app you need to have docker installed.
1. Clone this repo.
2. Open cmd promt and go inside the folder
3. Run "docker-compose up" , it will start postgres database and api.
4. Api will be running on 6868 port.
5. Postgres will be running on 5432 port.
6. You can modify these port by modifying .env file.
7. PFB rest end points that this api is exposing.

POST: api/v1/user/register : to register user
Sample Body: {"username":"admin","password":"admin", "name":"Administrator"}

POST: api/v1/user/authenticate : to authenticate, this api will return jwt token needs to used for subsequent requests
Sample Body: {"username":"user","password":"user"}

POST: api/v1/products/upload : to upload excel
Send token (got as part of authentication) as part of header.
also send excel with "file" parameter in the body.

GET: api/v1/jobs : to get status of all jobs
Send token (got as part of authentication) as part of header.

GET: api/v1/products : to get all products in the db
Send token (got as part of authentication) as part of header.