package com.fct.photo_organizer.service.image.impl;

import com.fct.photo_organizer.service.image.ImageService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageServiceImpl implements ImageService {

    @Override
    public ImageIcon loadImageIcon(File imageFile) throws IOException {

        Image image = readImage(imageFile);

        return createImageIcon(image);
    }

    ImageIcon createImageIcon(Image image) {
        return new ImageIcon(image);
    }

    Image readImage(File imageFile) throws IOException {
        return ImageIO.read(imageFile);
    }
}
