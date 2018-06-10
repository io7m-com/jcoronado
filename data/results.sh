#!/bin/sh

IFS="
"

for line in $(cat results.txt)
do
  echo $line | awk '{printf "%s: %s\n",$1,$2}'
done
