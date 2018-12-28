#version 450

layout(location = 0) in vec3 fragmentColor;

layout(location = 0) out vec4 outputColor;

layout(binding = 0) uniform IntensityData {
  float intensity;
} intensityData;

void
R3_clip_triangle_frag_ub_main()
{
  outputColor = vec4(fragmentColor * intensityData.intensity, 1.0);
}
