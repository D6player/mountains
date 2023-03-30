/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victor.mountains.renderer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author bard
 */
public class Renderer {
    private static final int LIGHT_BLUE = 6089215;
    private static final int DARK_BLUE = 461352;
    private static final double SEPARATION = 0.05;
    private static final double LINE_WIDTH = 0.0050;
    private static final double GROUND = 0.0;
    private static final double ATENUATION = 3;
    
    private static final double GRID_WIDTH = 0.0010;
    private static final double GRID_SEPARATION = 0.035;
    
    private final Vector3[][] points;
    private final int[][] colors;
    public int[][] buffer;
    public double[][] zbuffer;
    public double angle;
    private final int width;
    private final int height;
    
    public Renderer(ScalarField noise, int width, int height, double angle) {
        this.points = new Vector3[height*2][width*2];
        this.colors = new int[height*2][width*2];
        this.width = width;
        this.height = height;
        this.angle = angle;
        
        double x, y, z;
        for (int i = 0; i < width*2; i++) {
            for(int j = 0; j < height*2; j++) {
                x = ((double) i)/((double) width*2) - 0.5;
                y = ((double) j)/((double) height*2) - 0.5;
                z = noise.getHeight(x, y);
                if (z >= Renderer.GROUND) {
                    this.points[j][i] = new Vector3(
                            x,
                            y,
                            (z - (1 - Renderer.GROUND)/2)/Renderer.ATENUATION
                    );
                    /*this.colors[j][i] = 
                        ((z % Renderer.SEPARATION) < Renderer.LINE_WIDTH) ?
                        Renderer.LIGHT_BLUE :
                        Renderer.DARK_BLUE;*/
                    this.colors[j][i] = (int) (Math.pow(2, 16)*((int) (z*255)) + 255 - ((int) (z*255)));
                } else {
                    this.points[j][i] = new Vector3(
                            x,
                            y,
                            (Renderer.GROUND - (1 - Renderer.GROUND)/2)/Renderer.ATENUATION
                    );
                    /*this.colors[j][i] = 
                        (((x+0.5) % Renderer.GRID_SEPARATION) < Renderer.GRID_WIDTH) ||
                        (((y+0.5) % Renderer.GRID_SEPARATION) < Renderer.GRID_WIDTH)?
                        Renderer.LIGHT_BLUE :
                        Renderer.DARK_BLUE;*/
                    this.colors[j][i] = 255;
                }
            }
        }
    }
    
    public void renderToBuffer() {
        Vector3 v;
        
        this.zbuffer = new double[this.height][this.width];
        this.buffer = new int[this.height][this.width];
        
        Matrix3 q = this.transformMatrix();
        
        for (int i = 0; i < this.width*2; i++) {
            for (int j = 0; j < this.height*2; j++) {
                v = q.mul(this.points[j][i]);
                this.drawToBuffer(v, this.colors[j][i]);
            }
        }
    }
    
    public void increseAngle(double a) {
        this.angle += a;
    }
    
    private void drawToBuffer(Vector3 v, int col) {
        int x, y;
        
        x = (int) ((v.x + 0.5) * (double) this.width);
        y = (int) ((v.y + 0.5) * (double) this.height);

        v.z += 10;

        if (0 <= x && x < this.width && 0 <= y && y < this.height) {
            if (v.z >= this.zbuffer[y][x]) {
                this.zbuffer[y][x] = v.z;
                this.buffer[y][x] = col;
            }
        }
    }
    
    private Matrix3 transformMatrix() {
        double c, s;
        
        c = cos(this.angle);
        s = sin(this.angle);
        
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
}
