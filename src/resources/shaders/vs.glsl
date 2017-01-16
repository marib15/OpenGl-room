varying vec3 position;
varying vec3 normal;
varying vec2 texCoord;

void main()
{
    normal = gl_NormalMatrix * gl_Normal;
    position = (gl_ModelViewMatrix * gl_Vertex).xyz;
    texCoord = gl_MultiTexCoord0.st;

    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}