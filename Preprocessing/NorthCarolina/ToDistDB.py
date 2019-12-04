import pymysql
import pandas as pd
from time import sleep
import sys
import os


data = pd.read_csv('2018_NC_gen_Precinct.csv')

#data = data[data.OfficeDesc == 'United States Representative']

user='dinnerbone'
hostname='mysql4.cs.stonybrook.edu'
passwd='changeit'
database='dinnerbone'

myConn = pymysql.connect(host=hostname, user=user, passwd=passwd, db=database)

cursor = myConn.cursor()

print(data)

numDem = 0
numRepub = 0
numOther = 0
totalVotes = 0

stateName='\'NORTH CAROLINA\''

def maxParty(a, b, c):
    if a>b:
        if a>c:
            return "'REPUBLICAN'"
        else:
            return "'OTHER'"
    else:
        if b>c:
            return "'DEMOCRATIC'"
        else:
            return "'OTHER'"

distNum = 0
def getDistNum(name):
    strs = name.split(' ')
    return str(int(float((strs[len(strs)-1]))))


for idx, elem in data.iterrows():

    if(distNum == 0):
        distNum = getDistNum(elem['Contest Name'])
    if(distNum==getDistNum(elem['Contest Name'])):
        if(elem['Choice Party'] == 'REP'):
            numRepub+=int(float(elem['Total Votes']))
        elif(elem['Choice Party'] == 'DEM'):
            numDem+=int(float(elem['Total Votes']))
        else:
            numOther+=int(float(elem['Total Votes']))
        totalVotes+=int(float(elem['Total Votes']))
    else:
        print(distNum)
        cursor.execute("insert into DistVotes(districtId, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes, winner) values('"+str(distNum)+"'," 
                + stateName + ","
                + "'2018',"+
                "'CONGRESSIONAL',"
                + str(numRepub) + ","
                + str(numDem)+","
                + str(numOther) + ","
                + str(totalVotes)+","
                + maxParty(numRepub, numDem, numOther) 
                +");")
        myConn.commit()


        distNum = getDistNum(elem['Contest Name'])
        if(elem['Choice Party'] == 'REP'):
            numRepub=int(float(elem['Total Votes']))
        elif(elem['Choice Party'] == 'DEM'):
            numDem=int(float(elem['Total Votes']))
        else:
            numOther=int(float(elem['Total Votes']))
        totalVotes=int(float(elem['Total Votes']))


print(distNum)
cursor.execute("insert into DistVotes(districtId, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes, winner) values('"+str(distNum)+"'," 
                + stateName + ","
                + "'2018',"+
                "'CONGRESSIONAL',"
                + str(numRepub) + ","
                + str(numDem)+","
                + str(numOther) + ","
                + str(totalVotes)+","
                + maxParty(numRepub, numDem, numOther) 
                +");")
myConn.commit()




myConn.close()

