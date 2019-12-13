import pymysql as mysql
import pandas as pd
from time import sleep
import sys
import os

hostname='mysql4.cs.stonybrook.edu'
username='dinnerbone'
passwd='changeit'
database='dinnerbone'

def doQuery(conn):
    cur = conn.cursor()
    cur.execute("Select * from Votes;")
    for res in cur.fetchall():
        print(res)


def removeStuff(string):

    str2 = string.replace('precinct', '').replace('Precinct','').replace('PRECINCT','')
    str2 = str2.replace('-',' ').replace('  ', ' ').replace(' ','-')
    return str2



newFile = pd.read_csv("2018_FL_gen_Precinct.csv")
myConn = mysql.connect(host=hostname, user=username, passwd=passwd, db=database)
cursor = myConn.cursor()

stateName = "\'Florida\'"

voteDict = dict()

voteDict['REP'] = 0;
voteDict['DEM'] = 0;
voteDict['Other'] = 0;

countName = ""

#MAX = 2
i = 0 ##counter
isCommit = False
precinctID = ""
totalVote = 0
distID = None
countyName=""


for idx, row in newFile.iterrows():
    if(countName!= str(row['CountyName']) and countName != ""):
            print(countName)


    if(countyName!=row['CountyName']  and countyName!=""):
        print(countyName)
    if(precinctID==""):
        precinctID="Florida_"+ row['CountyCode'] + "_" + removeStuff(str(row['UniquePrecId']))
        countyName=str(row['CountyName'])
    if ("Florida_"+ row['CountyCode'] + "_" + removeStuff(str(row['UniquePrecId']))== precinctID):
        party = str(row['CandParty'])
        #precinctID = str(row['precinct'])
        #countyName = str(row['CountyName'])
        distID=str(row['Dist'])
        if (party=='Rep' or party == 'REP' or party=='REPUBLICAN' or party == 'Republican' or party=='Donald Trump'or party=='R'):
            voteDict['REP'] +=int(float(str(row['VoteTotal']).replace(',','')))
            #totalVote+=int(float(str(row['VoteTotal']).replace(',','')))
        elif (party =='Dem' or party=='Democratic' or party=='DEM' or party =='DEMOCRATIC' or party =='Hillary Clinton'):
            voteDict['DEM']+=int(float(str(row['VoteTotal']).replace(',','')))
            #totalVote+=int(float(str(row['VoteTotal']).replace(',','')))
        else:
            #totalVote+=int(float(str(row['VoteTotal']).replace(',','')))
            voteDict['Other']+=int(float(str(row['VoteTotal']).replace(',','')))
    elif (str(row['UniquePrecId'])!=precinctID):
        cursor.execute("insert into Votes(precinctID, countyName,districtName, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes) values ('" +
                str(precinctID) + "', '"+str(countyName)+"', '"+
                (None if distID == None else str(distID)) + "',"+
                str(stateName)+",'2018' , 'Congressional',"+
                str(voteDict['REP']) + "," + 
                str(voteDict['DEM']) + "," + 
                str(voteDict['Other'])+"," +
                str(totalVote)+");")
        #isCommit=False
        myConn.commit()
        voteDict['Other'] = 0
        voteDict['REP']=0
        voteDict['DEM']=0
        totalVote=int(row['TotalRegVot'])
        distID = str(row['Dist'])
        precinctID="Florida_"+ row['CountyCode'] + "_" + removeStuff(str(row['UniquePrecId']))
        party = str(row['CandParty'])
        #precinctID = str(row['precinct'])
        countyName = str(row['CountyName'])
        distID=str(row['Dist'])
        if (party=='Rep' or party == 'REP' or party=='REPUBLICAN' or party == 'Republican' or party=='Donald Trump'or party=='R'):
            voteDict['REP'] +=int(float(str(row['VoteTotal']).replace(',','')))
            #totalVote+=int(float(str(row['VoteTotal']).replace(',','')))
        elif (party =='Dem' or party=='Democratic' or party=='DEM' or party =='DEMOCRATIC' or party =='Hillary Clinton'):
            voteDict['DEM']+=int(float(str(row['VoteTotal']).replace(',','')))
            #totalVote+=int(float(str(row['VoteTotal']).replace(',','')))
        else:
            #totalVote+=int(float(str(row['VoteTotal']).replace(',','')))
            voteDict['Other']+=int(float(str(row['VoteTotal']).replace(',','')))

cursor.execute("insert into Votes(precinctID, countyName,districtName, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes) values ('" +
                str(precinctID) + "', '"+str(countyName)+"', '"+
                (None if distID == None else str(distID)) + "',"+
                str(stateName)+",'2018' , 'Congressional',"+
                str(voteDict['REP']) + "," + 
                str(voteDict['DEM']) + "," + 
                str(voteDict['Other'])+"," +
                str(totalVote)+");")
        #isCommit=False
myConn.commit()


doQuery(myConn)
myConn.close()




