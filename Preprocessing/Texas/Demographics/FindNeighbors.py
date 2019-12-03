#!/bin/bash
import geopandas as geop
import sys
from time import sleep
from shapely.ops import nearest_points

file_name = "TX_vtds.shp"

data = geop.read_file(file_name)
data["NEIGHBORS"] = None

for idx, precinct in data.iterrows():
    print(precinct)
    sleep(10000)
    #gis.stackexchange answer
    neighbors = data[data.geometry.touches(precinct['geometry'])].CNTYVTD.tolist()
    neighbors = [countypct for countypct in neighbors if precinct.CNTYVTD!=countypct]
    data.at[idx, "NEIGHBORS"] = ",".join(neighbors)


data.to_file('TX_vtds_with_neighbors.shp')
