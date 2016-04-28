package openGL;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;
 
public class OpenGLTemplate implements GLEventListener, KeyListener {
 
    static GLU glu = new GLU(); 					//Creates an object for the OpenGL Utility Library
    static GLCanvas canvas = new GLCanvas(); 		// Open GL Rendering Support
    static Frame frame = new Frame("Title"); 		//Creates a window to draw on
    static Animator animator = new Animator(canvas);//Creates our animation loop by repeatedly calling display();
    boolean[] keys = new boolean[256];    			//Storage for keys for user interface.     
    // ************** Declare and initialize variables here *******************
    private GLUquadric shape;
    double xrot =0, yrot=0, zrot=0;
    double zoom = -5;
    
    
    CoordinatePlane plane;
    WireCube cube;
    ObeliskScene obeliskScene;
    ExampleQuadrics exampleQuadrics;
  
    
    
    
    
   // *************** End variable declarations and initializations ***********
    public void init(GLAutoDrawable gLDrawable) {
        GL2 gl = gLDrawable.getGL().getGL2();		// Gets an OpenGL drawable area called gl from glDrawable
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);	// Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);	// Black Background
        gl.glClearDepth(1.0f);						// Depth Buffer Setup
        gl.glEnable(GL.GL_DEPTH_TEST);				// Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);				// The Type Of Depth Testing To Do
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // Really Nice Perspective Calculations
        ((Component) gLDrawable).addKeyListener(this); //Adds a listener for the keyboard for user interface
       
        // **** initialize any variables here ****
        shape = glu.gluNewQuadric();

        glu.gluQuadricNormals(shape, GLU.GLU_SMOOTH);

        glu.gluQuadricTexture(shape, true);
        
       // gl.glEnable(GL.GL_TEXTURE_2D);
        
        plane = new CoordinatePlane();
        cube = new WireCube();
        obeliskScene = new ObeliskScene(gl);
        exampleQuadrics = new ExampleQuadrics(gl);
        
        // **** end initialization of variables ****
   
    }
   
    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2(); // Gets an OpenGL drawable area called gl from glDrawable
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);	// Clear Screen
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);	// Depth Buffer
        gl.glLoadIdentity();				// Reset The Current Modelview Matrix
        
        // **** BEGIN DRAWINGS HERE ****
        gl.glTranslated(0, 0, zoom);
        gl.glRotated(xrot, 1, 0, 0);
        gl.glRotated(yrot, 0, 1, 0);
        gl.glRotated(zrot, 0, 0, 1);
        
        plane.draw(gl);
        cube.draw(gl);
        cube.increaseAngle();
        //obeliskScene.draw(gl, glu, shape);
        //exampleQuadrics.draw(gl, glu, shape);
        
        
        // **** END DRAWINGS ****
	processKeys();     
    }

    public void processKeys(){
 	// **** MAKE DECISIONS ABOUT WHAT KEYS ARE PRESSED ****

    	 
    	if(keys['O']){
    		zoom += 0.1;
    	}
    	if(keys['P']){
    		zoom -= 0.1;
    	}
    	
    	
    	if(keys['Q']){
    		xrot += 0.1;
    	}
    	if(keys['W']){
    		xrot -= 0.1;
    	}
    	if(keys['A']){
    		yrot += 0.1;
    	}
    	if(keys['S']){
    		yrot -= 0.1;
    	}
    	if(keys['Z']){
    		zrot += 0.1;
    	}
    	if(keys['X']){
    		zrot -= 0.1;
    	}
       
       
    	// **** END USER INTERFACE DECISIONS *******
    	if (keys[KeyEvent.VK_ESCAPE]) {		//if the escape key is pressed quit
            exit();
        }
    }

    public void keyPressed(KeyEvent e) {
       int key = e.getKeyCode(); 			//read key code
       keys[key] = true;					//if a key is pressed, set that key to true
    	
      
    }
 
    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }
 

 
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
        GL2 gl = gLDrawable.getGL().getGL2();	// Gets an OpenGL drawable area called gl from glDrawable
        if (height <= 0) {						//make sure there is a height of the window
            height = 1;
        }
        float h = (float) width / (float) height;
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION); //sets the current matrix
        gl.glLoadIdentity();						 // Reset The Current Modelview Matrix
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);	// Calculate The Aspect Ratio Of The Window
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();						// Reset The Current Modelview Matrix
    }
 
 
 
    public void keyReleased(KeyEvent e) {
    	int key = e.getKeyCode();				//get key code
    	keys[key] = false;						//if a key is released set to false.
    }
 
    public void keyTyped(KeyEvent e) {
    }
 
    public static void exit() {
        animator.stop();						//stop animation.
        frame.dispose();						//destroy the window.
        System.exit(0);							//stop the program
    }
 
    public static void main(String[] args) {
        
    	canvas.addGLEventListener(new OpenGLTemplate()); // <-- ADD CLASS NAME HERE!!!
       
        frame.add(canvas);									//adds our drawing canvas to the window
        //frame.setSize(640, 480);
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH); //maximizes the window
        frame.setUndecorated(true);							//removes menu bar
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.addWindowListener(new WindowAdapter() {		//adds user interface to the window
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        frame.setVisible(true);								//show the window
        animator.start();									//start the animation
        canvas.requestFocus();								//give user interface to the drawing area
    }	
 
    public void dispose(GLAutoDrawable gLDrawable) {
        // do nothing
    }
}




