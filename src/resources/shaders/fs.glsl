varying vec3 position;
varying vec3 normal;
varying vec2 texCoord;

uniform sampler2D wood;

void main(){
    vec3 n = normalize(normal);

    /* Svetlo 0 */
    vec3 light0 = normalize(gl_LightSource[0].position.xyz - position);
    vec3 h0 = normalize(light0 - normalize(position));

    vec4 ambient0 = gl_LightSource[0].ambient * gl_FrontMaterial.ambient;
    vec4 diffuse0 = max(0.0f, dot(n, light0)) * gl_LightSource[0].diffuse
            * gl_FrontMaterial.diffuse;
    vec4 specular0 = pow(max(0.0f, dot(n, h0), gl_FrontMaterial.shininess)
            * gl_LightSource[0].specular * gl_FrontMaterial.specular;


    /* Svetlo 1 */
    vec3 light1 = normalize(gl_LightSource[1].position.xyz - position);
    vec3 h1 = normalize(light1 - normalize(position));

    vec4 ambient1 = gl_LightSource[1].ambient * gl_FrontMaterial.ambient;
    vec4 diffuse1 = max(0.0f, dot(n, light1)) * gl_LightSource[1].diffuse
            * gl_FrontMaterial.diffuse;
    vec4 specular1 = pow(max(0.0f, dot(n, h1)), gl_FrontMaterial.shininess)
            * gl_LightSource[1].specular * gl_FrontMaterial.specular;


    /* Svetlo 2 */
    vec3 light2 = normalize(gl_LightSource[2].position.xyz - position);
    vec3 h2 = normalize(light2 - normalize(position));

    vec4 ambient2 = gl_LightSource[2].ambient * gl_FrontMaterial.ambient;
    vec4 diffuse2 = max(0.0f, dot(n, light2)) * gl_LightSource[2].diffuse
            * gl_FrontMaterial.diffuse;
    vec4 specular2 = pow(max(0.0f, dot(n, h2)), gl_FrontMaterial.shininess)
            * gl_LightSource[2].specular * gl_FrontMaterial.specular;

    vec4 woodColor = texture(wood, texCoord);

    vec4 total_light0 = vec4(0.0,0.0,0.0,0.0);
    vec4 total_light1 = vec4(0.0,0.0,0.0,0.0);
    vec4 total_light2 = vec4(0.0,0.0,0.0,0.0);

    total_light0= max(ambient0, diffuse0) * woodColor + specular0;
    total_light1= max(ambient1, diffuse1) * woodColor + specular1;
    total_light2= max(ambient2, diffuse2) * woodColor + specular2;

    gl_FragColor += total_light0 + total_light1 + total_light2;
}