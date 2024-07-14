#!/bin/sh

IFS="
"

for line in $(cat limitsRequired.txt)
do
  NAME=$(echo $line | awk -F: '{print $1}' | xargs)
  VALU=$(echo $line | awk -F: '{print $3}' | xargs)

  if [ $VALU = "false" ]
  then
    EVAL="false"
  else
    EVAL=$(echo "${VALU}" | bc 2>/dev/null)
    if [ $? -ne 0 ]
    then
      continue
    fi
  fi

  cat <<EOF
  .set${NAME^}(${EVAL})
EOF
done
