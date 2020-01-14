#!/usr/bin/env bash

export COOKIE="X-Authorization=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNTc5MDEzMzgzLCJpYXQiOjE1NzkwMDk3ODN9.rZjrV3WJ-2j2Rp5-RQsaOyCmGLHI21x3Vo-MCSc8KqqFhldOyrmJDhdQmw73bqtW3AfS0wM92gO3MLyqJJeUW4hBEYdwx1BVAwuiNCraE3uUCjMBfbp62CqRXvG38X3VKoKWvdfSYP7zGYNIDE8fLLiY9SnNne3JThrqxft-sxu6Xfpt_tNwb_x1-7LK7K3HpaV5KvdmSTtb0VyEmkPMequxIUqQIUTHmY34tf1t_VQdtaoJIbf-y-fG5cdyX5dmGk-41vdbMoSSm66-9BoS5I1jGQpE7-N4c_PmF2MR4vy4Lp7oYxn5ef8LjP1a4rbK8UAqSgZKloIEnptFQJ5dPQ"

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
