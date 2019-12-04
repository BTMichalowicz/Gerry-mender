#!/bin/bash

for dir in 2016 2018
do
  isFirst=true
  cd $dir
  #echo $dir
  for file in *
  do
	 #pwd
	 #echo $file
	 if [[ $file == *.csv ]]
	 then
		echo $file
		python3 ../DataMaker.py $file $isFirst 2> /dev/null
		isFirst=false
	 fi
  done
  cd ../
done
