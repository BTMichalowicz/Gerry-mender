import pymysql
import pandas as pd
from time import sleep
import sys
import os


data = pd.read_csv('2018Gen.csv')[['Juris1num','CountyName','CountyCode','OfficeDesc','CanVotes','PartyCode', 'PartyName']]

data = data[data.OfficeDesc == 'United States Representative']

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

stateName='\'FLORIDA\''

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


for idx, elem in data.iterrows():

    if(distNum == 0):
        distNum = int(float(elem['Juris1num']))
    if(distNum==elem['Juris1num']):
        if(elem['PartyCode'] == 'REP'):
            numRepub+=int(float(elem['CanVotes']))
        elif(elem['PartyCode'] == 'DEM'):
            numDem+=int(float(elem['CanVotes']))
        else:
            numOther+=int(float(elem['CanVotes']))
        totalVotes+=int(float(elem['CanVotes']))
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


        distNum = int(float(elem['Juris1num']))
        if(elem['PartyCode'] == 'REP'):
            numRepub=int(float(elem['CanVotes']))
        elif(elem['PartyCode'] == 'DEM'):
            numDem=int(float(elem['CanVotes']))
        else:
            numOther+=int(float(elem['CanVotes']))
        totalVotes=int(float(elem['CanVotes']))


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

