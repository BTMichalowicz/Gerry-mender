import pymysql as mysql
import pandas as pd
from time import sleep
import sys
import os

hostname='mysql4.cs.stonybrook.edu'
username='dinnerbone'
passwd='changeit'
database='dinnerbone'
myConn = mysql.connect(host=hostname, user=username, passwd=passwd, db=database)
cursor = myConn.cursor()

stateName="\'Texas\'"
voteDict = dict()
voteDict['REP'] = 0
voteDict['DEM'] = 0
voteDict['Other'] = 0

countyName = ""
i = 0
totalVote = 0
precinctID = ""
distID = None
test=0

def removeStuff(string):

    str2 = string.replace('precinct', '').replace('Precinct','').replace('PRECINCT','')
    str2 = str2.replace(' ','')
    return str2

Texas = pd.read_csv('2018_TX_gen_Precinct.csv')

for idx, row in Texas.iterrows():
    if(row['precinct']=='Total' or row['precinct'] == 'Early Voting' or row['precinct'] =='Provisional'
            or row['precinct'] =="" or row['precinct'] == 'EARLY' or
            row['precinct'] == 'TOTALS' or row['precinct'] == 'PROV'
            or row['precinct']=='Total EV' or row['precinct'] == 'Totals'
            or row['precinct'] == 'TOTAL'
            or row['precinct'] == "" or row['precinct'] == 'ABST' or row['precinct']=='Mil/Prov'
            or row['precinct'] == 'Lim Ballot' or row['precinct'] == None):
        #print(row)
        continue;
   # print("Test: " + str(test))
   # test+=1
    
    if(countyName!=row['county']  and countyName!=""):
        print(countyName)
    if(precinctID==""):
        precinctID="Texas"+"_"+row['county']+"_"+removeStuff(str(row['precinct']))
        #precinctID="Texas"+row['county']+str(row['precinct'])
    if ("Texas"+"_"+row['county']+"_"+removeStuff(str(row['precinct']))== precinctID):
        party = str(row['party'])
        #precinctID = str(row['precinct'])
        countyName = str(row['county'])
        distID=str(row['district'])
        if (party=='Rep' or party == 'REP' or party=='REPUBLICAN' or party == 'Republican' or party=='Donald Trump'or party=='R'):
            voteDict['REP'] +=int(float(str(row['votes']).replace(',','')))
            totalVote+=int(float(str(row['votes']).replace(',','')))
        elif (party =='Dem' or party=='Democratic' or party=='DEM' or party =='DEMOCRATIC' or party =='Hillary Clinton'):
            voteDict['DEM']+=int(float(str(row['votes']).replace(',','')))
            totalVote+=int(float(str(row['votes']).replace(',','')))
        else:
            totalVote+=int(float(str(row['votes']).replace(',','')))
            voteDict['Other']+=int(float(str(row['votes']).replace(',','')))
    else:
        ##Once this isn't the case
        #print("SQL Query #" + str(i))
        i+=1
        cursor.execute("insert into Votes(precinctID, countyName, districtName, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes) values('"+
            str(precinctID)+ "', '"+str(countyName+" County")+"','"+
            (None if distID==None else str(distID))+"',"+
            str(stateName) +",'2018','Congressional',"+
            str(voteDict['REP']) + "," + 
            str(voteDict['DEM']) + "," + 
            str(voteDict['Other'])+"," +
            str(totalVote)+");")
        myConn.commit()
        totalVote=0
        voteDict['Other']=0
        voteDict['REP']=0
        voteDict['DEM']=0
        precinctID ="Texas"+"_"+row['county']+"_"+removeStuff(str(row['precinct']))
        party = str(row['party'])
        #precinctID = str(row['precinct'])
        countyName = str(row['county'])
        distID=str(row['district'])
        if (party=='Rep' or party == 'REP' or party=='REPUBLICAN' or party == 'Republican' or party=='Donald Trump' or party=='Donald J. Trump / Mike Pence'or party=='D'):
            voteDict['REP'] += int(float(str(row['votes']).replace(',','')))
            totalVote+=int(float(str(row['votes']).replace(',','')))
        elif (party =='Dem' or party=='Democratic' or party=='DEM' or party =='DEMOCRATIC' or party =='Hillary Clinton' or party == 'Hillary Clinton / Tim Kaine'):
            voteDict['DEM']+=int(float(str(row['votes']).replace(',','')))
            totalVote+=int(float(str(row['votes']).replace(',','')))
        else:
            totalVote+=int(float(str(row['votes']).replace(',','')))
            voteDict['Other']+=int(float(str(row['votes']).replace(',','')))







