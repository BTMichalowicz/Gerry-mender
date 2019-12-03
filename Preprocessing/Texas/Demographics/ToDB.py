import pandas as pd
import pymysql
from pathlib import Path
import sys
import os

hostname='mysql4.cs.stonybrook.edu'
username='dinnerbone'
password='changeit'
database='dinnerbone'

def doQuery(conn):
    cur = conn.cursor()
    cur.execute('select * from State')
    for item in cur.fetchall():
        print(item)
    cur.execute("select * from County where stateName='Texas'")

    for stateName in cur.fetchall():
        print(stateName[0])


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




newFile2 = pd.read_json('TX_vtds_with_neighbors.json')
myConn = pymysql.connect(host=hostname, user=username, passwd=password, db=database)
cursor = myConn.cursor()


for i in range(0, newFile2['features'].size):
    properties = newFile2['features'][i]['properties'];
    other_votes = int(properties['TOTTO14'] -properties['SEN14R'] - properties['SEN14D'])   
    total = properties['TOTTO14']
    

    cursor.execute("insert into Votes(precinctId, districtName, countyName,stateName, electionYear, electionName, numRepub,numDemocrat, numOther, totalVotes, winner) values ('"+
            properties['countypct']+"', '" + str(properties['USCD']) + "', '"+properties['COUNTY']+"'," +
            "'TEXAS', '2016', 'CONGRESSIONAL',"+
            str(properties['SEN14R'])+","+
            str(properties['SEN14D']) +","+
            str(other_votes)+","
            +str(total) + ","+
            "'"+maxParty(properties['SEN14D'], properties['SEN14R'], other_votes)+"');" 
            )
    myConn.commit()
    print(properties['countypct'])

myConn.close()
exit()












for i in range(0, newFile2['features'].size):
    properties = newFile2['features'][i]['properties'];
    cursor.execute("insert into Precinct(precinctId, countyName,stateName, totalPop, party, whitePop, africanAmericanPop,asianPop,hispanicPop,nativeAmericanPop,neighbors) values ('"+
            properties['countypct']+"','" + str(properties['COUNTY'])+"', 'Texas'," + 
            str(properties['TOTPOP']) +", 'REPUBLICAN', "
            + str(properties['WHITE']) +","+
            str(properties['BLACK']) +","+
            str(int(properties['OTHER']/2)) + ","+
            str(properties['HISPANIC']) + "," +
            str((int(properties['OTHER']/2)))+","+
            "\'" + properties['NEIGHBORS'] +"\');")
    myConn.commit()
    print(properties['countypct'])

myConn.close()
exit()

myConn = pymysql.connect(host=hostname, user=username, passwd=password, db=database)
cursor = myConn.cursor()

pop = dict()
pop[0] = 0 ##total
pop[1] = 0 ##white
pop[2] = 0 ##african american
pop[3] = 0 ##native american
pop[4] = 0 ##asian
pop[5] = 0 ##hispanic pop
i = 0

MAX=5

races = ['Estimate; Total:', 
        'Estimate; Not Hispanic or Latino: - White alone',
        'Estimate; Not Hispanic or Latino: - Black or African American alone', 
        'Estimate; Not Hispanic or Latino: - American Indian and Alaska Native alone',
        'Estimate; Not Hispanic or Latino: - Asian alone',
        'Estimate; Hispanic or Latino:']

##Step 1: Sum total for entire state
name = "\'Texas\'"
party = "\'REPUBLICAN\'"

Texas = pd.read_csv('Texas_Demographics_2017.csv')

for idx, row in Texas.iterrows():
    for i in range(0, MAX+1):
        pop[i]+=(int(row[races[i]]))

cursor.execute("insert into State(stateName, totalPop, party, whitePop, africanAmericanPop,asianPop,hispanicPop,nativeAmericanPop) values (" + 
        name + "," +
        str(pop[0]) + ","+
        party + "," +
        str(pop[1])+ "," +
        str(pop[2]) + "," +
        str(pop[4]) + "," + 
        str(pop[5]) + "," +
        str(pop[3]) + ");")
myConn.commit()



#Step 2: By County


for idx, row in Texas.iterrows():
    for i in range(0, MAX+1):
        pop[i]=(int(row[races[i]]))
    cursor.execute("insert into County(countyName, stateName, totalPop, whitePop, africanAmericanPop,asianPop,hispanicPop,nativeAmericanPop) values ('" + 
        str(row['Geography']).split(',')[0] +"',"+
        name + "," +
        str(pop[0]) + ","+
        str(pop[1])+ "," +
        str(pop[2]) + "," +
        str(pop[4]) + "," + 
        str(pop[5]) + "," +
        str(pop[3]) + ");")
    myConn.commit()
doQuery(myConn)
myConn.close()
