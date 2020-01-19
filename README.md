# Tech stack:

1. Spring Boot 2 with reactive WebFlux REST endpoints.
1. Neo4j database with SDN-RX (endpoints) and OGM (reporting) access layers.
1. Spring Batch with metadata persistence on Neo4j
1. Angular / Angular material/flex for UI
1. Gradle as build system.
1. Dockerized deployment via docker-compose.

# UI / Application look and feel

## User management / Time logging

![User management](manage-and-record.gif)

## Reporting (using JXLS templates for report template)

![Reporting](report.gif)


# Features:

1. Complex user-group relations management.
1. Time card entry.
1. Report generation in xlsx format.
1. Reactive endpoints.

# Architecture

![Architecture diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/valb3r/time-tracker/master/architecture.puml&fmt=svg&vvv=2&sanitize=true)


# Build and run

Root project dir, execute these:

```sh
./gradlew clean buildImage
export TIMETRACKER_VERSION=`git rev-parse --verify --short HEAD`
docker-compose up 
# OR 'sudo -E docker-compose up' if you need sudo to run docker
```

Commands above spin up everything needed. UI will be at localhost:6500


# Stub data

To create stub data:
1. Ensure you have removed `neo-db` docker volume if it is not first start.
1. `docker-compose up`
1. run `administration/src/main/resources/init.sh`.

Important note - `init.sh` assumes that it is executed on clean database which was touched by administration 
application - that means clean neo4j started and administration app started but no users were added through UI.  
Test users all have same password as their login. I.e. `admin/admin`
