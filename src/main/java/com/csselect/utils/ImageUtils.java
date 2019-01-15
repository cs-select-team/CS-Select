package com.csselect.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class providing utility methods for interacting with images
 */
public final class ImageUtils {

    private ImageUtils() {
        //Util-Classes shouldn't be instantiated
    }

    /**
     * Reads a {@link BufferedImage} from a {@link File}
     * @param file file to read from
     * @return BufferedImage read from the file
     * @throws IOException IOException thrown on error while reading
     */
    public static BufferedImage readImage(File file) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
        reader.setInput(iis, true);
        ImageReadParam params = reader.getDefaultReadParam();
        BufferedImage image = reader.read(0, params);
        iis.close();
        reader.dispose();
        return image;
    }
}
