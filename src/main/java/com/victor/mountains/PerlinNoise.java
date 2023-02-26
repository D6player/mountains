/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victor.mountains;

/**
 *
 * @author bard
 */
public class PerlinNoise {
    double x1;
    double y1;
    double s1;
    
    public PerlinNoise(double x1, double y1, double s1) {
        this.x1 = x1;
        this.y1 = y1;
        this.s1 = s1;
    }
    
    private double noise(double x, double y) {
        return (Math.sin((x * this.x1 + y * this.y1)) * this.s1) % 1.0;
    }
    
    public double perlinNoise(double x, double y) {
        // todo
        return this.noise(x, y);
    }
}
