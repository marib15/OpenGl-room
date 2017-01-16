package projekt;

import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.media.opengl.GL.GL_BACK;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_CULL_FACE;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_FALSE;
import static javax.media.opengl.GL.GL_FRONT_AND_BACK;
import static javax.media.opengl.GL.GL_LINEAR_MIPMAP_LINEAR;
import static javax.media.opengl.GL.GL_LINES;
import static javax.media.opengl.GL.GL_MIRRORED_REPEAT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_WRAP_S;
import static javax.media.opengl.GL.GL_TEXTURE_WRAP_T;
import static javax.media.opengl.GL.GL_TRIANGLES;
import static javax.media.opengl.GL.GL_TRIANGLE_STRIP;
import static javax.media.opengl.GL.GL_TRUE;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.GL_COMPILE;
import static javax.media.opengl.GL2.GL_LIGHT_MODEL_COLOR_CONTROL;
import static javax.media.opengl.GL2.GL_QUAD_STRIP;
import static javax.media.opengl.GL2.GL_SEPARATE_SPECULAR_COLOR;
import static javax.media.opengl.GL2ES1.GL_LIGHT_MODEL_TWO_SIDE;
import static javax.media.opengl.GL2ES2.GL_COMPILE_STATUS;
import static javax.media.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static javax.media.opengl.GL2ES2.GL_INFO_LOG_LENGTH;
import static javax.media.opengl.GL2ES2.GL_LINK_STATUS;
import static javax.media.opengl.GL2ES2.GL_VERTEX_SHADER;
import static javax.media.opengl.GL2GL3.GL_FILL;
import static javax.media.opengl.GL2GL3.GL_LINE;
import static javax.media.opengl.GL2GL3.GL_POINT;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT_AND_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_COLOR_MATERIAL;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT2;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_NORMALIZE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPOT_CUTOFF;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPOT_DIRECTION;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import javax.media.opengl.glu.GLU;
import projekt.ObjLoader;

public class OpenGlListener implements GLEventListener{

    private GLU glu = new GLU();
    private GLUT glut = new GLUT();
    private FPSAnimator animator;
    private float angle = 0.0f;
    private float speed = 1.5f;
    private ObjLoader cabinet;
    private ObjLoader cabinetLeftDoor;
    private ObjLoader cabinetRightDoor;
    private ObjLoader box;
    private ObjLoader bin;
    private ObjLoader table;
    private ObjLoader draughts;
    private ObjLoader lamp;
    private ObjLoader metronome;
    private ObjLoader metronomeHand;
    private ObjLoader notebook;
    private ObjLoader notebookDisplay;
    private ObjLoader bulb;
    
    private int mode = 2;
    private int polygonModes[] = { GL_POINT, GL_LINE, GL_FILL };
    
    private boolean light0;
    private boolean light1;
    private boolean light2;
    
    
    private float time;
    private float y;
    
    private int floorId;
    
    private int program = 0;
    
    private float cameraX = 0f;
    private float cameraY = 0f;
    
    private static final float black[] = {0.0f, 0.0f, 0.0f, 1.0f};
    private static final float white[] = {1.0f, 1.0f, 1.0f, 1.0f};
    
    private Texture wood;
    private Texture wood1;
    private Texture wood2;
    private Texture steel;
    
     

    public OpenGlListener(FPSAnimator animator) {
        this.animator = animator;
    }
    
    public void togglePolygonMode() {
        mode = (++mode) % 3;
    }

