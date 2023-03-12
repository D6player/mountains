/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victor.mountains.renderer;

/**
 *
 * @author bard
 */
public class Vector3 {
    public double x;
    public double y;
    public double z;
    
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void mul(double a) {
        this.x *= a;
        this.y *= a;
        this.z *= a;
    }
    
    public double mul(Vector3 rhs) {
        return
        this.x * rhs.x +
        this.y * rhs.y +
        this.z * rhs.z;
    }
    
    public void sum(Vector3 rhs) {
        this.x += rhs.x;
        this.y += rhs.y;
        this.z += rhs.z;
    }
    
    public void sub(Vector3 rhs) {
        rhs.mul(-1);
        this.sum(rhs);
    }
}
