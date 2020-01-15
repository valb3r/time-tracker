#!/usr/bin/env bash

export COOKIE="X-Authorization=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNTc5MDg0ODIwLCJpYXQiOjE1NzkwODEyMjB9.A3b_BU0jhbRig7kWckvbORTkFNEeH0yK8xBJYHC27uobglw-4oNyyr1c_HJfbvgAEmiuLDYSwlqo0wwg3hKJsalsQL6skkXekrg-jLG08k8F_dPExMObYhFMlqec_MoJIK5eywU2gE0LrSVqNyJ7CekXAPQzdyyRGWAp7ePfsRY5cRl-g5cwET4JYA-qE9f8u3doYCsqkup-2K-tEhs-pGbvAn30YSAUq2nBf_hPj9lz6uW3f5qd_avP97sQbio8-_gPk6sMKPD8YqreNIFIDJ7CoBf-AI6gDCFdUmBY_DQBIsDAsRg0MtZJsgyhVH_b-kOSYMVBSDKr_5Zk348dGQ"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/1/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Golden dimension\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/3/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Managers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/3/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Developers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Ledgers\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Xs2a\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Datasafe\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/4/children" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Cross-development\"}"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/6" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Ledgers\",\"code\":\"LDGRS\",\"description\":\"Bank ledgers\", \"activities\":[\"Develop\", \"Manage\"]}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/7" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Xs2a-PSD2\",\"code\":\"XS2A\",\"description\":\"PSD2 XS2A interface\", \"activities\":[\"Develop\", \"Manage\"]}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/projects/of_group/8" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"name\":\"Datasafe\",\"code\":\"DAT\",\"description\":\"Secure file storage\", \"activities\":[\"Develop\", \"Manage\"]}"

curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/3" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"s.dima\",\"password\":\"s.dima\",\"rate\":\"10\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/4" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"h.vira\",\"password\":\"h.vira\",\"rate\":\"10\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"dima2m\",\"password\":\"dima2m\",\"rate\":\"10\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"petya\",\"password\":\"petya\",\"rate\":\"10\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"kolya\",\"password\":\"kolya\",\"rate\":\"10\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"roma\",\"password\":\"roma\",\"rate\":\"10\"}"
curl -X PUT --cookie "$COOKIE" "http://localhost:20081/v1/resources/users/of_group/5" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"maksym\",\"password\":\"maksym\",\"rate\":\"10\"}"

curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/groups/9/children/users-and-groups/15,16" -H  "accept: */*"

curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/17,18/in/11" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"from\":\"2000-01-01T00:00:00\",\"to\":\"2030-01-01T00:00:00\",\"rate\":\"20\"}"
curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/19/in/12" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"from\":\"2000-01-01T00:00:00\",\"to\":\"2030-01-01T00:00:00\",\"rate\":\"20\"}"
curl -X POST --cookie "$COOKIE" "http://localhost:20081/v1/resources/roles/DEVELOPER/actors/9/in/10,11" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"from\":\"2000-01-01T00:00:00\",\"to\":\"2030-01-01T00:00:00\",\"rate\":\"20\"}"
