import requests
import json

PARAMS = {'X-TBA-Auth-Key':"gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj"}
URL = "https://www.thebluealliance.com/api/v3/"

EVENT = "week0"

teams = requests.get(url=(URL + "event/2018" + EVENT + "/teams/keys"), params=PARAMS).json()

with open("docs/teams.txt", "w+") as file:
	for i in range(len(teams)):
		file.write(teams[i] + '\n')