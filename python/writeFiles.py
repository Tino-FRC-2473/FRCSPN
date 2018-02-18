import requests
import json
import os

PARAMS = {'X-TBA-Auth-Key':"gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj"}
URL = "https://www.thebluealliance.com/api/v3/"

EVENT = "week0"

matches = requests.get(url=(URL + "event/2018" + EVENT + "/matches"), params=PARAMS).json()

if not os.path.exists("docs/" + EVENT):
    os.makedirs("docs/" + EVENT)

for match in matches:
	with open("docs/" + EVENT + "/" + match['key'][match['key'].index('_')+1:] + ".json", "w+") as file:
		s = json.dumps([{'name': k, 'size': v} for k,v in match.items()], indent=4)
		file.write(s + "\n")