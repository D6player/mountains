/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.victor.mountains;

import com.victor.mountains.interpolatedNoise.InterpolatedNoise;
import com.victor.mountains.renderer.Renderer;
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
                12.9898,
                78.233,
                43758.5453123,
                3,
                2.5
        );
        
        Renderer renderer = new Renderer(noise, Mountains.WIDTH, Mountains.HEIGHT);
        renderer.renderToBuffer(Math.PI/3.5, 0.4);
        
        for (int i = 0; i < Mountains.WIDTH; i++) {
            for (int j = 0; j < Mountains.HEIGHT; j++) {
                im.setRGB(i, j, renderer.buffer[j][i]);
            }
        }
        
        File outputfile = new File("test.png");
        ImageIO.write(im, "png", outputfile);
        
        JFrame frame = new JFrame("Test frame");
        frame.add(new JLabel(new ImageIcon(im)));
        //hello.setBounds(500, 500, 500, 500);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
