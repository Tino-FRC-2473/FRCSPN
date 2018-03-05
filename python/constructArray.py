import os
import pprint
import json
import numpy as np

fullPath = os.getcwd()
path = fullPath[:fullPath.rfind("\\")] + "\\data\\"

eventKeys = os.listdir(path)

selected = []
sel = -2
while len(eventKeys) > 0:
	print("")
	if len(selected) > 0:
		print("Selected Events:")
		print("   ", selected)
	print("Options:")
	print("    F: Finished")
	print("    W: Write/Request Matches")
	print("    R: Remove One")
	print("    A: Add All")
	for i in range(len(eventKeys)):
		print("    " + str(i+1) + ": Add", eventKeys[i])
	sel = input("Select an option: ")
	if sel == 'F':
		break
	elif sel == 'W':
		import writeMatches
		continue
	elif sel == 'R':
		if(len(selected) > 0):
			print("    Options:")
			print("        0:", "Exit Removing")
			for i in range(len(selected)):
				print("        " + str(i+1) + ":", selected[i])
			sel2 = int(input("    Select an option: "))
			if sel2 == 0:
				continue
			try:
				eventKeys.append(selected[sel2-1])
				selected.remove(selected[sel2-1])
			except IndexError:
				print("Index out of bounds.")
		else:
			print("    Nothing to Remove.")
		continue
	elif sel == 'A':
		while len(eventKeys) > 0:
			selected.append(eventKeys[0])
			eventKeys.remove(eventKeys[0])
		break
	else:
		try:
			sel = int(sel)
		except ValueError:
			print("Invalid Letter Selected.")
			continue

	if sel > len(eventKeys) or sel <= 0:
		print("Invalid Index Selected.")
	else:
		selected.append(eventKeys[sel-1])
		eventKeys.remove(eventKeys[sel-1])

print("\nSelected Events:")
print("   ", selected)
print("")

matches = []
i = 0

for key in selected:
	fullPath = os.getcwd()
	path = fullPath[:fullPath.rfind("\\")] + "\\data\\" + key + "\\matches"
	for fName in os.listdir(path):
		matches.append(json.load(open(path + "\\" + fName, "r+")))

def getValue(match, key):
	for thing in match:
		if thing["name"] == key:
			return thing["size"]

def getTeams(match):
	arr = []
	for i in range(3):
		arr.append(getValue(match, "alliances")['blue']['team_keys'][i])
		arr.append(getValue(match, "alliances")['red']['team_keys'][i])
	return arr

fullTeamData = {}
teamData = {}

for match in matches:
	for i, team in enumerate(getTeams(match)):
		if not team in fullTeamData:
			fullTeamData[team] = {"switch": [], "scale": [], "oppSwitch": [], "auto": [], "endgame": []}
			teamData[team] = {"switch": [], "scale": [], "oppSwitch": [], "auto": [], "endgame": []}
		score = getValue(match, "score_breakdown")

		side, oppSide = "", ""
		if i % 2 == 0:
			side, oppSide = "blue", "red"
		elif i % 2 == 1:
			side, oppSide = "red", "blue"

		fullTeamData[team]["switch"].append(score[side]["teleopSwitchOwnershipSec"])
		fullTeamData[team]["scale"].append(score[side]["teleopScaleOwnershipSec"])
		fullTeamData[team]["oppSwitch"].append(score[oppSide]["teleopSwitchOwnershipSec"])
		fullTeamData[team]["auto"].append(score[side]["autoPoints"])
		fullTeamData[team]["endgame"].append(score[side]["endgamePoints"])

		#if team == 'frc811':
			#print(getValue(match, "key"), i, side, score[side]["autoPoints"], score[side]["endgamePoints"])


pprint.pprint(teamData)

arr = []
for i, match in enumerate(matches):
	arr.append([])
	arr[i].append([])
	arr[i].append([])
	arr[i][0].append([])
	arr[i][0].append([])
	arr[i][0].append([])
	arr[i][1].append([])
	arr[i][1].append([])
	arr[i][1].append([])

	# arr[i][n][m] (n is for alliances - 0: blue, 1: red) (m is the team # from 1-3 on that alliance)

	for j in range(2):
		for k in range(3):
			#team = match.
			arr[i][j][k].append(0)


data = np.array(arr)
#pprint.pprint(data)
print(data.shape)