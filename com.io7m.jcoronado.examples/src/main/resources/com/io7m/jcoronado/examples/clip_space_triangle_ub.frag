#version 450

layout(location = 0) in vec3 fragment_color;

layout(location = 0) out vec4 output_color;

layout(binding = 0) uniform UniformBufferObject {
  float intensity;
} data;

void
R3_clip_triangle_frag_ub_main()
{
  output_color = vec4(fragment_color * data.intensity, 1.0);
}
