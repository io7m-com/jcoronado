#version 450

layout(location = 0) in vec2 attrPosition;
layout(location = 1) in vec3 attrColor;
layout(location = 2) in vec3 attrUV;

layout(location = 0) out vec3 fragmentColor;
layout(location = 1) out vec3 fragmentUV;

out gl_PerVertex
{
  vec4 gl_Position;
};

void
R3_clip_triangle_vert_main()
{
  gl_Position   = vec4(attrPosition, 0.0, 1.0);
  fragmentColor = attrColor;
  fragmentUV    = attrUV;
}
