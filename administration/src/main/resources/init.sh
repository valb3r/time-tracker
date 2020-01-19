#!/usr/bin/env bash

export COOKIE="X-Authorization="`curl -X POST -i "http://localhost:20081/v1/login" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"admin\", \"password\":\"admin\"}" | grep -e "X-Authorization=" | cut -d"=" -f2 | cut -d";" -f1`

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/1/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Great Company\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/3/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Managers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/3/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Developers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Accounting project\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Hedge fund project\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Security project\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Cross-development\"}"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/6" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"AccountingApp\",\"code\":\"ACCNT\",\"description\":\"Bank ledgers\", \"activities\":[\"Develop\", \"Manage\"]}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/7" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"HedgeApp\",\"code\":\"HDGA\",\"description\":\"PSD2 XS2A interface\", \"activities\":[\"Develop\", \"Manage\"]}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/8" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"SecurityApp\",\"code\":\"SECAPP\",\"description\":\"Secure file storage\", \"activities\":[\"Develop\", \"Manage\"]}"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/3" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"dima\",\"password\":\"dima\",\"rate\":\"100\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/4" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"jack\",\"password\":\"jack\",\"rate\":\"90\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"james\",\"password\":\"james\",\"rate\":\"80\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"petya\",\"password\":\"petya\",\"rate\":\"30\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"kolya\",\"password\":\"kolya\",\"rate\":\"30\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"roma\",\"password\":\"roma\",\"rate\":\"30\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"maksym\",\"password\":\"maksym\",\"rate\":\"30\"}"

curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/9/children/users-and-groups/15,16" -H  "accept: */*"

curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/17,18/in/11" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"from\":\"2000-01-01T00:00:00\",\"to\":\"2030-01-01T00:00:00\",\"rate\":\"20\"}"
curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/19/in/12" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"from\":\"2000-01-01T00:00:00\",\"to\":\"2030-01-01T00:00:00\",\"rate\":\"20\"}"
curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/9/in/10,11" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"from\":\"2000-01-01T00:00:00\",\"to\":\"2030-01-01T00:00:00\",\"rate\":\"20\"}"
