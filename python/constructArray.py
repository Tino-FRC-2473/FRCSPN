import os
#import pprint
import json
import numpy as np

fullPath = os.getcwd()
path = fullPath[:fullPath.rfind("\\")] + "\\data\\"

eventKeys = os.listdir(path)
print("\nEvents:")
for i in range(len(eventKeys)):
	print("    " + str(i) + ":", eventKeys[i])

selected = []
sel = -2
while sel != "-1":
	sel = input("Select one by index or -1 if you are done: ")
	print(sel)