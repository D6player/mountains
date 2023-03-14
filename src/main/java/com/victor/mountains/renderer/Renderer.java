/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victor.mountains.renderer;

import com.victor.mountains.interpolatedNoise.InterpolatedNoise;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author bard
 */
public class Renderer {
    private static final int LIGHT_BLUE = 6089215;
    private static final int DARK_BLUE = 461352;
    private static final double SEPARATION = 0.075;
    private static final double LINE_WIDTH = 0.0075;
    
    private final Vector3[][] points;
    private final int[][] colors;
    public int[][] buffer;
    public double[][] zbuffer;
    private final int width;
    private final int height;
    
    public Renderer(InterpolatedNoise noise, int width, int height) {
        this.points = new Vector3[height*2][width*2];
        this.colors = new int[height*2][width*2];
        this.width = width;
        this.height = height;
        
        double x, y, z;
        for (int i = 0; i < width*2; i++) {
            for(int j = 0; j < height*2; j++) {
                x = ((double) i)/((double) width) - 0.5;
                y = ((double) j)/((double) height) - 0.5;
                z = noise.interpolatedNoise(x, y);
                
                this.points[j][i] = new Vector3(x, y, (z-0.5)/2);
                this.colors[j][i] = 
                        ((z % Renderer.SEPARATION) < Renderer.LINE_WIDTH) ?
                        Renderer.LIGHT_BLUE :
                        Renderer.DARK_BLUE;
            }
        }
    }
    
    public void renderToBuffer(double angle, double scale) {
        this.zbuffer = new double[this.height][this.width];
        this.buffer = new int[this.height][this.width];
        
        double c, s;
        int x, y;
        Vector3 v;
        
        c = cos(angle);
        s = sin(angle);
        
        Matrix3 m = new Matrix3(
                0.707, -0.707, 0,
                0.707, 0.707, 0,
                0, 0, 1
        );
        
        Matrix3 n = new Matrix3(
                1, 0, 0,
                0, c, -s,
                0, s, c
        );
        
        Matrix3 p = new Matrix3(
                scale, 0, 0,
                0, scale, 0,
                0, 0, scale
        );
        
        Matrix3 q = n.mul(m).mul(p);
        
        for (int i = 0; i < this.width*2; i++) {
            for (int j = 0; j < this.height*2; j++) {
                v = q.mul(this.points[j][i]);
                x = (int) ((v.x + 0.5) * (double) this.width);
                y = (int) ((v.y + 0.5) * (double) this.height);
                
                v.z += 10;
                
                if (0 <= x && x < this.width && 0 <= y && y < this.height) {
                    if (v.z >= this.zbuffer[y][x]) {
                        this.zbuffer[y][x] = v.z;
                        this.buffer[y][x] = this.colors[j][i];
                    }
                }
            }
        }
    }
}
