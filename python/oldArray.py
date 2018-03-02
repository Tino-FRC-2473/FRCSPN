import os
import pprint
import json
import requests
import numpy as np

allMatches = []

for folder in os.listdir("docs/matches"):
	for file in os.listdir("docs/matches/"+folder):
		if file.endswith(".json"):
			allMatches.append(json.load(open(os.path.join("docs/matches/"+folder, file))))

teams = []
with open("docs/teams.txt", "r") as f:
	for line in f:
	    teams.append(line[:len(line)-1])
#print(teams)

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
			blueScore = getValue(match, "score_breakdown")["blue"]
			redScore = getValue(match, "score_breakdown")["red"]

			'''name of match for testing'''
			#mArr.append(getValue(match, "key"))


			'''general'''
			mArr.append(blueScore["totalPoints"])
			mArr.append(redScore["totalPoints"])

			mArr.append(blueScore["adjustPoints"])
			mArr.append(redScore["adjustPoints"])

			mArr.append(blueScore["foulPoints"])
			mArr.append(redScore["foulPoints"])


			'''auto'''
			mArr.append(blueScore["autoPoints"])
			mArr.append(redScore["autoPoints"])

			mArr.append(blueScore["autoSwitchOwnershipSec"])
			mArr.append(redScore["autoSwitchOwnershipSec"])

			mArr.append(blueScore["autoScaleOwnershipSec"])
			mArr.append(redScore["autoScaleOwnershipSec"])

			mArr.append(blueScore["autoRunPoints"])
			mArr.append(redScore["autoRunPoints"])


			'''teleop'''
			mArr.append(blueScore["teleopPoints"])
			mArr.append(redScore["teleopPoints"])

			mArr.append(blueScore["teleopSwitchOwnershipSec"])
			mArr.append(redScore["teleopSwitchOwnershipSec"])
			mArr.append(blueScore["teleopSwitchBoostSec"])
			mArr.append(redScore["teleopSwitchBoostSec"])
			mArr.append(blueScore["teleopSwitchForceSec"])
			mArr.append(redScore["teleopSwitchForceSec"])

			mArr.append(blueScore["teleopScaleOwnershipSec"])
			mArr.append(redScore["teleopScaleOwnershipSec"])
			mArr.append(blueScore["teleopScaleBoostSec"])
			mArr.append(redScore["teleopScaleBoostSec"])
			mArr.append(blueScore["teleopScaleForceSec"])
			mArr.append(redScore["teleopScaleForceSec"])


			'''vault'''
			mArr.append(blueScore["vaultPoints"])
			mArr.append(redScore["vaultPoints"])

			mArr.append(blueScore["vaultBoostTotal"])
			mArr.append(redScore["vaultBoostTotal"])
			mArr.append(blueScore["vaultBoostPlayed"])
			mArr.append(redScore["vaultBoostPlayed"])

			mArr.append(blueScore["vaultForceTotal"])
			mArr.append(redScore["vaultForceTotal"])
			mArr.append(blueScore["vaultForcePlayed"])
			mArr.append(redScore["vaultForcePlayed"])

			mArr.append(blueScore["vaultLevitateTotal"])
			mArr.append(redScore["vaultLevitateTotal"])
			mArr.append(blueScore["vaultLevitatePlayed"])
			mArr.append(redScore["vaultLevitatePlayed"])


		#if len(mArr) > 0:
		tArr.append(mArr)
	data.append(tArr)
#pprint.pprint(data)

arr = np.array(data)
print(arr)
print(arr.shape)