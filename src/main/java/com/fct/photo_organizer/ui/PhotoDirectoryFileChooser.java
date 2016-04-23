package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;

import javax.swing.*;
import java.io.File;

public class PhotoDirectoryFileChooser extends DirectoryFileChooser {

    private FileService fileService;

    public PhotoDirectoryFileChooser(FileService fileService) {
        this.fileService = fileService;
    }

    JFileChooser createJFileChooser() {
        return new InternalPhotoDirectoryFileChooser(fileService);
    }

    static class InternalPhotoDirectoryFileChooser extends JFileChooser {

        private FileService fileService;

        public InternalPhotoDirectoryFileChooser(FileService fileService) {
            this.fileService = fileService;
        }

        @Override
        public void approveSelection() {

            File selectedDirectory = getSelectedFile();

            if (fileService.getImageFilesInDirectory(selectedDirectory).length > 0) {

                callSuperApproveSelection();

            } else {

                showErrorMessageForNoPhotoDirectory();
            }
        }

        void callSuperApproveSelection() {
            super.approveSelection();
        }

        void showErrorMessageForNoPhotoDirectory() {
            JOptionPane.showMessageDialog(this, "Please select a directory with photos!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
