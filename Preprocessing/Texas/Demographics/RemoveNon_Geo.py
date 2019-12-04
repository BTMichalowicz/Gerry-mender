import geopandas as gp
import sys
from time import sleep


file_name = "TX_vtds_with_neighbors.shp"

data = gp.read_file(file_name)
data['countypct'] = None

for idx, precinct in data.iterrows():
    datum = data.loc[idx]
    #print(datum)
    #sleep(1000)
    datum['countypct'] = datum['CNTYVTD']

    datum = datum[['countypct','geometry', 'NEIGHBORS']]

    print(datum)
    sleep(900)
    data.loc[idx] = datum

data.to_file('TX_vtds_with_neighbors_geo.shp')
