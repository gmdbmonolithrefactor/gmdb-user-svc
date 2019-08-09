# gmdb-users-svc

## Environment Variables required
DATABASE_HOST=157.230.165.191:6603
EUREKA_HOST=157.230.165.191:8761

## To build docker image
```
docker build -t gmdb/users .
```

## To run the docker container
```
docker run -d -p 1081:8081 -e DATABASE_HOST=157.230.165.191:6603 -e EUREKA_HOST=157.230.165.191:8761 --name gmdb-users --rm gmdb/users
```

