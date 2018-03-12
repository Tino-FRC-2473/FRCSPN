import os
import pprint
import json
import numpy as np
import writeMatches
import re

directory = os.getcwd()[:os.getcwd().rfind("\\")]

def selectEventKeys():
	path = directory + "\\data\\"

	eventKeys = os.listdir(path)

	selected = []
	sel = -2
	while True:
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
		if sel[0] == 'F':
			break
		elif sel[0] == 'W':
			writeMatches.write()
			allEvents = os.listdir(path)
			for event in allEvents:
				if event not in eventKeys and event not in selected:
					eventKeys.append(event)
					break
			continue
		elif sel[0] == 'R':
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
		elif sel[0] == 'A':
			while len(eventKeys) > 0:
				selected.append(eventKeys[0])
				eventKeys.remove(eventKeys[0])
			continue
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

	return selected

def getMatches(eventKeys):
	arr = []

	for key in eventKeys:
		path = directory + "\\data\\" + key + "\\matches"
		for fName in os.listdir(path):
			arr.append(json.load(open(path + "\\" + fName, "r+")))

	return arr

def getValue(match, key):
	for thing in match:
		if thing["name"] == key:
			return thing["size"]

# in order: B1, R1, B2, R2, B3, R3
def getTeams(match):
	arr = []
	for i in range(3):
		arr.append(getValue(match, "alliances")['blue']['team_keys'][i])
		arr.append(getValue(match, "alliances")['red']['team_keys'][i])
	return arr

def oppKey(s):
	return "opp" + s[0].upper() + s[1:]

def getTeamStats(matches):
	fullTeamData = {}
	teamData = {}

	yourStats = ["teleopSwitchOwnershipSec", "teleopScaleOwnershipSec", "autoScaleOwnershipSec", "autoScaleOwnershipSec",\
		"endgamePoints", "vaultLevitatePlayed", "vaultBoostPlayed", "vaultForcePlayed", "vaultPoints"]
	#oppStats = ["teleopSwitchOwnershipSec", "teleopScaleOwnershipSec"]
	#yourStats = ["totalPoints"]
	oppStats = []

	for match in matches:
		for i, team in enumerate(getTeams(match)):
			if not team in fullTeamData:
				fullTeamData[team], teamData[team] = {}, {}
				for yStat in yourStats:
					fullTeamData[team][yStat], teamData[team][yStat] = [], []
				for oStat in oppStats:
					fullTeamData[team][oppKey(oStat)], teamData[team][oppKey(oStat)] = [], []

			score = getValue(match, "score_breakdown")
			
			if not score == None:
				side, oppSide = "", ""
				if i % 2 == 0:
					side, oppSide = "blue", "red"
				elif i % 2 == 1:
					side, oppSide = "red", "blue"

				for yStat in yourStats:
					fullTeamData[team][yStat].append(score[side][yStat])
				for oStat in oppStats:
					fullTeamData[team][oppKey(oStat)].append(score[oppSide][oStat])
				
			else:
				print("SKIPPED MATCH:", getValue(match, "key"))

	for team, teamDict in fullTeamData.items():
		for stat, valArr in sorted(teamDict.items()):
			#teamData[team][stat].append(np.percentile(valArr, 0))
			#teamData[team][stat].append(np.percentile(valArr, 25))
			teamData[team][stat].append(np.percentile(valArr, 50))
			#teamData[team][stat].append(np.percentile(valArr, 75))
			#teamData[team][stat].append(np.percentile(valArr, 100))

			#med, iqr possible

	#pprint.pprint(teamData)
	return teamData

def buildTrainingData():
	matches = getMatches(selectEventKeys())
	teamStats = getTeamStats(matches)

	mArr = []
	rArr = []
	for i, match in enumerate(matches):
		mArr.append([])
		mArr[i].append([])
		mArr[i].append([])
		mArr[i][0].append([])
		mArr[i][0].append([])
		mArr[i][0].append([])
		mArr[i][1].append([])
		mArr[i][1].append([])
		mArr[i][1].append([])

		# mArr[i][n][m] (n is for alliances - 0: blue, 1: red) (m is from [0-2], representing the alliance team station # from [1-3])
		tms = getTeams(match)
		for j in range(2):
			for k in range(3):
				data = teamStats[tms[j + 2*k]] #casts the ([0-1], [0-2]) pairs to ([0-5])
				for _, valArr in data.items():
					for val in valArr:
						mArr[i][j][k].append(val)

		if not getValue(match, "score_breakdown") == None:
			rArr.append([])
			rArr[len(rArr)-1].append(getValue(match, "score_breakdown")["blue"]["totalPoints"])
			rArr[len(rArr)-1].append(getValue(match, "score_breakdown")["red"]["totalPoints"])

	return np.array(mArr), np.array(rArr)

def writeFile4D(npArr, path):
	with open(path, "w+") as file:
		file.write(str(npArr.shape) + "\n")
		print(npArr.shape)
		for i in range(npArr.shape[0]):
			for j in range(npArr.shape[1]):
				for m in range(npArr.shape[2]):
					for n in range(npArr.shape[3]):
						file.write(str(npArr[i][j][m][n]) + "\n")

def writeFile2D(npArr, path):
	with open(path, "w+") as file:
		file.write(str(npArr.shape) + "\n")
		print(npArr.shape)
		for i in range(npArr.shape[0]):
			for j in range(npArr.shape[1]):
				file.write(str(npArr[i][j]) + "\n")

def getTrailingNumbers(s):
    m = re.search(r'\d+$', s)
    return int(m.group()) if m else None

def writeFiles(arr4d, arr2d):
	d = os.listdir(directory + "\\neuralNetwork")
	arr = []
	for s in d:
		s1 = getTrailingNumbers(s[:-len(".txt")])
		if not s1 == None:
			arr.append(s1)
	arr.sort()
	n = 0 if len(arr) == 0 else arr[len(arr)-1] + 1

	writeFile4D(arr4d, directory + "\\neuralNetwork\\data" + str(n) + ".txt")
	writeFile2D(arr2d, directory + "\\neuralNetwork\\results" + str(n) + ".txt")

def main():
	trainingArr, resultsArr = buildTrainingData()
	'''
	pprint.pprint(trainingArr)
	print("training shape:", trainingArr.shape)

	pprint.pprint(resultsArr)
	print("results shape:", resultsArr.shape)
	'''
	writeFiles(trainingArr, resultsArr)

main()