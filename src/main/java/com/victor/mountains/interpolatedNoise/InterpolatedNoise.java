/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victor.mountains.interpolatedNoise;

/**
 *
 * @author bard
 */
public class InterpolatedNoise {
    double x1;
    double y1;
    double s1;
    double init_s;
    double ls;
    int depth;
    
    public InterpolatedNoise(
            double x1,
            double y1,
            double s1,
            int depth,
            double init_s
    ) { 
        this.x1 = x1;
        this.y1 = y1;
        this.s1 = s1;
        this.init_s = init_s;
        this.ls = 2.0 - Math.pow(0.5, depth);
        this.depth = depth;
    }
    
    private static double interpolate_helper(double x) {
        if (x < 0.5) {
            return 2*Math.pow(x, 2);
        }
        return 1-(Math.pow(2-2*x, 2) / 2);
    }
    
    private static double interpolate(double x, double min, double max) {
        return min + (max - min) * InterpolatedNoise.interpolate_helper(x);
    }
    
    private double noise(double x, double y, int i) {
        double n;
        
        n = (Math.sin((x * this.x1 + y * this.y1)) *
                Math.pow(this.s1, i)) % 1.0;

        if (n < 0.0)
            return n + 1.0;
        return n;
    }
    
    public double interpolatedNoise(double x, double y) {
        double Q1x, Q1y, Q2x, Q2y, h, w, n1, n2, n3, n4, i, i1, i2, l;
        int n;
        
        x *= this.init_s;
        y *= this.init_s;
        
        for (
            n = 1, i = 0.0, l = 1.0;
            n <= this.depth;
            n++, x *= 2.0, y *= 2.0, l /= 2.0
        ) {
            Q1x = Math.floor(x);
            Q1y = Math.floor(y);
            Q2x = Math.ceil(x);
            Q2y = Math.ceil(y);

            h = y - Q1y;
            w = x - Q1x;

            n1 = this.noise(Q1x, Q1y, n);
            n2 = this.noise(Q2x, Q1y, n);
            n3 = this.noise(Q1x, Q2y, n);
            n4 = this.noise(Q2x, Q2y, n);
        
            i1 = InterpolatedNoise.interpolate(w, n1, n2);
            i2 = InterpolatedNoise.interpolate(w, n3, n4);
            
            i += InterpolatedNoise.interpolate(h, i1, i2) * l;
        }
        
        return i/this.ls;
    }
}
