import sys
import os
from time import sleep
import pymysql
import pandas as pd

ncPrec = pd.read_json("FL_With_actual_neighbors.json")

#print(ncPrec.loc[0])

userName='dinnerbone'
passwd='changeit'
db='dinnerbone'
hostname='mysql4.cs.stonybrook.edu'

myConn = pymysql.connect(host=hostname,db=db,passwd=passwd,user=userName)
cursor=myConn.cursor()

#print(ncPrec['features'].size)
#print(ncPrec['features'][0]['properties']['countypct'])
#exit()


for i in range(0, ncPrec['features'].size):
    elem= ncPrec['features'][i]
    props = elem['properties']
    print(props['countypct'])
    #continue
    cursor.execute('UPDATE Precinct SET NEIGHBORS = \''  + props['NEIGHBORS'] + '\' where precinctID=\''
            +str(props['countypct']) +'\';')
    myConn.commit()


myConn.close()
