package com.fct.photo_organizer.main;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.service.file.impl.FileServiceImpl;
import com.fct.photo_organizer.service.image.ImageService;
import com.fct.photo_organizer.service.image.impl.ImageServiceImpl;
import com.fct.photo_organizer.ui.PhotoOrganizerFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PhotoOrganizerMain {

    public static void main(String[] args) {

        FileService fileService = new FileServiceImpl();
        ImageService imageService = new ImageServiceImpl();

        PhotoOrganizerFrameRunnable photoOrganizerFrameRunnable = new PhotoOrganizerFrameRunnable(fileService, imageService);

        SwingUtilities.invokeLater(photoOrganizerFrameRunnable);
    }

    static class PhotoOrganizerFrameRunnable implements Runnable {

        private FileService fileService;
        private ImageService imageService;

        PhotoOrganizerFrameRunnable(FileService fileService, ImageService imageService) {
            this.fileService = fileService;
            this.imageService = imageService;
        }

        @Override
        public void run() {

            PhotoOrganizerFrame photoOrganizerFrame = createPhotoOrganizerFrame(fileService, imageService);
            photoOrganizerFrame.init();
            photoOrganizerFrame.setVisible();
        }

        PhotoOrganizerFrame createPhotoOrganizerFrame(FileService fileService, ImageService imageService) {
            return new PhotoOrganizerFrame(fileService, imageService);
        }
    }
}
