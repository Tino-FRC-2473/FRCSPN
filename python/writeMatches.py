import requests
import json
import os
import sys

PARAMS = {'X-TBA-Auth-Key':"gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj"}
URL = "https://www.thebluealliance.com/api/v3/"

event = input("\nEnter 2018 event key to write match json files for: ")
if not event[:4] == "2018":
	event = "2018"+event

rq = requests.get(url=(URL + "event/" + event + "/matches"), params=PARAMS)
code = int(str(rq)[str(rq).index('[')+1:-2])

if code != 404:
	matches = rq.json()

	fullPath = os.getcwd()
	path = fullPath[:fullPath.rfind("\\")] + "\\data\\" + event

	if not os.path.exists(path + "\\matches"):
	    os.makedirs(path + "\\matches")

	for match in matches:
		with open(path + "/matches/" + match['key'][match['key'].index('_')+1:] + ".json", "w+") as file:
			file.write(json.dumps([{'name': k, 'size': v} for k,v in match.items()], indent=4) + "\n")
else:
	print("Invalid Event Key:", event)