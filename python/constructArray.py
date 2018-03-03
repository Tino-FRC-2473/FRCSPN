import os
#import pprint
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

allMatches = []
i = 0

for key in selected:
	fullPath = os.getcwd()
	path = fullPath[:fullPath.rfind("\\")] + "\\data\\" + key + "\\matches"
	for fName in os.listdir(path):
		allMatches.append(json.load(open(path + "\\" + fName, "r+")))

arr = np.array(allMatches)
print(arr.shape)