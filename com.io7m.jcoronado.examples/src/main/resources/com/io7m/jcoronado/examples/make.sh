#!/bin/sh -ex

glslangValidator \
  -V \
  -o clip_space_triangle_ub.frag.spv \
  -e R3_clip_triangle_frag_ub_main \
  --source-entrypoint R3_clip_triangle_frag_ub_main \
  clip_space_triangle_ub.frag

glslangValidator \
  -V \
  -o clip_space_triangle.frag.spv \
  -e R3_clip_triangle_frag_main \
  --source-entrypoint R3_clip_triangle_frag_main \
  clip_space_triangle.frag

glslangValidator \
  -V \
  -o clip_space_triangle.vert.spv \
  -e R3_clip_triangle_vert_main \
  --source-entrypoint R3_clip_triangle_vert_main \
  clip_space_triangle.vert

spirv-link \
  --create-library \
  --target-env vulkan1.0 \
  -o shaders.spv \
  clip_space_triangle.vert.spv \
  clip_space_triangle.frag.spv \
  clip_space_triangle_ub.frag.spv
