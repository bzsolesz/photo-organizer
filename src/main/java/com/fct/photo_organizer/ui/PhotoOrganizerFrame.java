package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;

import javax.swing.*;
import java.awt.*;

public class PhotoOrganizerFrame {

    final static String TITLE = "Photo Organizer";
    final static int HEIGHT = 400;
    final static int WIDTH = 500;

    private JFrame internalFrame;
    private FileService fileService;

    public PhotoOrganizerFrame(FileService fileService) {
        this.fileService = fileService;
    }

    public void init() {
        internalFrame = createJFrame();
        internalFrame.setTitle(TITLE);
        internalFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        internalFrame.setMinimumSize(createDimension(WIDTH, HEIGHT));

        PhotoViewerForm photoViewerForm = createPhotoViewerForm(fileService);
        photoViewerForm.init();

        internalFrame.setContentPane(photoViewerForm.getPhotoViewerFormPanel());
    }

    public void setVisible() {
        internalFrame.setVisible(true);
    }

    JFrame createJFrame() {
        return new JFrame();
    }

    PhotoViewerForm createPhotoViewerForm(FileService fileService) {
        return new PhotoViewerForm(fileService);
    }

    Dimension createDimension(int width, int height) {
        return new Dimension(width, height);
    }

    FileService getFileService() {
        return fileService;
    }
}
