import requests
import json
import os
import sys

PARAMS = {'X-TBA-Auth-Key':"gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj"}
URL = "https://www.thebluealliance.com/api/v3/"

def write():
	events = input("\nEnter 2018 event key(s) to write match json files for.\nSeparate keys by spaces: ").split()
	for event in events:
		if not event[:4] == "2018":
			event = "2018"+event

		print("Requesting:", URL + "event/" + event + "/matches")
		rq = requests.get(url=(URL + "event/" + event + "/matches"), params=PARAMS)
		code = int(str(rq)[str(rq).index('[')+1:-2])

		if code != 404:
			matches = rq.json()

			fullPath = os.getcwd()
			path = fullPath[:fullPath.rfind("\\")] + "\\data\\" + event

			if not os.path.exists(path + "\\matches"):
			    os.makedirs(path + "\\matches")

			for match in matches:
				json.dump(match, open(path + "/matches/" + match['key'][match['key'].index('_')+1:] + ".json", "w+"), indent=4)
			print("Files written.")
		else:
			print("Event Key", event, "returned error code", code)