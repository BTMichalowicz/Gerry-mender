import pymysql
from time import sleep
import sys
import os


hostname='mysql4.cs.stonybrook.edu'
username='dinnerbone'
password='changeit'
database='dinnerbone'


myConn = pymysql.connect(host=hostname, user=username, passwd=password, db=database)
cursor=myConn.cursor()

print('countyName,totalPop,whitePop,africanAmericanPop,asianPop,hispanicPop,nativeAmericanPop')
cursor.execute("select countyName, totalPop, whitePop, africanAmericanPop, asianPop, hispanicPop,nativeAmericanPop from County where stateName='Florida';")

for elem in cursor.fetchall():
    for item in elem:
        print(str(item) +",", end=' ')
    print()
