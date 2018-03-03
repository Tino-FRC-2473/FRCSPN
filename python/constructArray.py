import os
#import pprint
import json
import numpy as np
#import writeMatches as w

fullPath = os.getcwd()
path = fullPath[:fullPath.rfind("\\")] + "\\data\\"

eventKeys = os.listdir(path)

selected = []
sel = -2
while len(eventKeys) > 0:
	print("\nOptions:")
	print("    " + "F: Finished")
	print("    " + "R: Remove")
	for i in range(len(eventKeys)):
		print("    " + str(i+1) + ":", eventKeys[i])
	if len(selected) > 0:
		print("Selected Events:")
		print("   ", selected)
	sel = input("Select an option: ")
	if sel == 'F':
		break
	elif sel == 'R':
		print("remove")

	elif int(sel) > len(eventKeys) or int(sel) < -1:
		print("Invalid Option.")
		continue

	selected.append(eventKeys[sel-1])
	eventKeys.remove(eventKeys[sel-1])

print("\nSelected Events:")
print("   ", selected)