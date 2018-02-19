import os
import pprint
import json
import requests

allMatches = []

for folder in os.listdir("docs/matches"):
	for file in os.listdir("docs/matches/"+folder):
		if file.endswith(".json"):
			allMatches.append(json.load(open(os.path.join("docs/matches/"+folder, file))))

teams = []
with open("docs/teams.txt", "r") as f:
	for line in f:
	    teams.append(line[:len(line)-1])
print(teams)

data = []

def getValue(match, key):
	for thing in match:
		if thing["name"] == key:
			return thing["size"]

def getTeams(match):
	arr = []
	for i in range(3):
		arr.append(getValue(match, "alliances")['red']['team_keys'][i])
		arr.append(getValue(match, "alliances")['blue']['team_keys'][i])
	return arr

for team in teams:
	tArr = []
	for match in allMatches:
		mArr = []
		if(team in getTeams(match)):
			#mArr.append(getValue(match, "key"))

			mArr.append(getValue(match, "score_breakdown")["blue"]["totalPoints"])
			mArr.append(getValue(match, "score_breakdown")["red"]["totalPoints"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["autoSwitchOwnershipSec"])
			mArr.append(getValue(match, "score_breakdown")["red"]["autoSwitchOwnershipSec"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["autoScaleOwnershipSec"])
			mArr.append(getValue(match, "score_breakdown")["red"]["autoScaleOwnershipSec"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["autoRunPoints"])
			mArr.append(getValue(match, "score_breakdown")["red"]["autoRunPoints"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["teleopSwitchOwnershipSec"])
			mArr.append(getValue(match, "score_breakdown")["red"]["teleopSwitchOwnershipSec"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["teleopScaleOwnershipSec"])
			mArr.append(getValue(match, "score_breakdown")["red"]["teleopScaleOwnershipSec"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["vaultBoostTotal"])
			mArr.append(getValue(match, "score_breakdown")["red"]["vaultBoostTotal"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["vaultBoostPlayed"])
			mArr.append(getValue(match, "score_breakdown")["red"]["vaultBoostPlayed"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["vaultForceTotal"])
			mArr.append(getValue(match, "score_breakdown")["red"]["vaultForceTotal"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["vaultForcePlayed"])
			mArr.append(getValue(match, "score_breakdown")["red"]["vaultForcePlayed"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["vaultLevitateTotal"])
			mArr.append(getValue(match, "score_breakdown")["red"]["vaultLevitateTotal"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["vaultLevitatePlayed"])
			mArr.append(getValue(match, "score_breakdown")["red"]["vaultLevitatePlayed"])

			mArr.append(getValue(match, "score_breakdown")["blue"]["vaultLevitateTotal"])
			mArr.append(getValue(match, "score_breakdown")["red"]["vaultLevitateTotal"])
			
		if len(mArr) > 0:
			tArr.append(mArr)
	data.append(tArr)

pprint.pprint(data)