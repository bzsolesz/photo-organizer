package com.fct.photo_organizer.main;

import com.fct.photo_organizer.ui.PhotoOrganizerFrame;

import javax.swing.*;

/**
 * Created by zsolt_balogh on 21/03/2016.
 */
public class PhotoOrganizerMain {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PhotoOrganizerFrame().setVisible(true);
            }
        });
    }
}
