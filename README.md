# gmdb-users-svc

## Environment Variables required - Replace with actual data
* EUREKA_HOST=dev1.robwing.com:8761
* DATABASE_HOST=dev1.robwing.com:6603
* DB_USERNAME=gmdb_app
* DB_PASSWORD=gmdb_app

## To build docker image
```
docker build -t gmdb/users .
```

## To run the docker container
```
docker run -d -p 1081:8081 --name gmdb-users gmdb/users \
      -e EUREKA_HOST=dev1.robwing.com:8761 \
      -e DATABASE_HOST=dev1.robwing.com:6603 \
      -e DB_USERNAME=gmdb_app \
      -e DB_PASSWORD=gmdb_app
      
```