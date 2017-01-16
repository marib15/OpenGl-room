package projekt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ObjLoader {

    private String path;
    
    private List<float[]> vertices;
    private List<float[]> normals;
    private List<int[]> vertexIndices;
    private List<int[]> normalIndices;
    private List<float[]> textures;
    private List<int[]> textureIndices;
    
    private BufferedReader inReader;

    public ObjLoader(String path) {
        this.path = path;
    }

    public void load() {
        /** Mesh containing the loaded object */
        vertices = new ArrayList<float[]>();
        normals = new ArrayList<float[]>();
        vertexIndices = new ArrayList<int[]>();
        normalIndices = new ArrayList<int[]>();
        textures = new ArrayList<float[]>();
        textureIndices = new ArrayList<int[]>();
        
        String line;
        try {
            inReader = new BufferedReader(new InputStreamReader(
                    this.getClass().getResource(path).openStream()));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return;
        }
        try {
            while ((line = inReader.readLine()) != null) {
                
                if (line.startsWith("v ")) {
                    
                    String[] vertStr = line.split("\\s+");
                    float[] vertex = new float[3];
                            
                    vertex[0] = Float.parseFloat(vertStr[1]);
                    vertex[1] = Float.parseFloat(vertStr[2]);
                    vertex[2] = Float.parseFloat(vertStr[3]);
                    vertices.add(vertex);
                    
                } else if (line.startsWith("vn ")) {
                    
                    String[] normStr = line.split("\\s+");
                    float[] normal = new float[3];
                    
                    normal[0] = Float.parseFloat(normStr[1]);
                    normal[1] = Float.parseFloat(normStr[2]);
                    normal[2] = Float.parseFloat(normStr[3]);
                    normals.add(normal);
                    
                } else if (line.startsWith("vt ")) {
                   
                    String[] normStr = line.split("\\s+");
                    float[] texture = new float[3];
                    
                    texture[0] = Float.parseFloat(normStr[1]);
                    texture[1] = Float.parseFloat(normStr[2]);
                    texture[2] = 0f;
                    textures.add(texture);
                    
                } else if (line.startsWith("f ")) {
                    
                    String[] faceStr = line.split("\\s+");
                    int[] faceVert = new int[3];

                    faceVert[0] = Integer.parseInt(faceStr[1].split("/")[0]) - 1;
                    faceVert[1] = Integer.parseInt(faceStr[2].split("/")[0]) - 1;
                    faceVert[2] = Integer.parseInt(faceStr[3].split("/")[0]) - 1;
                    vertexIndices.add(faceVert);
                    
                    // TODO: indexy texturovych suradnic (2. hodnota z trojice cisel)
                    
                    if (faceStr[1].split("/").length >= 3) {
                        int[] faceNorm = new int[3];
                        int[] texNorm = new int[3];
                        
                        texNorm[0] = Integer.parseInt(faceStr[1].split("/")[1]) - 1;
                        texNorm[1] = Integer.parseInt(faceStr[2].split("/")[1]) - 1;
                        texNorm[2] = Integer.parseInt(faceStr[3].split("/")[1]) - 1;
                        textureIndices.add(texNorm);

                        faceNorm[0] = Integer.parseInt(faceStr[1].split("/")[2]) - 1;
                        faceNorm[1] = Integer.parseInt(faceStr[2].split("/")[2]) - 1;
                        faceNorm[2] = Integer.parseInt(faceStr[3].split("/")[2]) - 1;
                        normalIndices.add(faceNorm);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Unable to load " + path + " file: " + ex.getMessage());
        }
    }
    
    public List<float[]> getTextures() {
        return textures;
    }

    public List<int[]> getTextureIndices() {
        return textureIndices;
    }

    public List<float[]> getVertices() {
        return vertices;
    }

    public List<float[]> getNormals() {
        return normals;
    }

    public List<int[]> getVertexIndices() {
        return vertexIndices;
    }

    public List<int[]> getNormalIndices() {
        return normalIndices;
    }
}