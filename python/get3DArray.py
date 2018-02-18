import os
import pprint
import json

files = []

for folder in os.listdir("docs"):
	for file in os.listdir("docs/"+folder):
		if file.endswith(".json"):
			files.append(os.path.join("docs/"+folder, file))

for file in files:
	match = json.load(open(file))
	pprint.pprint(match)
