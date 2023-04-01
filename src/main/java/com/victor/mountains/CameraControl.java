/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victor.mountains;

import com.victor.mountains.renderer.Callback;
import com.victor.mountains.renderer.Renderer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Supplier;
import javax.swing.JLabel;

/**
 *
 * @author bard
 */
public class CameraControl implements KeyListener {
    Renderer renderer;
    JLabel label;
    Callback callback;
    
    public CameraControl(Renderer renderer, JLabel label, Callback callback) {
        this.renderer = renderer;
        this.label = label;
        this.callback = callback;
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_UP) {
            // we need to clear the image
            renderer.increseAngle(0.1);
            renderer.renderToBuffer(callback);
            label.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
}
