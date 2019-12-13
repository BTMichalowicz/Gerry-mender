import geopandas as geop
import shapely.ops
from shapely.ops import nearest_points
import fiona
from shapely.geometry import shape, mapping, Point, Polygon, MultiPolygon
import pymysql

user = "dinnerbone"
host = "mysql4.cs.stonybrook.edu"
passwd="changeit"
database=user

DistFile = geop.read_file("FLDis.shp")
PrecFile = geop.read_file("fl_neighbors_pop (1).shp")

myConn = pymysql.connect(user=user, host=host,passwd=passwd, db=database)
cursor = myConn.cursor()

distName = ""
distPop = 0
distWhite = 0
distAfric = 0
distAsian = 0 
distHisp = 0
distNative = 0

for idx, dist in DistFile.iterrows():
    region = dist['geometry']
    distName = str(dist['DISTRICT'])
    print(distName)
    for idx2, precinct in PrecFile.iterrows():
        if precinct['geometry'].intersects(region):
            #print(precinct['countypct'] + " is a part of district " + str(dist['DISTRICT']))
            distPop += precinct['total_pop']
            distWhite+= precinct['total_whit']
            distAfric+= precinct['total_afri']
            distAsian+=precinct['total_asia']
            distHisp+=precinct['total_hisp']
            distNative+=precinct['total_nati']
    cursor.execute("insert into District(districtID, stateName, totalPop, whitePop, africanAmericanPop,asianPop,hispanicPop, nativeAmericanPop) values('"+distName+"','FLORIDA',"+
            str(distPop)+","+
            str(distWhite)+","+
            str(distAfric)+","+
            str(distAsian)+","+
            str(distHisp)+","+
            str(distNative)+
            ");")
    myConn.commit()
    distPop = 0
    distWhite = 0
    distHisp = 0
    distAfric = 0
    distAsian = 0
    distNative=0
    
myConn.close()


