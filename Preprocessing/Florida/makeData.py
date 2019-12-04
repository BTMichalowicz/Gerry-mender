#!/usr/bin/python3

##Created to  minimize junk in our data



import pandas as pd
from pathlib import Path
import sys
import os
from time import sleep


filename = sys.argv[1]
useHeaders = sys.argv[2]

## TODO: RUN WHEN YOU GET THE CHANCE
init = pd.read_csv(filename, error_bad_lines=False)[['CountyCode','CountyName', 'ElectionDate','Dist','UniquePrecId','TotalRegVot','ContestName', 'CandParty', 'VoteTotal']]##TODO: GET THIS DONE AGAIN ON WSL 

pres_file = open('../2016_FL_President_Precinct.csv', 'a') ## For appending files
gen_election=open('../2016_FL_gen_Precinct.csv' if (filename.find('2016')>-1) else '../2018_FL_gen_Precinct.csv', 'a')

Pres=init[init.ContestName == 'President of the United States']
General= init[(init.ContestName == 'Representative in Congress')]


# (init.ContestName == 'United States Senator' )| Just to have on hand


#pres_file.write('CountyName,ElectionData,UniquePrecId,ContestName,CandParty,VoteTotal')
#gen_election.write('CountyName,ElectionData,UniquePrecId,ContestName,CandParty,VoteTotal')

if useHeaders == 'true':
    Pres.to_csv(pres_file, index=False)
    General.to_csv(gen_election, index=False)
elif useHeaders == 'false':
    Pres.to_csv(pres_file, header=False, index=False);
    General.to_csv(gen_election, header=False, index=False)

pres_file.close();
gen_election.close();

#sleep(3)

