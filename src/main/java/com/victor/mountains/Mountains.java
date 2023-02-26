/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.victor.mountains;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author bard
 */
public class Mountains {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    
    private static final int LIGHT_BLUE = 6089215;

    public static void main(String[] args) {
        BufferedImage im = new BufferedImage(
                Mountains.WIDTH,
                Mountains.HEIGHT,
                BufferedImage.TYPE_INT_RGB
        );
        
        PerlinNoise p = new PerlinNoise(12.9898, 78.233, 43758.5453123);
        
        int col;
        double n, x, y;
        for (int i = 0; i<Mountains.WIDTH; i++) {
            for (int j = 0; j < Mountains.HEIGHT; j++) {
                x = ((double) i)/((double) Mountains.WIDTH);
                y = ((double) j)/((double) Mountains.HEIGHT);
                
                x *= 10.0;
                y *= 10.0;
                
                n = p.perlinNoise(Math.floor(x), Math.floor(y));
                
                col = (int) (n*255.0);
                col = col + col*256 + col*65536;
                
                im.setRGB(i, j, col);
            }
        }
        
        JFrame hello = new JFrame("Hello World");
        hello.add(new JLabel(new ImageIcon(im)));
        //hello.setBounds(500, 500, 500, 500);
        hello.pack();
        hello.setVisible(true);
        hello.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
