/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.victor.mountains;

import com.victor.mountains.interpolatedNoise.InterpolatedNoise;
import java.awt.image.BufferedImage;
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
    
    private static final int LIGHT_BLUE = 6089215;
    //private static final int DARK_BLUE = 0;

    public static void main(String[] args) {
        BufferedImage im = new BufferedImage(
                Mountains.WIDTH,
                Mountains.HEIGHT,
                BufferedImage.TYPE_INT_RGB
        );
        
        InterpolatedNoise p = new InterpolatedNoise(
                12.9898,
                78.233,
                43758.5453123,
                3,
                8.0
        );
        
        int col;
        double n, x, y;
        for (int i = 0; i<Mountains.WIDTH; i++) {
            for (int j = 0; j < Mountains.HEIGHT; j++) {
                x = ((double) i)/((double) Mountains.WIDTH);
                y = ((double) j)/((double) Mountains.HEIGHT);
                
                n = p.interpolatedNoise(x, y);
                
                col = 0;
                if ((n % 0.1) < 0.005) {
                    col = Mountains.LIGHT_BLUE;
                }
                //col = (int) (n*255.0);
                //col = col + col*256 + col*65536;
                
                im.setRGB(i, j, col);
            }
        }
        
        JFrame frame = new JFrame("Test frame");
        frame.add(new JLabel(new ImageIcon(im)));
        //hello.setBounds(500, 500, 500, 500);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
