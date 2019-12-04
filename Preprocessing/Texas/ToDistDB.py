import pymysql
import pandas as pd
from time import sleep
import sys
import os


data = pd.read_csv('2018_TX_gen_Precinct.csv')

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

stateName='\'TEXAS\''

def convert(string):
    str2 = string.replace(',','')
    return str2


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

    if elem['district'] == float('nan') or elem['district'] == str('') or elem['county'] == 'Dallas':
        continue
    if elem['precinct'] == 'Total':
        continue

    if(distNum == 0):
        distNum = str((int(float(convert(str(elem['district']))))))
    try:
        if(distNum== str((int(float(convert(str(elem['district']))))))):
            if(elem['party'] == 'REP' or elem['party'] == 'Republican' or elem['party'] == 'R'):
                numRepub+=int(float(convert(str(elem['votes']))))
            elif(elem['party'] == 'DEM' or elem['party'] == 'Democratic' or elem['party'] =='D'):
                numDem+=int(float(convert(str(elem['votes']))))
            else:
                numOther+=int(float(convert(str(elem['votes']))))
            totalVotes+=int(float(convert(str(elem['votes']))))
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
            distNum = str(int(float(convert(str(elem['district'])))))
            if(elem['party'] == 'REP' or elem ['party']=='Republican' or elem['party'] == 'R'):
                numRepub=int(float(convert(str(elem['votes']))))
            elif(elem['party'] == 'DEM' or elem['party']=='Democratic' or elem['party'] == 'D'):
                numDem=int(float(convert(str(elem['votes']))))
            else:
                numOther=int(float(convert(str(elem['votes']))))
            totalVotes=int(float(convert(str(elem['votes']))))
    except ValueError:
        #print("error at district: " + str(distNum))
        continue

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

