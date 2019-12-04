import fiona
from time import sleep
from pathlib import Path
import sys
import os
##

with layername in fiona.open("NC_VTD/NC_VTD.shp") as src:
    print(layername, len(layername))
