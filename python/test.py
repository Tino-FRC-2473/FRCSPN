import requests
import json
import os

PARAMS = {'X-TBA-Auth-Key':"gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj"}
URL = "https://www.thebluealliance.com/api/v3/"

def main():
	# rq = requests.get(url=(URL + "status"), params=PARAMS)
	# print(rq)
	# print(rq.headers["Last-Modified"])
	# print(rq.status_code)

	# next(f)

	FNAME = "match.json"
	with open(FNAME, "r") as f1:
		top = f1.readline()
		with open(FNAME, "r") as f:
			if top[0] == '{':
				print(json.load(f))
			else:
				next(f)
				print(json.load(f))

main()