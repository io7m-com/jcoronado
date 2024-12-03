#!/bin/sh

IFS="
"

for line in $(cat limits.txt)
do
  NAME=$(echo $line | awk -F: '{print $1}')
  TYPE=$(echo $line | awk -F: '{print $2}')
  DESC=$(echo $line | sed -E 's/^[a-zA-Z0-9_]+ : [a-zA-Z0-9_]+ ://g')

  cat <<EOF
  /**
   * @return ${DESC}
   */

  @Value.Parameter
  ${TYPE} ${NAME}();

EOF
done
