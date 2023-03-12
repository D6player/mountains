/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victor.mountains.renderer;

import com.victor.mountains.interpolatedNoise.InterpolatedNoise;

/**
 *
 * @author bard
 */
public class Renderer {
    private static final int LIGHT_BLUE = 6089215;
    private static final int DARK_BLUE = 461352;
    private static final double SEPARATION = 0.1;
    private static final double LINE_WIDTH = 0.005;
    
    private Vector3[][] points;
    private int[][] colors;
    public int[][] buffer;
    public double[][] zbuffer;
    private int width;
    private int height;
    
    public Renderer(InterpolatedNoise noise, int width, int height) {
        this.points = new Vector3[height][width];
        this.colors = new int[height][width];
        this.buffer = new int[height][width];
        this.zbuffer = new double[height][width];
        this.width = width;
        this.height = height;
        
        double x, y, z;
        for (int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                x = ((double) i)/((double) width);
                y = ((double) j)/((double) height);
                z = noise.interpolatedNoise(x, y);
                
                this.points[j][i] = new Vector3(x, y, z);
                this.colors[j][i] = 
                        ((z % Renderer.SEPARATION) < Renderer.LINE_WIDTH) ?
                        Renderer.LIGHT_BLUE :
                        Renderer.DARK_BLUE;
            }
        }
    }
    
    public void renderToBuffer(double angle) {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.buffer[j][i] = this.colors[j][i];
            }
        }
    }
}
