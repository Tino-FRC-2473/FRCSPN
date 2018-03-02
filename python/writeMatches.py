import requests
import json
import os

PARAMS = {'X-TBA-Auth-Key':"gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj"}
URL = "https://www.thebluealliance.com/api/v3/"

EVENT = "week0"

matches = requests.get(url=(URL + "event/2018" + EVENT + "/matches"), params=PARAMS).json()

fullPath = os.getcwd()
path = fullPath[:fullPath.rfind("\\")] + "\\data\\" + EVENT

if not os.path.exists(path):
    os.makedirs(path)

#if not os.path.exists

for match in matches:
	with open(path + "/" + match['key'][match['key'].index('_')+1:] + ".json", "w+") as file:
		file.write(json.dumps([{'name': k, 'size': v} for k,v in match.items()], indent=4) + "\n")
