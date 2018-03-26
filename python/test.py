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

	FNAME = "data.txt"
	s = ""
	with open(FNAME, "r") as fr:
		for line in fr:
			s += line

	with open("dataaaa.txt", "w") as fw:
		fw.write(s)

	with open("dataaaa.txt", "r") as f:
		print(json.load(f))

main()