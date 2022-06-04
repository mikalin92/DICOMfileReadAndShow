/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.mika.dicom_exam;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferUShort;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;

/**
 * Class for analyzing and reading a DICOM file.
 * @author Mika
 */
public class DicomAnalyzer {
    
    /**
     * Reads single image from DICOM file as BufferedImage.
     * @param dicomFile
     * The original DICOM file.
     * @param imageindex
     * The index of specified single image in the DICOM file.
     * @return 
     * BufferedImage of the specified single image from the DICOM file.
     */

    public static BufferedImage createBufferedImgdFromDICOMfile(File dicomFile, int imageindex) {
        Raster raster = null;
        //System.out.println("Input: " + dicomFile.getName());

        //Open the DICOM file and get its pixel data
        try {
            Iterator iter = ImageIO.getImageReadersByFormatName("DICOM");
            ImageReader reader = (ImageReader) iter.next();
            DicomImageReadParam param = (DicomImageReadParam) reader.getDefaultReadParam();
            ImageInputStream iis = ImageIO.createImageInputStream(dicomFile);
            reader.setInput(iis, false);
            //Returns a new Raster (rectangular array of pixels) containing the raw pixel data from the image stream
            raster = reader.readRaster(imageindex, param);
            if (raster == null) {
                System.out.println("Error: couldn't read Dicom image!");
            }
            iis.close();
        } catch (Exception e) {
            System.out.println("Error: couldn't read dicom image! " + e.getMessage());
            e.printStackTrace();
        }
        return get16bitBuffImage(raster);
    }

    /**
     * Helper function to change the image type.
     * @param raster
     * Raster of the medical image
     * @return 
     * BufferedImage, 16 bit, of the medical image
     */
    
    public static BufferedImage get16bitBuffImage(Raster raster) {
        short[] pixels = ((DataBufferUShort) raster.getDataBuffer()).getData();
        ColorModel colorModel = new ComponentColorModel(
                ColorSpace.getInstance(ColorSpace.CS_GRAY),
                new int[]{16},
                false,
                false,
                Transparency.OPAQUE,
                DataBuffer.TYPE_USHORT);
        DataBufferUShort db = new DataBufferUShort(pixels, pixels.length);
        WritableRaster outRaster = Raster.createInterleavedRaster(
                db,
                raster.getWidth(),
                raster.getHeight(),
                raster.getWidth(),
                1,
                new int[1],
                null);
        return new BufferedImage(colorModel, outRaster, false, null);
    }

    /**
     * Gets sideways generated data set from the original medical array.
     * @param originals
     * Original data set of medical images.
     * @return 
     * Sideways images generated of the original data set.
     */
    
    
    public static BufferedImage[] get90slicesA(BufferedImage[] originals) {
        int width = originals[0].getWidth();
        int height = originals[0].getHeight();

        BufferedImage[] slices90 = new BufferedImage[width];
        for (int i = 0; i < width; i++) {
            slices90[i] = new BufferedImage(originals.length, height, originals[0].getType());

            for (int x = 0; x < originals.length; x++) {
                for (int y = 0; y < height; y++) {
                    slices90[i].setRGB(x, y, originals[x].getRGB(i, y));
                }
            }

        }
        return slices90;

    }
}
