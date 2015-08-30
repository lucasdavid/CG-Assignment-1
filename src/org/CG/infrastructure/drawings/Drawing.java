package org.CG.infrastructure.drawings;

import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public abstract class Drawing {
    int[] start;
    byte[] color;

    public Drawing(int[] start, byte[] color) {
        this.color = color;
        this.start = start;
    }
    
    abstract public void updateLastCoordinateInputted(int[] point);
    
    abstract public void draw(GL gl);
}
