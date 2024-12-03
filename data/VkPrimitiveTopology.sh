#!/bin/sh

IFS="
"

for line in $(cat VkPrimitiveTopology.txt)
do
  NAME=$(echo $line | awk -F: '{print $1}' | sed 's/ +//g')
  VALU=$(echo $line | awk -F: '{print $2}' | sed 's/ +//g')
  REST=$(echo $line | awk -F: '{print $3}')

  cat <<EOF
  /**
   * ${NAME}
   */

  ${NAME}(${VALU}),

EOF
done
