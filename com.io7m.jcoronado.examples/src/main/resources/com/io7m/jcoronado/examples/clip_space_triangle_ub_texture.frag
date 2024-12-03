#version 450

layout(location = 0) in vec3 fragmentColor;
layout(location = 1) in vec3 fragmentUV;

layout(location = 0) out vec4 outputColor;

layout(binding = 0) uniform IntensityData {
  float intensity;
} intensityData;

layout(binding = 1) uniform sampler2D textureSampler;

void
R3_clip_triangle_frag_ub_texture_main()
{
  vec3 textureSample =
    texture(textureSampler, fragmentUV.xy).rgb;
  vec3 textureTinted =
    fragmentColor * textureSample;

  outputColor =
    vec4(textureTinted * intensityData.intensity, 1.0);
}
