import requests
import sys

PARAMS = {'X-TBA-Auth-Key':"gSLmkXgiO6HobgtyYwb6CHyYs9KnKvJhl9F7pBXfokT3D9fcczt44lLgvh3BICzj"}
print("before")
r = requests.get(url = "https://www.thebluealliance.com/api/v3/events/" + sys.argv[1] + "/keys", params = PARAMS)
print("after")
data = r.json()
print(data)

# try:
#     page1 = requests.get(ap)
# except requests.exceptions.ConnectionError:
#     r.status_code = "Connection refused"