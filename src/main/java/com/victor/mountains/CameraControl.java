/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victor.mountains;

import com.victor.mountains.renderer.Renderer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import javax.swing.JLabel;

/**
 *
 * @author bard
 */
public class CameraControl implements KeyListener {
    Renderer renderer;
    JLabel label;
    BufferedImage im;
    Consumer<BufferedImage> cleaner;
    
    public CameraControl(
            Renderer renderer,
            JLabel label,
            BufferedImage im,
            Consumer<BufferedImage> cleaner
    ) {
        this.renderer = renderer;
        this.label = label;
        this.im = im;
        this.cleaner = cleaner;
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_UP) {
            this.cleaner.accept(im);
            renderer.increseAngle(0.1);
            renderer.renderToBuffer(im::setRGB);
            label.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
}
