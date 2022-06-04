/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.mika.dicom_exam;

import java.io.File;

/**
 * Class for determining the size of a file.
 * @author Mika
 */
public class SizeHelper {
    
    /**
     * Calculates size of a file.
     * @param file 
     * File of which size is to be calculated.
     */

    public static void printFileSize(File file) {

        if (file.exists()) {

            // size of a file (in bytes)
            long bytes = file.length();

            long kilobytes = (bytes / 1024);
            long megabytes = (kilobytes / 1024);
            long gigabytes = (megabytes / 1024);
            long terabytes = (gigabytes / 1024);
            long petabytes = (terabytes / 1024);
            long exabytes = (petabytes / 1024);
            long zettabytes = (exabytes / 1024);
            long yottabytes = (zettabytes / 1024);

            System.out.println(String.format("%,d bytes", bytes));
            System.out.println(String.format("%,d kilobytes", kilobytes));
            System.out.println(String.format("%,d megabytes", megabytes));
            System.out.println(String.format("%,d gigabytes", gigabytes));
            System.out.println(String.format("%,d terabytes", terabytes));
            System.out.println(String.format("%,d petabytes", petabytes));
            System.out.println(String.format("%,d exabytes", exabytes));
            System.out.println(String.format("%,d zettabytes", zettabytes));
            System.out.println(String.format("%,d yottabytes", yottabytes));

        } else {
            System.out.println("File does not exist!");
        }

    }
}
