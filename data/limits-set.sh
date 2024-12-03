#!/bin/sh

IFS="
"

for line in $(cat limits.txt)
do
  NAME=$(echo $line | awk -F: '{print $1}')

  cat <<EOF
  .set${NAME^}(vk_limits.${NAME}())
EOF
done
