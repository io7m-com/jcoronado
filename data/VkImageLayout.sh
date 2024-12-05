#!/bin/sh

IFS="
"

for line in $(cat VkImageLayout.txt)
do
  NAME=$(echo $line | awk -F: '{print $1}' | sed 's/ +//g')
  VALU=$(echo $line | awk -F: '{print $2}' | sed 's/ +//g')

  cat <<EOF
  /**
   * ${NAME}
   */

  ${NAME}(${VALU}),

EOF
done
