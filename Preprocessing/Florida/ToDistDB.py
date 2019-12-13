import pymysql
import pandas as pd
from time import sleep
import sys
import os


data = pd.read_csv('2016_FL_gen_Precinct.csv')#[['Juris1num','CountyName','CountyCode','OfficeDesc','CanVotes','PartyCode', 'PartyName']]

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
        distNum = int(float(elem['Dist']))
    if(distNum==elem['Dist']):
        if(elem['CandParty'] == 'REP'):
            numRepub+=int(float(elem['VoteTotal']))
        elif(elem['CandParty'] == 'DEM'):
            numDem+=int(float(elem['VoteTotal']))
        else:
            numOther+=int(float(elem['VoteTotal']))
        totalVotes+=int(float(elem['VoteTotal']))
    else:
        print(distNum)
        cursor.execute("insert into DistVotes(districtId, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes, winner) values('"+str(distNum)+"'," 
                + stateName + ","
                + "'2016',"+
                "'CONGRESSIONAL',"
                + str(numRepub) + ","
                + str(numDem)+","
                + str(numOther) + ","
                + str(totalVotes)+","
                + maxParty(numRepub, numDem, numOther) 
                +");")
        myConn.commit()
        numRepub = 0
        numDem = 0
        numOther = 0
        totalVotes = 0


        distNum = int(float(elem['Dist']))
        if(elem['CandParty'] == 'REP'):
            numRepub=int(float(elem['VoteTotal']))
        elif(elem['CandParty'] == 'DEM'):
            numDem=int(float(elem['VoteTotal']))
        else:
            numOther+=int(float(elem['VoteTotal']))
        totalVotes=int(float(elem['VoteTotal']))


print(distNum)
cursor.execute("insert into DistVotes(districtId, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes, winner) values('"+str(distNum)+"'," 
                + stateName + ","
                + "'2016',"+
                "'CONGRESSIONAL',"
                + str(numRepub) + ","
                + str(numDem)+","
                + str(numOther) + ","
                + str(totalVotes)+","
                + maxParty(numRepub, numDem, numOther) 
                +");")
myConn.commit()




myConn.close()

