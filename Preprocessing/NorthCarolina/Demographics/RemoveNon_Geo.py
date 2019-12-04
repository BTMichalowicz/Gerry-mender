import geopandas as gp
import sys
from time import sleep


file_name = "NC_VTD_with_neighbors.shp"

data = gp.read_file(file_name)
data['countypct'] = None

for idx, precinct in data.iterrows():
    datum = data.loc[idx]
    #print(datum)
    #sleep(1000)
    datum['countypct'] = datum['VTD']

    datum = datum[['countypct','geometry', 'NEIGHBORS']]

    #print(datum)
    #sleep(900)
    data.loc[idx] = datum
    print(datum['countypct'])

data.to_file('../NC_2016_neighbors_pop_geo.shp')
