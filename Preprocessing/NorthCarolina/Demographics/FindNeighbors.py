#!/bin/bash
import geopandas as geop
import sys
from time import sleep
from shapely.ops import nearest_points

file_name = "NC_VTD.shp"

data = geop.read_file(file_name)
data["NEIGHBORS"] = None

for idx, precinct in data.iterrows():
    print(precinct)
    sleep(1000)
    #gis.stackexchange answer
    neighbors = data[data.geometry.touches(precinct['geometry'])].VTD.tolist()
    neighbors = [countypct for countypct in neighbors if precinct.VTD!=countypct]
    data.at[idx, "NEIGHBORS"] = ",".join(neighbors)


data.to_file('NC_VTD_with_neighbors.shp')
