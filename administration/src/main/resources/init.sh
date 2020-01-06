#!/usr/bin/env bash

export COOKIE="X-Authorization=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIyMSIsImV4cCI6MTU3ODMwMzUwNCwiaWF0IjoxNTc4Mjk5OTA0fQ.j3FNjGC_0_EPH89lASE9yeKPt7wJ0qKViGSVbbDVq7tyCb5_-miTSTdyAO4HsIh4dvcoEpGpOjVLBTclbgcQdpHCjJ8QeIeeRwhAznnqBJmvWoYGiy6_h3h9J8kIB6p9tpqkgW3of1RSONJWT4utR_UT6SYKi2pI5OBxebh5MR-B_DtMgFyL84FWQqwq8nzqHcNRNnCuE7Ti4UizBtkr55Un4PHlCJ66DQPSZLkRRYcj0lbRT-BABMrdKU3e9ClAJcHBkHdJYgLG3L-wQBD_zXCKaWnk9Gk0qzdJ_B5Djc7YL7TscuIi7TnAgOARzHYGQG6kbMo6Y5PjKne_II8lNA"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/0/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Golden dimension\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/2/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Managers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/2/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Developers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/3/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Ledgers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/3/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Xs2a\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/3/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Datasafe\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/3/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Cross-development\"}"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Ledgers\",\"code\":\"LDGRS\",\"description\":\"Bank ledgers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/6" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Xs2a-PSD2\",\"code\":\"XS2A\",\"description\":\"PSD2 XS2A interface\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/7" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Datasafe\",\"code\":\"DAT\",\"description\":\"Secure file storage\"}"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/2" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"s.dima\",\"password\":\"s.dima\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/3" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"h.vira\",\"password\":\"h.vira\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/4" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"dima2m\",\"password\":\"dima2m\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/4" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"petya\",\"password\":\"petya\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/4" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"kolya\",\"password\":\"kolya\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/4" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"roma\",\"password\":\"roma\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/4" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"maksym\",\"password\":\"maksym\"}"

curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/8/children/users-and-groups/14,15" -H  "accept: */*"

curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/16,17/in/10" -H  "accept: */*"
curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/18/in/11" -H  "accept: */*"
curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/8/in/9,10" -H  "accept: */*"
