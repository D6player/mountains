/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.victor.mountains;

import com.victor.mountains.interpolatedNoise.InterpolatedNoise;
import com.victor.mountains.renderer.Renderer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author bard
 */
public class Mountains {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    public static void main(String[] args) throws IOException {
        BufferedImage im = new BufferedImage(
                Mountains.WIDTH,
                Mountains.HEIGHT,
                BufferedImage.TYPE_INT_RGB
        );
        
        InterpolatedNoise noise = new InterpolatedNoise(
                14.9898,
                87.233,
                478758.5453123,
                3,
                6
        );
        
        Renderer renderer = new Renderer(
                noise,
                Mountains.WIDTH,
                Mountains.HEIGHT,
                Math.PI/4
        );
        
        renderer.renderToBuffer();
        for (int i = 0; i < Mountains.WIDTH; i++) {
                for (int j = 0; j < Mountains.HEIGHT; j++) {
                    im.setRGB(i, j, renderer.buffer[j][i]);
                }
        }
        
        //File outputfile = new File("test.png");
        //ImageIO.write(im, "png", outputfile);
        
        JLabel label = new JLabel(new ImageIcon(im));
        JFrame frame = new JFrame("Test frame");
        frame.add(label);
        //hello.setBounds(500, 500, 500, 500);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    renderer.increseAngle(0.1);
                    renderer.renderToBuffer();
                    for (int i = 0; i < Mountains.WIDTH; i++) {
                        for (int j = 0; j < Mountains.HEIGHT; j++) {
                            im.setRGB(i, j, renderer.buffer[j][i]);
                        }
                    }
                    label.repaint();
                }
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        });
        
    }
}
