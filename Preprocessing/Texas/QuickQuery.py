import pymysql
from time import sleep
import os
import sys


hostname='mysql4.cs.stonybrook.edu'
username='dinnerbone'
passwd='changeit'
database='dinnerbone'
myConn = pymysql.connect(host=hostname, user=username, passwd=passwd, db=database)
cursor = myConn.cursor()

cursor.execute('Select * from Votes where stateName = \'Texas\' and electionYear = \'2018\';')


print("PrecinctID, countyName, ignore, distID, stateName, electionYear, electionName, numRepub, numDem, numOther, totVote, winner")
for elem in cursor.fetchall():
    for e in elem:
        if("_" in str(e)):
            print(str(e).split("_")[2].replace(',',';')+ ",", end=" ")
            print(str(e).split("_")[1].replace(',',';') + ",", end=" ")

        else:
            print(str(e).replace(',',';') +",", end=" ")
    print()
