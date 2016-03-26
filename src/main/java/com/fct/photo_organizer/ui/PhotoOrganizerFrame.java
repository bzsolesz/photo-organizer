package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;

import javax.swing.*;
import java.awt.*;

/**
 * Created by zsolt_balogh on 21/03/2016.
 */
public class PhotoOrganizerFrame extends JFrame {

    public PhotoOrganizerFrame(FileService fileService) {
        setTitle("Photo Organizer");
        setContentPane(new PhotoViewerForm(fileService).getPhotoViewerFormPanel());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 400));
    }
}
