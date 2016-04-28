package openGLDemos;

import java.awt.Color;
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
import javax.swing.JFrame;
import com.jogamp.opengl.util.Animator;
 
public class WireCube implements GLEventListener, KeyListener {
 
    static GLU glu = new GLU(); 					//Creates an object for the OpenGL Utility Library
    static GLCanvas canvas = new GLCanvas(); 		// Open GL Rendering Support
    static Frame frame = new Frame("Title"); 		//Creates a window to draw on
    static Animator animator = new Animator(canvas);//Creates our animation loop by repeatedly calling display();
    boolean[] keys = new boolean[256];    			//Storage for keys for user interface.     
    // ************** Declare and initialize variables here *******************
   
    double xrot = 12.5, yrot = 0, zrot = 0;
   
    
    
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

        
        
        // **** end initialization of variables ****
    }
   
    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2(); // Gets an OpenGL drawable area called gl from glDrawable
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);	// Clear Screen
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);	// Depth Buffer
        gl.glLoadIdentity();				// Reset The Current Modelview Matrix
        gl.glTranslated(0, 0, -8);
        gl.glRotated(xrot, 1,0,0);
        gl.glRotated(yrot, 0,1,0);
        gl.glRotated(zrot, 0,0,1);
        coordinatePlane(gl);
        // **** BEGIN DRAWINGS HERE ****
        
        wireCube(gl);
        pyramid(gl);
        quadBase(gl);
       
         bowlShape(gl);
        
        // **** END DRAWINGS ****
        
        processKeys();
        
        
        
    }
    
    public void bowlShape(GL2 gl){
    	double insideY = -1;
    	double outsideY = -2;
    	this.setColor(gl, Color.GREEN);
    	gl.glBegin(GL2.GL_QUAD_STRIP);
    	gl.glVertex3d(1,insideY,0);
		gl.glVertex3d(2,outsideY,0);
		
		
		gl.glVertex3d(1.5/2,insideY,-1.5/2);
		gl.glVertex3d(1.5,outsideY,-1.5);
		
		gl.glVertex3d(0,insideY,-1);
		gl.glVertex3d(0,outsideY,-2);
		
		gl.glVertex3d(-1.5/2,insideY,-1.5/2);
		gl.glVertex3d(-1.5,outsideY,-1.5);
		
		gl.glVertex3d(-1,insideY,0);
		gl.glVertex3d(-2,outsideY,0);
		
		gl.glVertex3d(-1.5/2,insideY,1.5/2);
		gl.glVertex3d(-1.5,outsideY,1.5);
		
		gl.glVertex3d(0,insideY,1);
		gl.glVertex3d(0,outsideY,2);
		
		gl.glVertex3d(1.5/2,insideY,1.5/2);
		gl.glVertex3d(1.5,outsideY,1.5);
		gl.glVertex3d(1,insideY,0);
		gl.glVertex3d(2,outsideY,0);
    		
    		
    	gl.glEnd();
    	this.setColor(gl, Color.BLACK);
    	
    	gl.glBegin(GL2.GL_LINES);
    		gl.glVertex3d(1,insideY,0);
    		gl.glVertex3d(2,outsideY,0);
    		
    		
    		gl.glVertex3d(1.5/2,insideY,-1.5/2);
    		gl.glVertex3d(1.5,outsideY,-1.5);
    		
    		gl.glVertex3d(0,insideY,-1);
    		gl.glVertex3d(0,outsideY,-2);
    		
    		gl.glVertex3d(-1.5/2,insideY,-1.5/2);
    		gl.glVertex3d(-1.5,outsideY,-1.5);
    		
    		gl.glVertex3d(-1,insideY,0);
    		gl.glVertex3d(-2,outsideY,0);
    		
    		gl.glVertex3d(-1.5/2,insideY,1.5/2);
    		gl.glVertex3d(-1.5,outsideY,1.5);
    		
    		gl.glVertex3d(0,insideY,1);
    		gl.glVertex3d(0,outsideY,2);
    		
    		gl.glVertex3d(1.5/2,insideY,1.5/2);
    		gl.glVertex3d(1.5,outsideY,1.5);
    		gl.glVertex3d(1,insideY,0);
    		gl.glVertex3d(2,outsideY,0);
    		
    		
    	gl.glEnd();
    	
    	this.setColor(gl, Color.YELLOW);
    	
    	gl.glBegin(GL2.GL_POLYGON);
    	gl.glVertex3d(1,insideY,0);
    	gl.glVertex3d(1.5/2,insideY,-1.5/2);
    	gl.glVertex3d(0,insideY,-1);
    	gl.glVertex3d(-1.5/2,insideY,-1.5/2);
    	gl.glVertex3d(-1,insideY,0);
    	gl.glVertex3d(-1.5/2,insideY,1.5/2);
    	
    	gl.glVertex3d(0,insideY,1);
    	gl.glVertex3d(1.5/2,insideY,1.5/2);
    	
    	gl.glEnd();
    	this.setColor(gl, Color.BLACK);
    	
    	gl.glBegin(GL2.GL_LINE_LOOP);
    	gl.glVertex3d(1,insideY,0);
    	gl.glVertex3d(1.5/2,insideY,-1.5/2);
    	gl.glVertex3d(0,insideY,-1);
    	gl.glVertex3d(-1.5/2,insideY,-1.5/2);
    	gl.glVertex3d(-1,insideY,0);
    	gl.glVertex3d(-1.5/2,insideY,1.5/2);
    	
    	gl.glVertex3d(0,insideY,1);
    	gl.glVertex3d(1.5/2,insideY,1.5/2);
    	
    	gl.glEnd();
    	
    	
    }
    
    public void processKeys(){
    	 // **** MAKE DECISIONS ABOUT WHAT KEYS ARE PRESSED ****

    	if(keys['Q']){
    		xrot+=0.1;
    	}       
    	if(keys['W']){
    		xrot-=0.1;
    	}
    	//if(keys['A']){
    		yrot+=0.1;
    	//}       
    	if(keys['S']){
    		yrot-=0.1;
    	}
    	if(keys['Z']){
    		zrot+=0.1;
    	}       
    	if(keys['X']){
    		zrot-=0.1;
    	}
    	
    	// **** END USER INTERFACE DECISIONS *******
    	if (keys[KeyEvent.VK_ESCAPE]) {		//if the escape key is pressed quit
            //exit();
    		System.exit(0);
        }
    }
    
    public void pyramid(GL2 gl){
    	//front
    	this.setColor(gl, Color.MAGENTA);
    	gl.glBegin(GL2.GL_TRIANGLES);
    		gl.glVertex3d(0.5,-0.5,0.5);
    		gl.glVertex3d(0.0,0.5,0.0);
    		gl.glVertex3d(-0.5,-0.5,0.5);
    	gl.glEnd();
    	this.setColor(gl, Color.LIGHT_GRAY);
    	gl.glBegin(GL2.GL_TRIANGLES);
    		gl.glVertex3d(0.5,-0.5,-0.5);
    		gl.glVertex3d(0.0,0.5,0.0);
    		gl.glVertex3d(-0.5,-0.5,-0.5);
    	gl.glEnd();
    	this.setColor(gl, Color.YELLOW);
    	gl.glBegin(GL2.GL_TRIANGLES);
    		gl.glVertex3d(0.5,-0.5,-0.5);
    		gl.glVertex3d(0.0,0.5,0.0);
    		gl.glVertex3d(0.5,-0.5,0.5);
    	gl.glEnd();
    	this.setColor(gl, Color.ORANGE);
    	gl.glBegin(GL2.GL_TRIANGLES);
    		gl.glVertex3d(-0.5,-0.5,-0.5);
    		gl.glVertex3d(-0.0,0.5,0.0);
    		gl.glVertex3d(-0.5,-0.5,0.5);
    	gl.glEnd();
    	
    }
    
    public void quadBase(GL2 gl){
    	
    	//front
    	this.setColor(gl, Color.RED);
    	gl.glBegin(GL2.GL_QUADS);
    		gl.glVertex3d(0.5,-1,0.5);
    		gl.glVertex3d(0.5,-0.5,0.5);
    		gl.glVertex3d(-0.5,-0.5,0.5);
    		gl.glVertex3d(-0.5,-1,0.5);
    	gl.glEnd();
    	
    	this.setColor(gl, Color.CYAN);
    	gl.glBegin(GL2.GL_QUADS);
    		gl.glVertex3d(0.5,-1,-0.5);
    		gl.glVertex3d(0.5,-0.5,-0.5);
    		gl.glVertex3d(-0.5,-0.5,-0.5);
    		gl.glVertex3d(-0.5,-1,-0.5);
    	gl.glEnd();
    	
    	this.setColor(gl, Color.BLUE);
    	gl.glBegin(GL2.GL_QUADS);
    		gl.glVertex3d(0.5,-1,0.5);
    		gl.glVertex3d(0.5,-1,-0.5);
    		gl.glVertex3d(0.5,-0.5,-0.5);
    		gl.glVertex3d(0.5,-0.5,0.5);    		
    	gl.glEnd();
    	this.setColor(gl, Color.GREEN);
    	gl.glBegin(GL2.GL_QUADS);
    		gl.glVertex3d(-0.5,-1,0.5);
    		gl.glVertex3d(-0.5,-1,-0.5);
    		gl.glVertex3d(-0.5,-0.5,-0.5);
    		gl.glVertex3d(-0.5,-0.5,0.5);    		
    	gl.glEnd();
    }
    
    public void wireCube(GL2 gl){
    	gl.glColor3d(1, 1, 1);
    	//front
    	gl.glBegin(GL2.GL_LINES);
	    	gl.glVertex3d(1, 1, 1);
	    	gl.glVertex3d(1, -1, 1);
	    	gl.glVertex3d(-1, 1, 1);
	    	gl.glVertex3d(-1, -1, 1);    	
	    	gl.glVertex3d(-1, 1, 1);
	    	gl.glVertex3d(1, 1, 1);    	
	    	gl.glVertex3d(-1, -1, 1);
	    	gl.glVertex3d(1, -1, 1);    	
    	gl.glEnd();
    	
    	//back
    	gl.glBegin(GL2.GL_LINES);
	    	gl.glVertex3d(1, 1, -1);
	    	gl.glVertex3d(1, -1, -1);
	    	gl.glVertex3d(-1, 1, -1);
	    	gl.glVertex3d(-1, -1, -1);    	
	    	gl.glVertex3d(-1, 1, -1);
	    	gl.glVertex3d(1, 1, -1);    	
	    	gl.glVertex3d(-1, -1, -1);
	    	gl.glVertex3d(1, -1, -1);    	
    	gl.glEnd();
    	
    	//connecting lines
    	gl.glBegin(GL2.GL_LINES);
	    	gl.glVertex3d(1, 1, 1);
	    	gl.glVertex3d(1, 1, -1);	    	    
	    	
	    	gl.glVertex3d(1, -1, 1);
	    	gl.glVertex3d(1, -1, -1);
	    	
	    	gl.glVertex3d(-1, 1, 1);
	    	gl.glVertex3d(-1, 1, -1);
	    	
	    	gl.glVertex3d(-1, -1, 1);
	    	gl.glVertex3d(-1, -1, -1);
	    gl.glEnd();
    	
    }

    public void keyPressed(KeyEvent e) {
       int key = e.getKeyCode(); 			//read key code
       keys[key] = true;					//if a key is pressed, set that key to true
    	
      
    }
 
    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }
 
    public void coordinatePlane(GL2 gl){
    	gl.glBegin(GL2.GL_LINES);
    	//red x axis
    	gl.glColor3d(1, 0, 0);
    	gl.glVertex3d(-1, 0, 0);
    	gl.glVertex3d(1, 0, 0);
    	
    	//green y axis
    	gl.glColor3d(0, 1, 0);
    	gl.glVertex3d(0, -1, 0);
    	gl.glVertex3d(0, 1, 0);
    	
    	//blue z axis
    	gl.glColor3d(0, 0, 1);
    	gl.glVertex3d(0, 0, -1);
    	gl.glVertex3d(0, 0, 1);
    	gl.glEnd();
    	
    }
    public void setColor(GL2 gl, Color c){
    	   double r = c.getRed()/255.0;
    	   double g = c.getGreen()/255.0;
    	   double b = c.getBlue()/255.0;
    	   gl.glColor3d(r, g, b);    	
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
        
    	canvas.addGLEventListener(new WireCube()); // <-- ADD CLASS NAME HERE!!!
       
        frame.add(canvas);									//adds our drawing canvas to the window
        //frame.setSize(640, 480);
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH); //maximizes the window
        //frame.setUndecorated(true);							//removes menu bar
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




