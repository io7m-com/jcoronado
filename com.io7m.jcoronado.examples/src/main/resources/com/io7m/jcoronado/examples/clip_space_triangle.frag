#version 450

layout(location = 0) in vec3 fragment_color;

layout(location = 0) out vec4 output_color;

void
R3_clip_triangle_frag_main()
{
  output_color = vec4(fragment_color, 1.0);
}
