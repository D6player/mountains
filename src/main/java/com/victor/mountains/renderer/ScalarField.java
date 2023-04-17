/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.victor.mountains.renderer;

/**
 *
 * @author bard
 */
public interface ScalarField {
    public double getHeight(double x, double y) throws UndefinedScalarException;
    
    public class UndefinedScalarException extends Exception {
        public UndefinedScalarException() {
            super("Value not defined in scalar field");
        }
    }
}
