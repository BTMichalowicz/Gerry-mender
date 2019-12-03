import pandas as pd
import geopandas as gp
from time import sleep
import sys
import os


countyName=""
precinctCount=0
popDict = dict()

Demographics = pd.read_csv('test.csv')

CountyDict = dict()

for idx, row in Demographics.iterrows():
    c = str(row['countyCode'])
    CountyDict[c] = dict()
    CountyDict[c]['prec_count'] = 0
    CountyDict[c]['total_pop'] = (int(row['totalPop']))
    CountyDict[c]['total_White'] = (int(row['whitePop']))
    CountyDict[c]['total_Afric'] = (int(row['africanAmericanPop']))
    CountyDict[c]['total_Asian'] = (int(row['asianPop']))
    CountyDict[c]['total_Hisp'] = (int(row['hispanicPop']))
    CountyDict[c]['total_Native'] = (int(row['nativeAmericanPop']))

    #print(row['countyCode'])

##precincts = pd.read_json('fl_2016.json')
precincts = gp.read_file('fl_2016_no_errors/fl_2016_with_neighbors.shp')

precincts['total_pop'] = 0
precincts['total_white'] = 0
precincts['total_afric'] = 0
precincts['total_asian'] = 0
precincts['total_hisp'] = 0
precincts['total_native'] = 0

for idx, row in precincts.iterrows():
    try:
        name = row['county']
        CountyDict[name]['prec_count']+=1
    except:
        print(name + " county was not found")


for idx, row in precincts.iterrows():
    index = precincts.loc[idx]
    #print(index)
    #sleep(122)
    name = row['county']

    precCount = CountyDict[name]['prec_count']
    index['total_pop'] = int(CountyDict[name]['total_pop']/precCount)
    index['total_white'] = int(CountyDict[name]['total_White']/precCount)
    index['total_afric'] = int(CountyDict[name]['total_Afric']/precCount)
    index['total_asian'] = int(CountyDict[name]['total_Asian']/precCount)
    index['total_asian'] = int(CountyDict[name]['total_Asian']/precCount)
    index['total_hisp'] = int(CountyDict[name]['total_Hisp']/precCount)
    index['total_native'] = int(CountyDict[name]['total_Native']/precCount)

    precincts.loc[idx] = index


precincts.to_file("fl_neighbors_pop.shp")

## Idea: Sorted the selection of counties in the csv file; now to sort them, and divide demographics


#for i in range(0, precincts['features'].size):
#    try:
#        name =  precincts['features'][i]['properties']['county']
#       # print(name)
#        #sleep(10)
#        CountyDict[name]['prec_count']+=1
#    except:
#        print(name+" was not found")
#
#
#for i in range(0, precincts['features'].size):
#    name = precincts['features'][i]['properties']['county']
#    newDict=precincts['features'][i]['properties']
#    precCount = CountyDict[name]['prec_count']
#    newDict['total_pop'] = int(CountyDict[name]['total_pop'] / precCount)
#    newDict['total_white'] = int(CountyDict[name]['total_White'] / precCount)
#    newDict['total_afric'] = int(CountyDict[name]['total_Afric'] / precCount)
#    newDict['total_asian'] = int(CountyDict[name]['total_Asian'] / precCount)
#    newDict['total_hisp'] = int(CountyDict[name]['total_Hisp'] / precCount)
#    newDict['total_native'] = int(CountyDict[name]['total_Native'] / precCount)
#    precincts['features'][i]['properties'] = newDict
#





#for idx, row in precincts.iterrows():
    #print(row)
    #sleep(100)

##precincts.to_json('FLPTest2.json', orient="records")
#outputfile = open('FLPTest2.json', 'w')
#
#for row in precincts.iterrows():
#    row[1].to_json(outputfile)
#outputfile.close()
#
    ### TODO: ALTER PRECINCT AND DINISH

