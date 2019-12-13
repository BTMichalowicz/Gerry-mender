import pandas as pd
import pymysql
from pathlib import Path
import sys
import os

hostname='mysql4.cs.stonybrook.edu'
username='dinnerbone'
password='changeit'
database='dinnerbone'


def maxParty(numDem, numRepub, numOther):
    if numDem>numRepub:
        if numDem > numOther:
            return "DEMOCRATIC"
        else:
            return "OTHER"
    else:
        if numRepub > numOther:
            return "REPUBLICAN"
        else:
            return "OTHER"


cong2018 = pd.read_csv('2018_Gen.csv')
myConn = pymysql.connect(host=hostname, user=username, passwd=password, db=database)
cursor = myConn.cursor()


for idx, row in cong2018.iterrows():
    print(row['precinctID'])
    other = row['totalVotes'] - row['numRepub']-row['numDemocrat']
    cursor.execute("insert into Votes(precinctId, stateName, electionYear, electionName, numRepub,numDemocrat, numOther, totalVotes, winner) values ('"+
            row['precinctID']+"',"+
            "'NORTH CAROLINA', '2018', 'CONGRESSIONAL',"+
            str(row['numRepub'])+","+
            str(row['numDemocrat']) +","+
            str(other)+","
            +str(row['totalVotes']) + ","+
            "'"+maxParty(row['numDemocrat'], row['numRepub'], other)+"');" 
            )
    myConn.commit()

    