    @Override
    // metoda volana pri vytvoreni okna OpenGL
    public void init(GLAutoDrawable glad) {

        // Get GL2 interface
        GL2 gl = glad.getGL().getGL2();
        
        // Enable depth testing
	gl.glEnable(GL_DEPTH_TEST);
        
	// Enable lighting
	gl.glEnable(GL_LIGHTING);
        gl.glEnable(GL_NORMALIZE);
         gl.glEnable(GL_DEPTH_TEST); // enables depth testing
        gl.glEnable(GL_COLOR_MATERIAL);
        gl.glCullFace(GL_BACK);
        // Enable face culling
        gl.glEnable(GL_CULL_FACE);
        
        // Create floor
        createFloor(gl, 8f, 12, 4);
        
        cabinet = new ObjLoader("/resources/object/cabinet.obj");
        cabinetLeftDoor = new ObjLoader("/resources/object/cabinet-leftdoor.obj");
        cabinetRightDoor = new ObjLoader("/resources/object/cabinet-rightdoor.obj");
        bin = new ObjLoader("/resources/object/bin.obj");
        box = new ObjLoader("/resources/object/box.obj");
        table = new ObjLoader("/resources/object/table.obj");
        draughts = new ObjLoader("/resources/object/draughts.obj");
        lamp = new ObjLoader("/resources/object/lamp.obj");
        metronome = new ObjLoader("/resources/object/metronome.obj");
        metronomeHand = new ObjLoader("/resources/object/metronome-hand.obj");
        notebook = new ObjLoader("/resources/object/notebook.obj");
        notebookDisplay = new ObjLoader("/resources/object/notebook-display.obj");
        bulb = new ObjLoader("/resources/object/bulb.obj");
        
        cabinet.load();
        cabinetLeftDoor.load();
        cabinetRightDoor.load();
        bin.load();
        box.load();
        table.load();
        draughts.load();
        lamp.load();
        metronome.load();
        metronomeHand.load();
        notebook.load();
        notebookDisplay.load();
        bulb.load();
        
        try {
            wood = loadTexture(gl, "/resources/texture/wood.jpg", TextureIO.JPG);
            wood1 = loadTexture(gl, "/resources/texture/wood1.jpg", TextureIO.JPG);
            wood2 = loadTexture(gl, "/resources/texture/wood2.jpg", TextureIO.JPG);
            steel = loadTexture(gl, "/resources/texture/steel.jpg", TextureIO.JPG);
            program = loadProgram(gl, "/resources/shaders/vs.glsl", "/resources/shaders/fs.glsl");
        } catch (IOException ex) {
            Logger.getLogger(OpenGlListener.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }

    @Override
    // metoda volana pri zatvoreni okna OpenGL
    public void dispose(GLAutoDrawable glad) {
    }

    @Override
    // metoda volana pri kazdom prekresleni obrazovky 
    public void display(GLAutoDrawable glad) {

        // Get GL2 interface
        GL2 gl = glad.getGL().getGL2();
        
        // Clear buffers
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        gl.glPolygonMode(GL_FRONT_AND_BACK, polygonModes[mode]);
        
        if (animator.isAnimating()) {
            time += 0.1f;
            y = 0.5f * (float) (Math.sin(time) + 1);
	}

	// Set look at matrix
	gl.glLoadIdentity();
	
        
        glu.gluLookAt(0, 5, 10,
		0, 0, 0,
		0, 1, 0);

        // Flight around the floor
	gl.glRotatef(cameraX, 1.0f, 0.0f, 0.0f);
	gl.glRotatef(cameraY, 0.0f, 1.0f, 0.0f);

        gl.glDisable(GL_COLOR_MATERIAL);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, black, 0);
        wood.bind(gl);
        gl.glCallList(floorId);
        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        gl.glScalef(0.02f, 0.02f, 0.02f);       
        gl.glTranslatef(0, 0, -140); 
        wood.bind(gl);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);        
        renderObject(gl, cabinet);
        wood1.bind(gl);
        renderObject(gl, cabinetLeftDoor);
        renderObject(gl, cabinetRightDoor);
        gl.glTranslatef(150, 0, 0); 
        renderObject(gl, box);       
        gl.glColor3f(1f, 1f, 1f);
        gl.glTranslatef(0,60,0);
        steel.bind(gl);
        gl.glRotatef(90,0,1,0);
        renderObject(gl, lamp);
        gl.glRotatef(-90,0,1,0);
        
        if(light0) {
            gl.glEnable(GL_DEPTH_TEST);
            float[] light0_position = {1f, 1f, 0, 1};
            float[] light0_direction = {0.1f, 0f, 3f};
            gl.glLightfv(GL_LIGHT0, GL_POSITION, light0_position, 0);
            gl.glLightfv(GL_LIGHT0, GL_AMBIENT, black, 0);
            gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, white, 0);
            gl.glLightfv(GL_LIGHT0, GL_SPECULAR, white, 0);
            gl.glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, light0_direction, 0);
            gl.glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 20.0f);
            
            gl.glLightModeli(GL_LIGHT_MODEL_COLOR_CONTROL, GL_SEPARATE_SPECULAR_COLOR);
            gl.glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);
            
            gl.glEnable(GL_LIGHT0);

            gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, white, 0);
            gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, white, 0);
            gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 20f);
            } else {
            gl.glDisable(GL_LIGHT0);
            }
        
        gl.glTranslatef(-30, -60, 250);
        gl.glColor3f(0f, 1f, 0.0f);
        renderObject(gl, bin);
        gl.glTranslatef(-80, 0, 0);
        wood2.bind(gl);
        
        
        gl.glUseProgram(program);
       	int woodUniform = gl.glGetUniformLocation(program, "wood");
	gl.glUniform1i(woodUniform, 0);
        renderObject(gl, table);
        gl.glUseProgram(0);
        
        
        gl.glTranslatef(40, 70, 0);
        gl.glDisable(GL_TEXTURE_2D);
        gl.glEnable(GL_COLOR_MATERIAL);
        gl.glColor3f(1.0f, 0f, 0f);
        renderObject(gl, draughts);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glTranslatef(-20,2,0);
        gl.glColor3f(0f,1f,0f);
        renderObject(gl,notebook);
        renderObject(gl,notebookDisplay);
        
        gl.glDisable(GL_TEXTURE_2D);
        
        gl.glTranslatef(0,130,-80);
        
        if(light1) {
            gl.glLightfv(GL_LIGHT1, GL_AMBIENT, black, 0); 
            gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, white, 0); 
            gl.glLightfv(GL_LIGHT1, GL_SPECULAR, white, 0);
            gl.glLightModeli(GL_LIGHT_MODEL_COLOR_CONTROL, GL_SEPARATE_SPECULAR_COLOR);
            gl.glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);
            gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, black, 0);
            gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, black, 0);
            gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 10.0f);
            gl.glEnable(GL_LIGHT1);
            gl.glColor3f(1.0f, 1.0f, 0f);
        } else {
            gl.glColor3f(1.0f, 1.0f, 1.0f);
            gl.glDisable(GL_LIGHT1);
        }
        renderObject(gl, bulb);
        gl.glTranslatef(0,-130,80);
                
                
        gl.glTranslatef(-50, -2, 0);
        gl.glScalef(2f, 2f, 2f);
        gl.glColor3f(1.0f, 0.0f, 1.0f);
        renderObject(gl, metronome);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        
      
        
        gl.glRotatef(angle, 0, 0, 1);
        
        if(light2) {
            gl.glPushMatrix();
            gl.glTranslatef(4+angle, 2.5f+angle, 6+angle);           
            glut.glutSolidSphere(0.1, 10, 10);
            gl.glPopMatrix();
            float[] light1_position = {4+angle, 2.5f+angle, 6+angle, 0};
            gl.glLightfv(GL_LIGHT2, GL_POSITION, light1_position, 0);
            gl.glLightfv(GL_LIGHT2, GL_AMBIENT, black, 0); 
            gl.glLightfv(GL_LIGHT2, GL_DIFFUSE, white, 0); 
            gl.glLightfv(GL_LIGHT2, GL_SPECULAR, white, 0);
            gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, black, 0);
            gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, black, 0);
            gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 1.0f);
            gl.glEnable(GL_LIGHT2);
        } else {
            gl.glDisable(GL_LIGHT2);
        }
        renderObject(gl, metronomeHand);
        gl.glPopMatrix();
        
        
        angle += speed;
        if(Math.abs(angle)>30) speed = speed * -1;
    }
    
    private void createFloor(GL2 gl, float size, int tiles, int repetition) {
        floorId = gl.glGenLists(1);

        float step = size / tiles;
	float xstart = -0.5f * size;
	float zstart = 0.5f * size;
        
        gl.glNewList(floorId, GL_COMPILE);
	gl.glNormal3f(0.0f, 1.0f, 0.0f);
        
	for (int z = 0; z < tiles; z++) {
            gl.glBegin(GL_QUAD_STRIP);
            for (int x = 0; x <= tiles; x++) {
                // Generate texture coordinate in range [0, repetition]
                gl.glTexCoord2f(1f/tiles * x * repetition,
                        1f/tiles * (z + 1) * repetition);
                gl.glVertex3f(xstart + x * step, -0.01f, zstart - (z + 1) * step);
		
                // Generate texture coordinate in range [0, repetition]
                gl.glTexCoord2f(1f/tiles * x * repetition,
                        1f/tiles * z * repetition);
                gl.glVertex3f(xstart + x * step, -0.01f, zstart - z * step);
            }
            gl.glEnd();
	}

	gl.glEndList();
    }

    private void renderObject(GL2 gl, ObjLoader objl) {
        List<float[]> vertices = objl.getVertices();
        List<float[]> normals = objl.getNormals();
        List<float[]> textures = objl.getTextures();
        List<int[]> vertexIndices = objl.getVertexIndices();
        List<int[]> normalIndices = objl.getNormalIndices();
        List<int[]> textureIndices = objl.getTextureIndices();

        
        gl.glBegin(GL_TRIANGLES);
        for (int j = 0; j < vertexIndices.size(); j++) {
            int[] face = vertexIndices.get(j);
            int[] normal = normalIndices.get(j);
            int[] texture = textureIndices.get(j);
            for (int i = 0; i < 3; i++) {
                gl.glTexCoord3fv(textures.get(texture[i]), 0);
                gl.glNormal3fv(normals.get(normal[i]), 0);
                gl.glVertex3fv(vertices.get(face[i]), 0);
            }
        }
        gl.glEnd();
    }
    

    @Override
    // metoda volana pri zmene velkosti okna
    public void reshape(GLAutoDrawable glad, int x, int y, int width, int height) {
        // Get GL2 interface
        GL2 gl = glad.getGL().getGL2();
        
        // Use projection matrix
	gl.glMatrixMode(GL_PROJECTION);

	// Set up perspective projection matrix
	gl.glLoadIdentity();
	glu.gluPerspective(60, ((double)width)/height, 1.0, 1000.0);

	// Part of the image where the scene will be renderer, (0, 0) is bottom left
	gl.glViewport(0, 0, width, height);

	// Use model view matrix
	gl.glMatrixMode(GL_MODELVIEW);
    }


    public void increasePitch() {
        updatePitch(1f);
    }
    
    public void decreasePitch() {
        updatePitch(-1f);
    }
    
    public void updatePitch(float pitch) {
        cameraX += pitch;
    }
    
    public void increaseYaw() {
        updateYaw(1f);
    }
    
    public void decreaseYaw() {
        updateYaw(-1f);
    }
    
    public void updateYaw(float yaw) {
        cameraY += yaw;
    }

    private Texture loadTexture(GL2 gl, String filename, String suffix) throws IOException {
        // Load image form resources contained within JAR
        try (InputStream is = OpenGlListener.class.getResourceAsStream(filename)) {
            Texture tex = TextureIO.newTexture(is, true, suffix);
            
            // Use mipmapping for texture filtering
            tex.setTexParameteri(gl, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            
            // Repeat texture when a texcoord is outside [0, 1]
            tex.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
            tex.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
            
            return tex;
        }
    }

    void changeLight0() {
        light0 = !light0;
    }
    
    void changeLight1() {
        light1 = !light1;
    }
    
    void changeLight2() {
        light2 = !light2;
    }
    
    private int loadProgram(GL2 gl, String vertexShaderFN, String fragmentShaderFN) throws IOException {
        // Load frament and vertex shaders (GLSL)
	int vs = loadShader(gl, vertexShaderFN, GL_VERTEX_SHADER);
	int fs = loadShader(gl, fragmentShaderFN, GL_FRAGMENT_SHADER);
        
	// Create GLSL program, attach shaders and compile it
	int program = gl.glCreateProgram();
	gl.glAttachShader(program, vs);
	gl.glAttachShader(program, fs);
	gl.glLinkProgram(program);
        
        int[] linkStatus = new int[1];
        gl.glGetProgramiv(program, GL_LINK_STATUS, linkStatus, 0);

        if (linkStatus[0] == GL_FALSE) {
            int[] length = new int[1];
            gl.glGetProgramiv(program, GL_INFO_LOG_LENGTH, length, 0);
            
            byte[] log = new byte[length[0]];
            gl.glGetProgramInfoLog(program, length[0], length, 0, log, 0);
            
            String error = new String(log, 0, length[0]);
            System.err.println(error);
        }
        
        return program;
    }
    
    private int loadShader(GL2 gl, String filename, int shaderType) throws IOException {
        String source = readFile(getClass().getResourceAsStream(filename));
        int shader = gl.glCreateShader(shaderType);
        
        // Create and compile GLSL shader
        gl.glShaderSource(shader, 1, new String[] { source }, new int[] { source.length() }, 0);
        gl.glCompileShader(shader);
        
        // Check GLSL shader compile status
        int[] status = new int[1];
        gl.glGetShaderiv(shader, GL_COMPILE_STATUS, status, 0);
        if (status[0] == GL_FALSE) {
            int[] length = new int[1];
            gl.glGetShaderiv(shader, GL_INFO_LOG_LENGTH, length, 0);
            
            byte[] log = new byte[length[0]];
            gl.glGetShaderInfoLog(shader, length[0], length, 0, log, 0);
            
            String error = new String(log, 0, length[0]);
            System.err.println(error);
        }
        
        return shader;
    }
    
    private String readFile(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        
        int c;
        while ((c = reader.read()) != -1) {
            sb.append((char) c);
        }
        
        return sb.toString();
    }
}
