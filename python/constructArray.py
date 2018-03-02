import os
#import pprint
import json
import numpy as np

fullPath = os.getcwd()
path = fullPath[:fullPath.rfind("\\")] + "\\data\\"

eventKeys = os.listdir(path)

selected = []
sel = -2
while sel != -1 and len(eventKeys) > 0:
	print("\nEvents:")
	for i in range(len(eventKeys)):
		print("    " + str(i) + ":", eventKeys[i])
	if len(selected) > 0:
		print("Selected Events:")
		print("   ", selected)
	sel = int(input("Select one by index or -1 if you are done: "))
	if sel > len(eventKeys) or sel < -1:
		print("Invalid index.")
		continue
	selected.append(eventKeys[sel])
	eventKeys.remove(eventKeys[sel])

if len(selected) > 0:
	print("\nSelected Events:")
	print("   ", selected)