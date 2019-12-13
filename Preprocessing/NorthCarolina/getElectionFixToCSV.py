import pymysql


username='dinnerbone'
hostname='mysql4.cs.stonybrook.edu'
passwd='changeit'
db=username

myConn = pymysql.connect(user=username, host=hostname, db=db, passwd=passwd)

cursor = myConn.cursor()


cursor.execute('Select * from Votes where stateName=\'North Carolina\' and electionYear = \'2016\' and electionName =\'Congressional\';')

print('precinctID, countyCode,countyName,districtName,stateName,electionYear,electionName,numRepub,numDemocrat,numOther,totalVotes,winner')

for res in cursor.fetchall():
    for elem in res:
        if "_" in str(elem):
            print(str(elem.split("_")[2]) + "," + str(elem.split("_")[1]) + ",", end = " ")
        else:
            print(str(elem) +",", end=" ")
    print()


myConn.close()
    
