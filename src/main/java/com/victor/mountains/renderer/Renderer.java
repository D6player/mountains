/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victor.mountains.renderer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;

/**
 *
 * @author bard
 */
public class Renderer {
    private static final int LIGHT_BLUE = 6089215;
    private static final int DARK_BLUE = 461352;
    private static final double SEPARATION = 0.05;
    private static final double LINE_WIDTH = 0.0050;
    private static final double GROUND = 0.35;
    private static final double ATENUATION = 3;
    
    private static final double GRID_WIDTH = 0.0010;
    private static final double GRID_SEPARATION = 0.035;
    
    private final ArrayList<Point> points;
    public double[][] zbuffer;
    public double angle;
    private final int width;
    private final int height;
    
    public Renderer(ScalarField noise, int width, int height, double angle) {
        this.points = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.angle = angle;
        
        double x, y, z;
        Vector3 pos;
        int col;
        for (int i = 0; i < width*2; i++) {
            for(int j = 0; j < height*2; j++) {
                x = ((double) i)/((double) width*2) - 0.5;
                y = ((double) j)/((double) height*2) - 0.5;
                z = noise.getHeight(x, y);
                if (z >= GROUND) {
                    pos = new Vector3(
                            x,
                            y,
                            z/ATENUATION
                    );
                    col = 
                        ((z % Renderer.SEPARATION) < Renderer.LINE_WIDTH) ?
                        Renderer.LIGHT_BLUE :
                        Renderer.DARK_BLUE;
                    //col = (int) (Math.pow(2, 16)*((int) (z*255)) + 255 - ((int) (z*255)));
                } else {
                    pos = new Vector3(
                            x,
                            y,
                            GROUND/ATENUATION
                    );
                    col = 
                        (((x+0.5) % Renderer.GRID_SEPARATION) < Renderer.GRID_WIDTH) ||
                        (((y+0.5) % Renderer.GRID_SEPARATION) < Renderer.GRID_WIDTH)?
                        Renderer.LIGHT_BLUE :
                        Renderer.DARK_BLUE;
                    //col = 255;
                }
                
                this.points.add(new Point(pos, col));
            }
        }
    }
    
    /**
     *
     * @param callback
     */
    public void renderToBuffer(Callback callback) {
        Vector3 v;
        
        this.zbuffer = new double[height][width];
        
        Matrix3 q = this.transformMatrix();
        
        for (Point p : points) {
            v = q.mul(p.pos);
            this.drawToBuffer(v, p.col, callback);
        }
    }
    
    public void increseAngle(double a) {
        this.angle += a;
    }
    
    private void drawToBuffer(Vector3 v, int col, Callback callback) {
        int x, y;
        
        x = (int) ((v.x + 0.5) * (double) width);
        y = (int) ((v.y + 0.5) * (double) height);

        if (this.isVisible(x, y, v.z)) {
            callback.callback(x, y, col);
        }
    }
    
    private Matrix3 transformMatrix() {
        double c, s;
        
        c = cos(angle);
        s = sin(angle);
        
        Matrix3 m = new Matrix3(
                c, -s, 0,
                s, c, 0,
                0, 0, 1
        );
        
        Matrix3 n = new Matrix3(
                1, 0, 0,
                0, 0.6234, -0.7818,
                0, 0.7818, 0.6234
        );
        
        //  P -> M -> N
        return n.mul(m);
    }
    
    private boolean isVisible(int x, int y, double z) {
        z += 10; // precaution value
        
        if (
                0 <= x && x < this.width &&
                0 <= y && y < this.height
            ) {
            
            if(z >= this.zbuffer[y][x]) {
                this.zbuffer[y][x] = z;
                return true;
            }
        }
        return false;
    }
}
