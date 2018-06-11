#!/bin/sh

IFS="
"

for line in $(cat vk_surface_transform_bits.txt)
do
  NAME=$(echo $line | awk -F: '{print $1}')
  VALU=$(echo $line | awk -F: '{print $2}')
  REST=$(echo $line | sed -E 's/^[A-Z_0-9]+[ ]+: 0x[0-9]+ ://g')

  cat <<EOF
  /**
   * ${REST}
   */

  ${NAME}(${VALU}),

EOF
done
