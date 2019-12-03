#!/usr/bin/python3

import pandas as pd
import sys
import os
from time import sleep
import re
from pathlib import Path

filename = sys.argv[1]
isFirst = sys.argv[2]
print(filename)

init = pd.read_csv(filename, error_bad_lines=False, low_memory=False)[['county', 'precinct', 'district','office', 'party', 'votes']]


#init = init[(init['precinct']!='Total') | (init['precinct']!='Early Voting') | (init['precinct']!='Provisional')]

Pres = init[init['office'] == 'President']
General = init[(init['office'] == 'U.S. House')]
        
        
        #| (init['office'] == 'State Senator')]

pres_file = open('../2016_TX_President_Precinct.csv', 'a') ## For appending files
gen_election=open('../2016_TX_gen_Precinct.csv' if (filename.find('2016')>-1) else '../2018_TX_gen_Precinct.csv', 'a')


if isFirst == 'true':
    Pres.to_csv(pres_file, index=False)
    General.to_csv(gen_election, index=False)
else:
    Pres.to_csv(pres_file, header=False, index=False);
    General.to_csv(gen_election, header=False, index=False)


pres_file.close();
gen_election.close();

