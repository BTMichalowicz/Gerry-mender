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

stateName="\'North Carolina\'"
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

def keepDistrict(string):
    str2 = string.replace('US HOUSE OF REPRESENTATIVES ', '')
    return str2

NorthCarol=pd.read_csv('2018_NC_gen_Precinct.csv')


for idx, row in NorthCarol.iterrows():
    p = row['Precinct']

    if(p =='Total' or p=='PROVISIONAL' or p=='ABSENTEE' or p=='CURBSIDE'):
        continue
#    if(row['Precinct']=='Total' or row['Precinct'] == 'Early Voting' or row['precinct'] =='Provisional'
#            or row['precinct'] =="" or row['precinct'] == 'EARLY' or
#            row['precinct'] == 'TOTALS' or row['precinct'] == 'PROV'
#            or row['precinct']=='Total EV' or row['precinct'] == 'Totals'
#            or row['precinct'] == 'TOTAL'
#            or row['precinct'] == "" or row['precinct'] == 'ABST' or row['precinct']=='Mil/Prov'
#            or row['precinct'] == 'Lim Ballot' or row['precinct'] == None):
        #print(row)
#        continue;
   # print("Test: " + str(test))
   # test+=1
    
    if(countyName!=row['County']  and countyName!=""):
        print(countyName)
    if(precinctID==""):
        precinctID="NC"+"_"+row['County']+"_"+removeStuff(str(row['Precinct']))
        #precinctID="Texas"+row['county']+str(row['precinct'])
    if ("NC"+"_"+row['County']+"_"+removeStuff(str(row['Precinct']))== precinctID):
        party = str(row['Choice Party'])
        #precinctID = str(row['precinct'])
        countyName = str(row['County'])
        #distID=str(row['district'])
        if (party=='Rep' or party == 'REP' or party=='REPUBLICAN' or party == 'Republican' or party=='Donald Trump'or party=='R'):
            voteDict['REP'] +=int(float(str(row['Total Votes']).replace(',','')))
            totalVote+=int(float(str(row['Total Votes']).replace(',','')))
        elif (party =='Dem' or party=='Democratic' or party=='DEM' or party =='DEMOCRATIC' or party =='Hillary Clinton'):
            voteDict['DEM']+=int(float(str(row['Total Votes']).replace(',','')))
            totalVote+=int(float(str(row['Total Votes']).replace(',','')))
        else:
            totalVote+=int(float(str(row['Total Votes']).replace(',','')))
            voteDict['Other']+=int(float(str(row['Total Votes']).replace(',','')))
    else:
        ##Once this isn't the case
        #print("SQL Query #" + str(i))
        i+=1
        #print(precinctID)
        #print(type(str(totalVote)))
        #sleep(1)
        total=totalVote
        cursor.execute("insert into Votes(precinctID, countyName, districtName, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes) values('"+
            str(precinctID)+ "', '"+str(countyName+" County")+"','"+
            (str(None) if distID==None else str(distID))+"',"+
            str(stateName) +",'2018','Congressional',"+
            str(voteDict['REP']) + "," + 
            str(voteDict['DEM']) + "," + 
            str(voteDict['Other'])+"," +
            str(int(total))+");")
        myConn.commit()
        totalVote=0
        voteDict['Other']=0
        voteDict['REP']=0
        voteDict['DEM']=0
        precinctID ="NC"+"_"+row['County']+"_"+removeStuff(str(row['Precinct']))
        party = str(row['Choice Party'])
        #precinctID = str(row['precinct'])
        countyName = str(row['County'])
        #distID=str(row['district'])
        if (party=='Rep' or party == 'REP' or party=='REPUBLICAN' or party == 'Republican' or party=='Donald Trump' or party=='Donald J. Trump / Mike Pence'or party=='D'):
            voteDict['REP'] += int(float(str(row['Total Votes']).replace(',','')))
            totalVote+=int(float(str(row['Total Votes']).replace(',','')))
        elif (party =='Dem' or party=='Democratic' or party=='DEM' or party =='DEMOCRATIC' or party =='Hillary Clinton' or party == 'Hillary Clinton / Tim Kaine'):
            voteDict['DEM']+=int(float(str(row['Total Votes']).replace(',','')))
            totalVote+=int(float(str(row['Total Votes']).replace(',','')))
        else:
            totalVote+=int(float(str(row['Total Votes']).replace(',','')))
            voteDict['Other']+=int(float(str(row['Total Votes']).replace(',','')))


