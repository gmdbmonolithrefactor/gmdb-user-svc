# gmdb-user-svc

## Environment Variables required - Replace with actual data
* EUREKA_CLIENT_ENABLED=false # Default
* EUREKA_HOST=gmdb-discovery:8761
* DB_HOST_AND_PORT=localhost:3306 # Default
* DB_USER=gmdb #Default
* DB_PWD=someGoodSecret

## Endpoint Examples

## Docker Instructions
```
$ docker build -t gmdb/user .

$ docker run -d -p [localport]:8080 \
        -e EUREKA_CLIENT_ENABLED=true \
        -e EUREKA_HOST=gmdb-discovery:8761 \ 
        -e DB_HOST_AND_PORT=gmdb-devdb:3306 \
        -e DB_USER=gmdb \
        -e DB_PWD=someGoodSecret \
        --network gmdb-bridge \
        gmdb/user
```

## PCF Instructions
1. Build project as normal `$ ./gradlew clean build`
1. Push from root directory `$ cf push`

NOTE: Requires a mysql database service named gmdb-devdb (ex: ClearDb MySQL Database free-spark DB).  Name of service can be changed on command line, or in the included manifest.yml. 