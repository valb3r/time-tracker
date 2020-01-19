# Build and run
Root project dir, execute these:

```sh
./gradlew clean buildImage
export TIMETRACKER_VERSION=`git rev-parse --verify --short HEAD`
docker-compose up 
# OR 'sudo -E docker-compose up' if you need sudo to run docker
```

Commands above spin up everything needed. UI will be at localhost:6500

To create stub data - run `administration/src/main/resources/init.sh`.
Test users all have same password as their login. I.e. `admin/admin`
