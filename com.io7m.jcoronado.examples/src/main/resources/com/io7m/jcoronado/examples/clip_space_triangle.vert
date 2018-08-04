#version 450

layout(location = 0) in vec2 attr_position;
layout(location = 1) in vec3 attr_color;

layout(location = 0) out vec3 fragment_color;

out gl_PerVertex
{
  vec4 gl_Position;
};

void
R3_clip_triangle_vert_main()
{
  gl_Position = vec4(attr_position, 0.0, 1.0);
  fragment_color = attr_color;
}
