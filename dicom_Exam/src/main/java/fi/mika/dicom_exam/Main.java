/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.mika.dicom_exam;

import org.dcm4che3.imageio.plugins.dcm.*;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomOutputStream;
import java.awt.*;
import java.awt.image.*;

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * Main Class to show medical image array on screen.
 * @author Mika
 */
public class Main {
    
    /**
     * Shows an array of medical BufferedImage in a slideshow in JFrame
     * @param imgarr
     * array of medical BufferedImage 
     * @param frametitle 
     * Title for the JFrame window. Can be left as an empty string.
     */

    public static void showSlicesAtJFrame(BufferedImage[] imgarr, String frametitle) {
        JFrame frame = new JFrame(frametitle);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //i<max
        for (int i = 0; i < imgarr.length; i++) {
            BufferedImage bimage = imgarr[i];

            frame.setSize(Math.max(bimage.getWidth(), 512), Math.max(bimage.getHeight(), 512));
            //System.out.println("Framesize: w:"+bimage.getWidth()+", h:"+ bimage.getHeight());
            JPanel jpanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(bimage, 0, 0, null);

                }
            };

            frame.add(jpanel);
            frame.setVisible(true);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.remove(jpanel);

        }

    }
    
    /**
     * Main function
     * TODO: let the user choose their files instead of static reference.
     * TODO: let the user scroll images instead of a slideshow.
     * @param args 
     * unused
     */
    
    public static void main(String[] args) {
        File dicomFile = new File("./E1154S7I.dcm");
        int max = Short.MAX_VALUE;

        SizeHelper.printFileSize(dicomFile);

        BufferedImage[] bimages = new BufferedImage[max];

        for (int i = 0; i < max; i++) {

            try {
                bimages[i] = DicomAnalyzer.createBufferedImgdFromDICOMfile(dicomFile, i);
            } catch (Exception ex) {
                //clean catch outofbounds/null: if next step does not contain an
                //image then note the index and break loop
                max = i;
                break;
            }
        }

        bimages = Arrays.copyOf(bimages, max);
        BufferedImage[] bimagesA = DicomAnalyzer.get90slicesA(bimages);

        System.out.println("Maxpic:" + max);

        showSlicesAtJFrame(bimages, "Original slices");
        showSlicesAtJFrame(bimagesA, "Different (90 deg) projection slices from original");

    }

}