#for idx, row in Texas.iterrows():
#    if row['county']=='Zavala':
#        if(precinctID==""):
#            precinctID=str(row['precinct'])
#        if (str(row['precinct'])== precinctID):
#            party = str(row['party'])
#            #precinctID = str(row['precinct'])
#            countyName = str(row['county'])
#            distID=str(row['district'])
#            if (party=='Rep' or party == 'REP' or party=='REPUBLICAN' or party == 'Republican' or party=='Donald Trump'or party=='R'):
#                voteDict['REP'] = int(str(row['votes']).replace(',',''))
#                totalVote+=int(str(row['votes']).replace(',',''))
#            elif (party =='Dem' or party=='Democratic' or party=='DEM' or party =='DEMOCRATIC' or party =='Hillary Clinton'):
#                voteDict['DEM']=int(str(row['votes']).replace(',',''))
#                totalVote+=int(str(row['votes']).replace(',',''))
#            else:
#                totalVote+=int(str(row['votes']).replace(',',''))
#                voteDict['Other']+=int(str(row['votes']).replace(',',''))
#        else:
#        ##Once this isn't the case
#        #print("SQL Query #" + str(i))
#            i+=1
#            cursor.execute("insert into Votes(precinctID, countyName, districtName, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes) values('"+
#            str(precinctID)+ "', '"+str(countyName+" County")+"','"+
#            (None if distID==None else str(distID))+"',"+
#            str(stateName) +",'2016','President',"+
#            str(voteDict['REP']) + "," + 
#            str(voteDict['DEM']) + "," + 
#            str(voteDict['Other'])+"," +
#            str(totalVote)+");")
#            myConn.commit()
#            totalVote=0
#            voteDict['Other']=0
#            precinctID = str(row['precinct'])
#            party = str(row['party'])
#        #precinctID = str(row['precinct'])
#            countyName = str(row['county'])
#            distID=str(row['district'])
#        if (party=='Rep' or party == 'REP' or party=='REPUBLICAN' or party == 'Republican' or party=='Donald Trump' or party=='Donald J. Trump / Mike Pence'or party=='D'):
#            voteDict['REP'] = int(str(row['votes']).replace(',',''))
#            totalVote+=int(str(row['votes']).replace(',',''))
#        elif (party =='Dem' or party=='Democratic' or party=='DEM' or party =='DEMOCRATIC' or party =='Hillary Clinton' or party == 'Hillary Clinton / Tim Kaine'):
#            voteDict['DEM']=int(str(row['votes']).replace(',',''))
#            totalVote+=int(str(row['votes']).replace(',',''))
#        else:
#            totalVote+=int(str(row['votes']).replace(',',''))
#            voteDict['Other']+=int(str(row['votes']).replace(',',''))
#




#   
#    #party = row['party']
#    if(precinctID != str(row['precinct']) and precinctID!= ""):
#        if i>0:
#            cursor.execute("insert into Votes(precinctID, countyName, districtName, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes) values('"+
#                    str(precinctID)+ "', '"+str(countyName+" County")+"','"+
#                    (None if distID==None else str(distID))+"',"+
#                    str(stateName) +",'2016','President',"+
#                    str(voteDict['REP']) + "," + 
#                    str(voteDict['DEM']) + "," + 
#                    str(voteDict['Other'])+"," +
#                    str(totalVote)+");")
#            myConn.commit()
#            totalVote=0
#            voteDict['Other']=0
#
#            ##Insertion
#        #else
#            ##Nothing
#    else:
#        if(i ==0):
#            print("Made it to else")
#        i+=1
#        uparty = str(row['party'])
#        precinctID = str(row['precinct'])
#        countyName = str(row['county'])
#        distID=str(row['district'])
#        if (party=='Rep' or party == 'REP' or party=='REPUBLICAN' or party == 'Republican' or party=='Donald Trump'):
#            voteDict['REP'] = int(row['votes'])
#            totalVote+=int(row['votes'])
#        elif (party =='Dem' or party=='Democratic' or party=='DEM' or party =='DEMOCRATIC' or party =='Hillary Clinton'):
#            voteDict['DEM']=int(row['votes'])
#            totalVote+=int(row['votes'])
#        else:
#            totalVote+=int(row['votes'])
#            voteDict['Other']+=int(row['votes'])
#
## Commit insertion query
myConn.close()
