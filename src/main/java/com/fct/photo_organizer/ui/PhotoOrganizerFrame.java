package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.service.image.ImageService;
import com.fct.photo_organizer.service.photo_targeting.PhotoTargetingService;

import javax.swing.*;
import java.awt.*;

public class PhotoOrganizerFrame {

    final static String TITLE = "Photo Organizer";
    final static int WIDTH = 1300;
    final static int HEIGHT = 700;

    private JFrame internalFrame;
    private FileService fileService;
    private ImageService imageService;
    private PhotoTargetingService photoTargetingService;

    public PhotoOrganizerFrame(FileService fileService, ImageService imageService,
                               PhotoTargetingService photoTargetingService) {

        this.fileService = fileService;
        this.imageService = imageService;
        this.photoTargetingService = photoTargetingService;
    }

    public void init() {
        internalFrame = createJFrame();
        internalFrame.setTitle(TITLE);
        internalFrame.setResizable(false);
        internalFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        internalFrame.setMinimumSize(createDimension(WIDTH, HEIGHT));

        PhotoViewerForm photoViewerForm = createPhotoViewerForm(fileService, imageService, photoTargetingService);
        photoViewerForm.init();

        internalFrame.setContentPane(photoViewerForm.getPhotoViewerFormPanel());
    }

    public void setVisible() {
        internalFrame.setVisible(true);
    }

    JFrame createJFrame() {
        return new JFrame();
    }

    PhotoViewerForm createPhotoViewerForm(FileService fileService, ImageService imageService,
                                          PhotoTargetingService photoTargetingService) {

        return new PhotoViewerForm(fileService, imageService, photoTargetingService);
    }

    Dimension createDimension(int width, int height) {
        return new Dimension(width, height);
    }

    FileService getFileService() {
        return fileService;
    }

    ImageService getImageService() {
        return imageService;
    }

    PhotoTargetingService getPhotoTargetingService() {
        return photoTargetingService;
    }
}
