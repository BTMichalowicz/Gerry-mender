#!/usr/bin/python3

from pathlib import Path
from time import sleep
import sys
import os
import errno

##Benjamin Michalowicz
##Purpose: To turn election result text files into csv files to be easily separatable in a database setting
## Java or otherwise



for file in os.listdir('.'):

    filename = os.fsdecode(file)
    if filename.endswith(".txt"):
        ##Parse the text files inside the textfiles from dos.myflorida.com
        inputfile = open("./"+filename, 'r')
        outputfile = open("./"+filename.split(".txt")[0]+".csv", 'w')
        outputfile.write("CountyCode,CountyName,ElectionNumber,ElectionDate,ElectionName,UniquePrecId,PrecPollLoc,TotalRegVot,TotalRep,TotalDem,TotalOther,ContestName,Dist,ContestCode,Candidate-Reten-IssueName-WriteIns-OverUnderVote,CandParty,RegIdNumber,DOECandNumber,VoteTotal\n")
        line = inputfile.readline()
        while(line=='\n'):
            line = inputfile.readline()
            continue
        while(line!=''):
            if line == "\n":
                while(line =='\n'):
                    line = inputfile.readline()
            #Trying to remove empty lines
            line_arr = line.split('\t')
            for i in range(0, len(line_arr)):
                line_arr[i] = line_arr[i].replace(',',';')

            #line_arr = line_arr.replace(',', ';')
            #while ' ' in line_arr:
            #    line_arr.remove(' ')

            #Trying to remove newline characters
            for x in line_arr:
                if '\n' in x:
                    x.replace('\n', '')
            #print(line_arr)
            outputfile.write(line_arr[0])

            for i in range(1, len(line_arr)):
                if i ==5:
                    outputfile.write(","+line_arr[i])
                else:
                    outputfile.write("," + line_arr[i])
           # outputfile.write("\n") 

            line = inputfile.readline()

        outputfile.close()
        inputfile.close()


