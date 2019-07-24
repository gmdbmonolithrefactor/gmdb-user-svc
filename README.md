# gmdb-users-svc

## Environment Variables required
DATABASE_HOST=192.168.0.200:3306
EUREKA_HOST=127.0.0.1:8761

## To build docker image
docker build -t gmdb-users .

## To run the docker container
```
docker run -d -p 1081:8081 -e DATABASE_HOST=192.168.0.200:3306 -e EUREKA_HOST=127.0.0.1:8761 --name gmdb-users --rm gmdb/users-svc
```

