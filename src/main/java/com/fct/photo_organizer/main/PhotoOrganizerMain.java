package com.fct.photo_organizer.main;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.service.file.impl.FileServiceImpl;
import com.fct.photo_organizer.ui.PhotoOrganizerFrame;

import javax.swing.*;
import java.io.File;

/**
 * Created by zsolt_balogh on 21/03/2016.
 */
public class PhotoOrganizerMain {

    public static void main(String[] args) {

        FileService fileService = new FileServiceImpl();
        PhotoOrganizerFrameRunnable photoOrganizerFrameRunnable = new PhotoOrganizerFrameRunnable(fileService);

        SwingUtilities.invokeLater(photoOrganizerFrameRunnable);
    }

    static class PhotoOrganizerFrameRunnable implements Runnable {

        private FileService fileService;

        PhotoOrganizerFrameRunnable(FileService fileService) {
            this.fileService = fileService;
        }

        @Override
        public void run() {

            PhotoOrganizerFrame photoOrganizerFrame = createPhotoOrganizerFrame(fileService);
            photoOrganizerFrame.init();
            photoOrganizerFrame.setVisible();
        }

        PhotoOrganizerFrame createPhotoOrganizerFrame(FileService fileService) {
            return new PhotoOrganizerFrame(fileService);
        }
    }
}
