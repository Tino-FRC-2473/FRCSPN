import sys
import os
import json
import pprint
import numpy as np

# python writeStatsForNN.py 118-txda,code 148-txda,code 1678-cada,utwv 254-azfl 1619-txlu,code 2767-misjo

directory = os.getcwd()[:os.getcwd().rfind("\\")]

def getLeadingNumber(s):
	toReturn = ""
	for i in range(len(s)):
		try:
			toReturn += str(int(s[i]))
		except ValueError:
			return toReturn

def getPath(s):
	if "FRCSPN" in s:
		return s
	else:
		return s[:s.index("data")] + "FRCSPN\\" + s[s.index("data"):]

def getMatches(eventKeys):
	arr = []

	for i in range(len(eventKeys)):
#		print("Loading event", i+1, "of", len(eventKeys))
		path = getPath(directory + "\\data\\" + eventKeys[i] + "\\matches")
		if os.path.isdir(path):
			for fName in os.listdir(path):
				with open(path + "\\" + fName, "r") as f1:
					_line1 = f1.readline()
					with open(path + "\\" + fName, "r") as f:
						data = None
						if _line1[0] == '{':
							data = json.load(f)
							print(eventKeys[i])
						else:
							next(f)
							data = json.load(f)
						if not data == None and not data["score_breakdown"] == None:
							arr.append(data)
	return arr

def getFilteredMatches(eventKeys):
	matches = getMatches(eventKeys)
	for i in range(len(matches)):
#		print("Filtering match", i+1, "of", len(matches))
		match = matches[i]
		for stat in list(match["score_breakdown"]["blue"].keys()):
			try:
				float(match["score_breakdown"]["blue"][stat])
			except ValueError:
				if stat[:-1] == "endgameRobot":
					match["score_breakdown"]["blue"][stat] = (1 if match["score_breakdown"]["blue"][stat] == "Climbing" else 0)
					match["score_breakdown"]["red"][stat] = (1 if match["score_breakdown"]["red"][stat] == "Climbing" else 0)
				else:
					del match["score_breakdown"]["blue"][stat]
					del match["score_breakdown"]["red"][stat]

	return matches

def getTeamMatchesDict(eventKeys):
	matches = getFilteredMatches(eventKeys)

	matchesDict = {}

	for match in matches:
		teams = getTeams(match)
		for team in teams:
			if not team in matchesDict:
				matchesDict[team] = {}
				matchesDict[team]["matches"] = []
			matchesDict[team]["matches"].append({"teams": teams, "score": match["score_breakdown"]})

	for tDict in matchesDict.values():
		tDict["toMerge"] = []
		length = len(tDict["matches"])
		for i in range(length-1):
			for j in range(i+1, length):
				if set(tDict["matches"][i]["teams"]) == set(tDict["matches"][j]["teams"]):
					found = False
					for arr in tDict["toMerge"]:
						if i in arr or j in arr:
							found = True
							if i in arr and j not in arr:
								arr.append(j)
								break
							elif j in arr and i not in arr:
								arr.append(i)
								break
					if not found:
						tDict["toMerge"].append([i, j])

	for tDict in matchesDict.values():
		for merge in tDict["toMerge"]:
			replace, allReplace = {"blue": {}, "red": {}}, {"blue": {}, "red": {}}
			for stat in tDict["matches"][0]["score"]["blue"].keys():
				allReplace["blue"][stat], allReplace["red"][stat] = [], []

			for i in merge:
				for stat in tDict["matches"][i]["score"]["blue"].keys():
					allReplace["blue"][stat].append(tDict["matches"][i]["score"]["blue"][stat])
					allReplace["red"][stat].append(tDict["matches"][i]["score"]["red"][stat])
			for stat in allReplace["blue"].keys():
				replace["blue"][stat], replace["red"][stat] = np.mean(allReplace["blue"][stat]), np.mean(allReplace["red"][stat])

			first = True
			for i in merge:
				if first:
					first = False
					tDict["matches"][i]["score"] = replace
				else:
					tDict["matches"][i]["score"] = -1

		i = 0
		while i < len(tDict["matches"]):
			if tDict["matches"][i]["score"] == -1:
				del tDict["matches"][i]
			else:
				i+=1

		del tDict["toMerge"]

	return matchesDict

