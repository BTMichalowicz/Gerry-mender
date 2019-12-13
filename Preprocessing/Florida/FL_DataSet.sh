#!/bin/bash
##Code to work out election data in Florida

for dir in "precinctlevelelectionresults2016gen" "precinctlevelelectionresults2018gen"
do
	cd $dir
	for file in *
	do
		if [[ $file == *.csv ]]
		then
			echo $file
		
			python3 ../makeData.py $file #2> /dev/null ##When running on our own PC
			#/c/software/Python36/python.exe ../makeData.py $file 2> /dev/null ## When running on a CS dept computer
		fi
	done
	cd ../
done
