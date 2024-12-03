#!/bin/sh

IFS="
"

i=0
for line in $(cat vk_color_space.txt)
do
  NAME=$(echo $line | awk -F: '{print $1}')
  REST=$(echo $line | sed -E 's/^[A-Z_0-9]+ : [0-9]+ ://g')

  cat <<EOF
  /**
   * ${REST}
   */

  ${NAME}($i),

EOF

  i=$(expr $i + 1)
done