# in order: B1, R1, B2, R2, B3, R3
def getTeams(match):
	arr = []
	for i in range(3):
		arr.append(match["alliances"]['blue']['team_keys'][i])
		arr.append(match["alliances"]['red']['team_keys'][i])
	return arr

def oppKey(s):
	return "opp" + s[0].upper() + s[1:]

def getTeamStats(teamMatches):
	fullTeamData = {}
	teamData = {}

	yourStats, oppStats = [], []
	doEndgame = True
#	yourStats = ["totalPoints"]
#	yourStats = ["teleopSwitchOwnershipSec", "teleopScaleOwnershipSec", "autoScaleOwnershipSec", "autoSwitchOwnershipSec",\
#		"endgamePoints", "vaultLevitatePlayed", "vaultBoostPlayed", "vaultForcePlayed", "vaultPoints"]
	yourStats = ["teleopSwitchOwnershipSec", "teleopScaleOwnershipSec", "autoScaleOwnershipSec", "autoSwitchOwnershipSec",\
		"vaultLevitatePlayed", "vaultBoostPlayed", "vaultForcePlayed", "vaultPoints"]
#	oppStats = ["teleopSwitchOwnershipSec", "teleopScaleOwnershipSec"]

	for team, tDict in teamMatches.items():
		fullTeamData[team], teamData[team] = {}, {}

		if doEndgame:
			fullTeamData[team]["endgameRobot"], teamData[team]["endgameRobot"] = [], []
		for yStat in yourStats:
			fullTeamData[team][yStat], teamData[team][yStat] = [], []

			for match in tDict["matches"]:
				side = "blue" if (match["teams"].index(team) % 2 == 0) else "red"
				fullTeamData[team][yStat].append(match["score"][side][yStat])
				if doEndgame:
					fullTeamData[team]["endgameRobot"].append(match["score"][side]["endgameRobot"+str(int(match["teams"].index(team)/2)+1)])

		for oStat in oppStats:
			fullTeamData[team][oppKey(oStat)], teamData[team][oppKey(oStat)] = [], []

			for match in tDict["matches"]:
				oppSide = "red" if (match["teams"].index(team) % 2 == 0) else "blue"
				fullTeamData[team][oppKey(oStat)].append(match["score"][oppSide][oStat])

	for team, teamDict in fullTeamData.items():
		for stat, valArr in sorted(teamDict.items()):
#			teamData[team][stat].append(np.percentile(valArr, 0))
#			teamData[team][stat].append(np.percentile(valArr, 25))
			teamData[team][stat].append(np.percentile(valArr, 50))
#			teamData[team][stat].append(np.percentile(valArr, 75))
#			teamData[team][stat].append(np.percentile(valArr, 100))

	return teamData

def getTeamsAndEventKeysFromSys():
	teams = []
	eventKeys = []

	print(sys.argv[1:])
	for thing in sys.argv[1:]:
		teams.append(getLeadingNumber(thing))
		for eKey in thing[len(str(teams[len(teams)-1]))+1:].split(","):
			if ("2018"+eKey) not in eventKeys:
				eventKeys.append("2018"+eKey)

	return teams, eventKeys

def main():
	teams, eventKeys = getTeamsAndEventKeysFromSys()
	print("teams", teams)
	print("event keys", eventKeys)

	teamStats = getTeamStats(getTeamMatchesDict(eventKeys))
#	pprint.pprint(teamStats)

	arr = []
	print("tStat keys", list(teamStats.keys()))
	for team in teams:
		print("getting keys for", team)
		print(teamStats["frc"+team])
		orderKeys = sorted(teamStats["frc"+team])
		print("good")
		for thing in orderKeys:
			arr.append(teamStats["frc"+team][thing][0])
	print("got through reading through teamStats")
	with open(("MatchInput.txt" if ("FRCSPN" in directory) else "neuralNetwork\\MatchInput.txt"), "w") as f:
		for n in arr:
			f.write(str(n) + "\n")
	print("!")

main()
