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


newFile2 = pd.read_json('NC_VTD_with_neighbors.json')
myConn = pymysql.connect(host=hostname, user=username, passwd=password, db=database)
cursor = myConn.cursor()


for i in range(0, newFile2['features'].size):
    properties = newFile2['features'][i]['properties'];
    other_votes = int(properties['EL16G_PR_T'] -properties['EL16G_PR_R'] - properties['EL16G_PR_D'])   
    total = properties['EL16G_PR_T']
    

    cursor.execute("insert into Votes(precinctId, districtName, countyName,stateName, electionYear, electionName, numRepub,numDemocrat, numOther, totalVotes, winner) values ('"+
            properties['countypct']+"', '" + str(properties['newplan']) + "', '"+properties['County']+"'," +
            "'NORTH CAROLINA', '2016', 'PRESIDENT',"+
            str(properties['EL16G_PR_R'])+","+
            str(properties['EL16G_PR_D']) +","+
            str(total)+","
            +str(total) + ","+
            "'"+maxParty(properties['EL16G_PR_D'], properties['EL16G_PR_R'], other_votes)+"');" 
            )
    myConn.commit()
    print(properties['countypct'])

myConn.close()
exit()

for i in range(0, newFile2['features'].size):
    properties = newFile2['features'][i]['properties'];
    cursor.execute("insert into Precinct(precinctId, stateName, totalPop, party, whitePop, africanAmericanPop,asianPop,hispanicPop,nativeAmericanPop,neighbors) values ('"+
            properties['countypct']+"', 'North Carolina'," + 
            str(properties['TOTPOP']) +", 'REPUBLICAN', "
            + str(properties['NH_WHITE']) +","+
            str(properties['NH_BLACK']) +","+
            str(properties['NH_ASIAN']) + ","+
            str(properties['HISP']) + "," +
            str(properties['NH_AMIN'])+","+
            "\'" + properties['NEIGHBORS'] +"\');")
    myConn.commit()
    print(properties['countypct'])

myConn.close()
exit()



#myConn = pymysql.connect(host=hostname, user=username, passwd=password, db=database)
#cursor = myConn.cursor()

stateName='\'North Carolina\''
party='\'REPUBLICAN\''

NorthCarol= pd.read_csv('NC_Demographics 2017.csv')

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

for idx, row in NorthCarol.iterrows():
    for i in range(0, MAX+1):
        pop[i]+=(int(row[races[i]]))

cursor.execute("insert into State(stateName, totalPop, party, whitePop, africanAmericanPop,asianPop,hispanicPop,nativeAmericanPop) values (" + 
        stateName + "," +
        str(pop[0]) + ","+
        party + "," +
        str(pop[1])+ "," +
        str(pop[2]) + "," +
        str(pop[4]) + "," + 
        str(pop[5]) + "," +
        str(pop[3]) + ");")
myConn.commit()



#Step 2: By County


for idx, row in NorthCarol.iterrows():
    for i in range(0, MAX+1):
        pop[i]=(int(row[races[i]]))
    cursor.execute("insert into County(countyName, stateName, totalPop, whitePop, africanAmericanPop,asianPop,hispanicPop,nativeAmericanPop) values ('" + 
        str(row['Geography']).split(',')[0] +"',"+
        stateName + "," +
        str(pop[0]) + ","+
        str(pop[1])+ "," +
        str(pop[2]) + "," +
        str(pop[4]) + "," + 
        str(pop[5]) + "," +
        str(pop[3]) + ");")
    myConn.commit()
#doQuery(myConn)
myConn.close()


