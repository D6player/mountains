/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.victor.mountains;

import com.victor.mountains.interpolatedNoise.InterpolatedNoise;
import com.victor.mountains.renderer.Renderer;
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

    public static void main(String[] args) {
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
        
        renderer.renderToBuffer(im::setRGB);
        
        JLabel label = new JLabel(new ImageIcon(im));
        JFrame frame = new JFrame("Mountains");
        frame.add(label);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new CameraControl(
                renderer,
                label,
                im,
                Mountains::cleanImage
        ));
    }
    
    public static void cleanImage(BufferedImage im) {
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < WIDTH; j++)
                im.setRGB(i, j, 0);
    }
    
}
