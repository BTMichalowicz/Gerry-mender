import pandas as pd
from pathlib import Path
import sys
import os
from time import sleep
import pymysql

hostname='mysql4.cs.stonybrook.edu'
username='dinnerbone'
password='changeit'
database='dinnerbone'

def doQuery(conn):
    cur = conn.cursor()
    cur.execute("select * from County")

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



newFile = pd.read_csv("SF1-FL-Cntys-age-race-sex-hispanic.csv")



newFile2 = pd.read_json('fl_neighbors_pop (1).json')
myConn = pymysql.connect(host=hostname, user=username, passwd=password, db=database)
cursor = myConn.cursor()


for i in range(0, newFile2['features'].size):
    properties = newFile2['features'][i]['properties'];
    other_votes = int(properties['G16USSLSta'] + properties['G16USSOth'])   
    total = other_votes +  properties['G16USSRRub'] +  properties['G16USSDMur']


    cursor.execute("insert into Votes(precinctId, countyName,stateName, electionYear, electionName, numRepub,numDemocrat, numOther, totalVotes, winner) values ('"+
            properties['countypct']+"', '"+properties['county']+"'," +
            "'FLORIDA', '2016', 'CONGRESSIONAL',"+
            str(properties['G16USSRRub'])+","+
            str(properties['G16USSDMur']) +","+
            str(int(properties['G16USSLSta'] + properties['G16USSOth'] ))+","
            +str(total) + ","+
            "'"+maxParty(properties['G16USSDMur'], properties['G16USSRRub'], other_votes)+"');" 
            )
    myConn.commit()
    print(properties['countypct'])

myConn.close()
exit()



    #print(len(feature['geometry']['coordinates'][0]));
    #exit();i

##DB Connection
name = "Florida"
party="REPUBLICAN"
pop = dict()
pop[0] = 0 ##Total
pop[1] = 0 ##White
pop[2] = 0 ##African American
pop[3] = 0 ##Native American
pop[4] = 0 ##Asian
pop[5] = 0 ##Hispanic Pop
i = 0

MAX=5

races = ['Total', 'White Alone', 'Black or African American Alone', 'Asian Alone', 'Hispanic or Latino of Any Race', 'American Indian and Alaska Native Alone']

#for idx, row in newFile.iterrows():
#    if i>MAX:
#        break
#    if row['Race / Ethnicity'] in races:
#        if row['Area Name'] == name:
#            pop[i] = (int(row['Total'].replace(',','')))
#            i+=1
#        else: 
#            continue
#

#for elem in pop.values():
#    print("Pop: " + str(elem))
#    sleep(4)
    #Florida Loop

#cursor.execute("use dinnerbone;")
#cursor.execute("insert into State(stateName, totalPop, party, whitePop, africanAmericanPop,asianPop, hispanicPop, nativeAmericanPop) values ('Florida'," + str(pop[0]) +",'REPUBLICAN', " + str(pop[1]) + "," + str(pop[2]) + "," + str(pop[4])+ "," + str(pop[5]) + "," + str(pop[3])+");")
#myConn.commit()

#for idx, row in newFile.iterrows():
#    row2 = row['Area Name']
#    print(row2)
#    print("\n")
#    sleep(10)
#



newSetup = newFile[newFile['Area Name'] != 'Florida']
#print(newSetup['Area Name'])
name = ""
isCommit = False
for idx, row in newSetup.iterrows():
    if row['Race / Ethnicity'] == 'Total':
        i = 0
        name = row['Area Name']
        print("County: " + name)
        isCommit = False
        pop[i] = (int(row['Total'].replace(',','')))
        i+=1
    else:
        if isCommit:
            continue
        else:
            if row['Race / Ethnicity'] in races:
                pop[i] = (int(row['Total'].replace(',','')))
                i+=1
            if i > MAX:
                isCommit = True
                cursor.execute("insert into County(countyName, stateName, totalPop, whitePop, africanAmericanPop, asianPop, hispanicPop, nativeAmericanPop) VALUES ('" + name + "', 'Florida'," + str(pop[0]) + "," + str(pop[1]) + "," + str(pop[2]) + ","+str(pop[4]) + "," + str(pop[5]) + "," + str(pop[3]) + ");")
                myConn.commit() ## Commit the connection




doQuery(myConn)
myConn.close()
#SF1-FL-Cntys-age-race-sex-hispanic.csv
