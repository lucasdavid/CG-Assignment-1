package org.CG;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import org.CG.infrastructure.drawings.Drawing;
import org.CG.infrastructure.Pair;
import org.CG.infrastructure.drawings.LineInPixelMatrix;

/**
 * CGAssignment1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class CGAssignment1 implements GLEventListener {

    LinkedList<Pair<Drawing, int[]>> drawings;

    public CGAssignment1() {
        this(null);
    }

    public CGAssignment1(LinkedList<Pair<Drawing, int[]>> drawings) {
        if (drawings == null) {
            drawings = new LinkedList<Pair<Drawing, int[]>>();

            drawings.add(new Pair<Drawing, int[]>(new LineInPixelMatrix(), new int[]{0, 0, 100, 100, randomColor(), randomColor(), randomColor()}));
            drawings.add(new Pair<Drawing, int[]>(new LineInPixelMatrix(), new int[]{0, 0, 100, 50, randomColor(), randomColor(), randomColor()}));
            drawings.add(new Pair<Drawing, int[]>(new LineInPixelMatrix(), new int[]{0, 0, 50, 100, randomColor(), randomColor(), randomColor()}));

            drawings.add(new Pair<Drawing, int[]>(new LineInPixelMatrix(), new int[]{120, 100, 120, 200, randomColor(), randomColor(), randomColor()}));
            drawings.add(new Pair<Drawing, int[]>(new LineInPixelMatrix(), new int[]{120, 100, 220, 100, randomColor(), randomColor(), randomColor()}));
            
            drawings.add(new Pair<Drawing, int[]>(new LineInPixelMatrix(), new int[]{250, 100, 350, 95, randomColor(), randomColor(), randomColor()}));
            drawings.add(new Pair<Drawing, int[]>(new LineInPixelMatrix(), new int[]{250, 100, 350, 20, randomColor(), randomColor(), randomColor()}));
            
            drawings.add(new Pair<Drawing, int[]>(new LineInPixelMatrix(), new int[]{400, 200, 350, 0, randomColor(), randomColor(), randomColor()}));
            
            drawings.add(new Pair<Drawing, int[]>(new LineInPixelMatrix(), new int[]{450, 300, 350, 400, randomColor(), randomColor(), randomColor()}));
        }

        this.drawings = drawings;
    }
    
    protected static int randomColor() {
        return (int)(Math.random() * 256);
    }

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new CGAssignment1());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, 0, height, -1, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        for (Pair p : this.drawings) {
            ((Drawing) p.getLeft()).draw(gl, (int[]) p.getRight());
        }

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
