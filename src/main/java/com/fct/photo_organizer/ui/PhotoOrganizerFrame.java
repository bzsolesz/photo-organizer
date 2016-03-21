package com.fct.photo_organizer.ui;

import javax.swing.*;

/**
 * Created by zsolt_balogh on 21/03/2016.
 */
public class PhotoOrganizerFrame extends JFrame {

    public PhotoOrganizerFrame() {
        setTitle("Photo Organizer");
        setContentPane(new PhotoViewerForm().getPhotoViewerFormPanel());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }
}
