#!/usr/bin/python3

##Created to  minimize junk in our data



import pandas as pd
from pathlib import Path
import sys
import os
from time import sleep
import re # For regex parsing

filename = sys.argv[1]

print(filename)

## TODO: RUN WHEN YOU GET THE CHANCE
init = pd.read_csv(filename, error_bad_lines=False, low_memory=False)[['County','Election Date','Precinct','Contest Name','Choice Party', 'Total Votes']]##TODO: GET THIS DONE AGAIN ON WSL 

pres_file = open('../2016_NC_President_Precinct.csv', 'a') ## For appending files
gen_election=open('../2016_NC_gen_Precinct.csv' if (filename.find('2016')>-1) else '../2018_NC_gen_Precinct.csv', 'a')

#init = init.dropna()
#init = init[(type(init['Precinct']) == int) | (re.search("[0-9]*-[0-9]*", init['Precinct']) == True)]

#for col in init:
#    col = col.interpolate(method="pad")


Pres=init[init['Contest Name'] == 'US PRESIDENT']
#init = init.interpolate()

#init.interpolate(method='linear',limit_direcrion='forward', axis=0)

General = init[init['Contest Name'].str.contains('US HOUSE OF REPRESENTATIVES')]

#print(General['Contest Name'][0])
#General = General.fillna("Not avail")
#General = General.interpolate()


#print(General.shape)

Pres.to_csv(pres_file, index=False);
General.to_csv(gen_election, index=False)

#for idx, rows in init.iterrows():
#    for elem in rows:
#        if 'US HOUSE OF REPRESENTATIVES' in (str(elem)):
#            gen_election.write(str(rows['County']) + ',' + str(rows['Election Date']) +',' + str(rows['Precinct']) + ',' + str(rows['Contest Name']) + ',' + str(rows['Choice Party']) + ',' + str(rows[ 'Total Votes']))



#for idx, row in General.iterrows():
#    for elem in row:
#        print(str(elem)+" ")
#        gen_election.write(str(elem)+",")



pres_file.close();
gen_election.close();

