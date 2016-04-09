package com.fct.photo_organizer.service.image.impl;

import com.fct.photo_organizer.service.image.ImageService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageServiceImpl implements ImageService {

    @Override
    public ImageIcon loadImageIcon(File imageFile, int maxWidth, int maxHeight) throws IOException {

        BufferedImage image = readImage(imageFile);

        Image scaledImage = scaleImageIfRequired(image, maxWidth, maxHeight);

        return createImageIcon(scaledImage);
    }

    private Image scaleImageIfRequired(BufferedImage image, int maxWidth, int maxHeight) {

        double widthRatio = image.getWidth() / (double) maxWidth;
        double heightRatio = image.getHeight() / (double) maxHeight;

        if (widthRatio > 1 || heightRatio > 1) {

            if (widthRatio > heightRatio) {
                return image.getScaledInstance(maxWidth, -1, Image.SCALE_SMOOTH);
            } else {
                return image.getScaledInstance(-1, maxHeight, Image.SCALE_SMOOTH);
            }

        } else {
            return image;
        }
    }

    ImageIcon createImageIcon(Image image) {
        return new ImageIcon(image);
    }

    BufferedImage readImage(File imageFile) throws IOException {
        return ImageIO.read(imageFile);
    }
}
