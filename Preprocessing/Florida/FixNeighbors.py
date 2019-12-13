import geopandas as gp
from time import sleep
from pathlib import Path
import sys 
import os
#import shapely.geometry
import shapely.affinity
import shapely
import math
from shapely.geometry import Point
from shapely.geometry import LineString
from shapely.geometry import MultiLineString

txPrecincts= gp.read_file('NC_VTD_with_neighbors.shp')
txPrecincts["NEIGHBORS"] = None

#for idx, row in txPrecincts.iterrows():
#    for elem in row:
#        if elem == row['geometry']:
#            continue
#        print (elem)

#    print("\n")
#    sleep(3400)


#print(txPrecincts.loc[0])
#exit()


i = 0
j = 0


def findDistance(point1, point2):
    return math.acos(math.sin(math.radians(point1[1]))*math.sin(math.radians(point2[1]))+math.cos(math.radians(point2[1]))*math.cos(math.radians(point1[1]))*math.cos(math.radians(point2[0])-math.radians(point1[0])))*20902230



#point1 = flPrecincts['geometry'].exterior.coords
#point2 = flPrecincts['geometry'].exterior.coords

#print(findDistance(point1, point2))
#cexit()
txPrecincts['countypct'] = None
for i in range(0, 2691):
    prec = txPrecincts.loc[i]
    prec['countypct'] =prec['VTD_Key']
    txPrecincts.loc[i] = prec






for i in range(0, (2691)):
    if (i+1 > txPrecincts.size -1 or i>= txPrecincts.size-1):
        break
    prec1 = txPrecincts.loc[i]
    print(prec1['countypct'])
    neighbors = []
    for j in range(0, (2691)):
    
        if (j+1 > txPrecincts.size - 1 or j >=txPrecincts.size-1):
            break
        #prec1 = flPrecincts.loc[i]
        prec2 = txPrecincts.loc[j]
        if prec1['countypct'] == prec2['countypct']:
            continue
        regPrec1 = prec1['geometry']
        regPrec2 = prec2['geometry']
        ##Precinct intersection
        if(regPrec1.intersects(regPrec2)):
            #if(regPrec1.geom_type =='Point' or regPrec2.geom_type == 'Point') or (regPrec1.geom_type == 'Point' and regPrec.geom_type == 'Point'):
                #print('One of the ends is a point, not an edge')
                #break
           # else:
            #print("Intersection: ")
            #print(regPrec1.intersection(regPrec2))
            res = regPrec1.intersection(regPrec2)
            if isinstance(res, Point):
                #print(res)
                continue
            elif isinstance(res, LineString):
                #print(res.coords[0])
                dist = findDistance(res.coords[0], res.coords[1])
                if dist >=100.0:
                    neighbors.append(prec2['countypct'])
                    continue

                #continue

            print((type(res)) if isinstance(res, LineString)else "", end = ' ')
            if(type(res) == LineString):
                dist = findDistance(res.coords[0], res.coords[1])
                if dist >=100.0:
                    neighbors.append(prec2['countypct'])
                    continue

            try:
                for string in regPrec1.intersection(regPrec2):
                    if(isinstance(string, Point)==True):
                        break
                    elif(isinstance(string,MultiLineString) == True):
                        for string2 in string:
                            dist = findDistance(string2.coords[0], string2.coords[1])
                #for point in string.coords:
                    #print(point)
                   # print(point(point))
                            if (dist>=100.0):
                                #print(str(prec1['countypct']) + " and " + str(prec2['countypct']) + " are neighbors!")
                                neighbors.append(prec2['countypct'])
                                break
                    else:

                        dist = findDistance(string.coords[0], string.coords[1])
                #for point in string.coords:
                    #print(point)
                   # print(point(point))
                        if (dist>=100.0):
                        #print(str(prec1['countypct']) + " and " + str(prec2['countypct']) + " are neighbors!")
                            neighbors.append(prec2['countypct'])
                            break
            except:
                dist = findDistance(res.coords[0], res.coords[1])
                if dist >=100.0:
                    neighbors.append(prec2['countypct'])
                    continue


##Collinearity check
        #print(prec1['countypct'] + " may be a neighbor of " + prec2['countypct'])
    prec1['NEIGHBORS'] = ",".join(neighbors)
    txPrecincts.loc[i] = prec1


txPrecincts.to_file("NC_With_actual_neighbors.shp")

