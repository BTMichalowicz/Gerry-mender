#!/bin/bash

for i in "2016-Nov8" "2018-Nov6"
do
  	 cd $i
	 for file in *
	 do
		if [[ $file == *.csv ]]
		then
		  echo $file
		  python3 ../DataConvert.py $file #2>/dev/null
		fi
	 done
	 cd ../
done
