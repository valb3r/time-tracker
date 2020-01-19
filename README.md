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
