#!/bin/sh

IFS="
"

i=0
for line in $(cat formats.txt)
do
  NAME=$(echo $line | awk -F: '{print $1}')
  REST=$(echo $line | sed -E 's/^[A-Z_0-9]+ : //g')

  echo ${NAME} : ${i} : ${REST}
  i=$(expr $i + 1)
done
