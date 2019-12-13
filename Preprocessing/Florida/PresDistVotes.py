import geopandas as pd
from time import sleep
import sys
import os
import pymysql

from shapely.geometry import Polygon, MultiPolygon

host='mysql4.cs.stonybrook.edu'
user='dinnerbone'
passwd='changeit'
db=user
myConn = pymysql.connect(user=user,db=db,passwd=passwd,host=host)
cursor = myConn.cursor()



distFile = pd.read_file('FL.shp')
precFile = pd.read_file('Florida_precincts.shp')

i = 0 
j = 0

def maximumParty(Dem, Repub, Other):
    if (Dem>Repub):
        if(Dem>Other):
            return "DEMOCRATIC"
        else:
            return "OTHER"
    else:
        if (Repub>Other):
            return "REPUBLICAN"
        else:
            return "OTHER"


#print(distFile['features'][0]['geometry'])
#exit()

numDem = 0
numRepub = 0
numOther = 0
totalVotes = 0

stateName='\'FLORIDA\''
electionName='\'PRESIDENT\''
electionYear='\'2016\''

for i in range(0, 27):
    district = distFile.loc[i]
    distGeom = None
    distGeom = district['geometry']
    print(district['DISTRICT'])
    for j in range(0, 5966):
        precinct = precFile.loc[j]
        precinctGeom = precinct['geometry']

        if precinctGeom.intersects(distGeom):
            #print(precinct['countypct'])
            numDem+=precinct['G16PREDCli']
            numRepub+=precinct['G16PRERTru']
            numOther+=precinct['G16PRELJon'] + precinct['G16PRECCas']+precinct['G16PREGSte']+precinct['G16PREIDeL']
    #continue
    totalVotes = numDem+numRepub+numOther
    ##print
    cursor.execute('Insert into DistVotes(districtID, stateName, electionYear, electionName, numRepub, numDemocrat, numOther, totalVotes, winner) values(\''+
            str(district['DISTRICT']) + '\','+
            stateName+','+
            electionYear+','+
            electionName+','+
            str(numRepub)+','+
            str(numDem)+','+
            str(numOther)+','+
            str(totalVotes)+',\''+
            maximumParty(numDem, numRepub, numOther)
            +'\');')
    myConn.commit()
    numDem=0
    numRepub=0
    numOther=0
    totalVotes=0


myConn.close()
print("Success")

