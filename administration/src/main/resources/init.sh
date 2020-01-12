#!/usr/bin/env bash

export COOKIE="X-Authorization=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNTc4ODcwMzM5LCJpYXQiOjE1Nzg4NjY3Mzl9.EmbTFhyzKdf7gZbjXQjaI-hOJWTnjAsSIW8UFx_jH5hYe07uPCfhGaJvQN0PTP_2aH-9dNOXZKrAOFeWkoceq8TwqAC_pipD4j3iDDTBlcMwoxldbs7mHntSuJ9t7I7Nik3Kv11t9Cfi9sl_n5u9P3uAnKje1pEhe32EoO8OvhnB5BX2G5JfJ3UsyhZKF9_UzOpf07qWWTnGP2kwwuLDzHMA7XSFOHFVKS8SxPHtRATwxOfum-Clmxfy5nDwNCVBn7m8ZhvoycHPLZ_mJgohFe0RhZGxgGRaoixFl11UMW8ntTwxHY1rsrKdVPU4iYjdRoxSBsMhPjw31X5s26XFHQ"

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
