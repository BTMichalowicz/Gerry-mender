#!/bin/bash

for i in {1..100..1}
do
  name="${i}_prec_sort.txt"
  wget https://s3.amazonaws.com/dl.ncsbe.gov/ENRS/2018_11_06/precinct_sort/$name
#  echo $name
done
