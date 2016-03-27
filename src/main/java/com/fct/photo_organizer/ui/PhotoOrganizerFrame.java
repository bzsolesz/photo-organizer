package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;

import javax.swing.*;
import java.awt.*;

/**
 * Created by zsolt_balogh on 21/03/2016.
 */
public class PhotoOrganizerFrame {

    final static String TITLE = "Photo Organizer";
    final static int HEIGHT = 400;
    final static int WIDTH = 500;

    private JFrame jFrame;
    private FileService fileService;

    public PhotoOrganizerFrame(FileService fileService) {
        this.fileService = fileService;
    }

    public void init() {
        jFrame = createJFrame();
        jFrame.setTitle(TITLE);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(createDimension(WIDTH, HEIGHT));
        jFrame.setContentPane(createPhotoViewerForm(fileService).getPhotoViewerFormPanel());
    }

    public void setVisible() {
        jFrame.setVisible(true);
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
