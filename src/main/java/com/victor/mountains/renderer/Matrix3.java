/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victor.mountains.renderer;

/**
 *
 * @author bard
 */
public class Matrix3 {
    public Vector3 f1;
    public Vector3 f2;
    public Vector3 f3;
    
    public Matrix3(
            double a, double b, double c,
            double d, double e, double f,
            double g, double h, double i
    ) {
        this.f1 = new Vector3(a, b, c);
        this.f2 = new Vector3(d, e, f);
        this.f3 = new Vector3(g, h, i);
    }
    
    public void transpose() {
        double aux;
        
        aux = this.f1.y;
        this.f1.y = this.f2.x;
        this.f2.x = aux;
        
        aux = this.f1.z;
        this.f1.z = this.f3.x;
        this.f3.x = aux;
        
        aux = this.f2.z;
        this.f2.z = this.f3.y;
        this.f3.y = aux;
    }
    
    public Matrix3 mul(Matrix3 rhs) {
        rhs.transpose();
        return new Matrix3(
            this.f1.mul(rhs.f1), this.f1.mul(rhs.f2), this.f1.mul(rhs.f3),
            this.f2.mul(rhs.f1), this.f2.mul(rhs.f2), this.f3.mul(rhs.f3),
            this.f3.mul(rhs.f1), this.f3.mul(rhs.f2), this.f3.mul(rhs.f3)
        );
    }
    
    public Vector3 mul(Vector3 rhs) {
        return new Vector3(
                this.f1.mul(rhs),
                this.f2.mul(rhs),
                this.f3.mul(rhs)
        );
    }
}
