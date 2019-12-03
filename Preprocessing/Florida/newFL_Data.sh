#!/bin/bash

for dir in "precinctlevelelectionresults2016gen1" "precinctlevelelectionresults2018gen1"
do
  useHeaders="true"
  cd $dir
  #pwd
  #ls *.csv
  for file in *
  do
	 #echo $file
	 if [[ $file == *.csv ]]
	 then
		echo $file
		python3 ../makeData.py $file $useHeaders 2> /dev/null
		useHeaders="false"
	 fi
  done
  cd ../
done
