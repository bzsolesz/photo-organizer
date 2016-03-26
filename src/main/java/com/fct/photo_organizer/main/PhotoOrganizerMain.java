package com.fct.photo_organizer.main;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.service.file.impl.FileServiceImpl;
import com.fct.photo_organizer.ui.PhotoOrganizerFrame;

import javax.swing.*;

/**
 * Created by zsolt_balogh on 21/03/2016.
 */
public class PhotoOrganizerMain {

    public static void main(String[] args) {

        FileService fileService = new FileServiceImpl();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PhotoOrganizerFrame(fileService).setVisible(true);
            }
        });
    }
}
