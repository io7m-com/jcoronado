#!/bin/sh

IFS="
"

i=0
for line in $(grep -E -v '^#' formats.txt)
do
  NAME=$(echo $line | awk -F: '{print $1}')
  REST=$(echo $line | sed -E 's/^[A-Z_0-9x]+  : [0-9]+ ://g')

  cat <<EOF
  /**
   * ${REST}
   */

  ${NAME}($i),

EOF

  i=$(expr $i + 1)
done
