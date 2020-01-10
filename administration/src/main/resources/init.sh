#!/usr/bin/env bash

export COOKIE="X-Authorization=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNTc4NjgzMjA0LCJpYXQiOjE1Nzg2Nzk2MDR9.jCVxUVkWAnWftrtn4qeGt_T8rFEATC-A3XU1ZTinkwAraREd6_9RJu2CSjqjhNFRvICxEQPeBLzRy72OkymJcvK_gTwxlNJwsTKLK2WDH_wtjPK8l417wQfh3LGqUJORWc8j0vGM_rlLMgZvXFOOfFyHp9CGHLpaW5Yio7n6GYjDb3CpMPxy0tj5OBulY0edUoOYJIATYw0sbZ10UkcQiJPPoexK6vFI1jDkugfe1hjx8vXOSgd_MucVr83pKjFpkPnZjYg87k-JM2WMXmbQHlcS2QueRZ3VKtOxcsp5_dwaCxcVZwuINpkDN-rDsxGwKmVxHpBC5VG3eQU7lLeQpA"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/1/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Golden dimension\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/3/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Managers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/3/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Developers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Ledgers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Xs2a\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Datasafe\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Cross-development\"}"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/6" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Ledgers\",\"code\":\"LDGRS\",\"description\":\"Bank ledgers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/7" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Xs2a-PSD2\",\"code\":\"XS2A\",\"description\":\"PSD2 XS2A interface\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/8" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Datasafe\",\"code\":\"DAT\",\"description\":\"Secure file storage\"}"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/3" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"s.dima\",\"password\":\"s.dima\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/4" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"h.vira\",\"password\":\"h.vira\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"dima2m\",\"password\":\"dima2m\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"petya\",\"password\":\"petya\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"kolya\",\"password\":\"kolya\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"roma\",\"password\":\"roma\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"maksym\",\"password\":\"maksym\"}"

curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/9/children/users-and-groups/15,16" -H  "accept: */*"

curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/17,18/in/11" -H  "accept: */*"
curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/19/in/12" -H  "accept: */*"
curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/9/in/10,11" -H  "accept: */*"
