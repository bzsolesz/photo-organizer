package com.fct.photo_organizer.service.file.impl;

import com.fct.photo_organizer.service.file.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class FileServiceImpl implements FileService {

    @Override
    public File[] getImageFilesInDirectory(File targetDirectory) {

        return targetDirectory.listFiles(new ImageFileFilter());
    }

    @Override
    public boolean doesPhotoExistInTargetDirectory(File photo, File targetDirectory) {

        File targetFile = createFile(targetDirectory, photo.getName());

        return targetFile.exists();
    }

    @Override
    public void copyPhotoToTargetDirectory(File photo, File targetDirectory) throws IOException {

        FileUtils.copyFileToDirectory(photo, targetDirectory);
    }

    @Override
    public void copyPhotoToTargetDirectory(File photo, File targetDirectory, String newPhotoName) throws IOException {

        File renamedPhotoFile = new File(targetDirectory, newPhotoName);

        copyFileWithNewName(photo, renamedPhotoFile);
    }

    void copyFileWithNewName(File file, File newFile) throws IOException {

        FileUtils.copyFile(file, newFile);
    }

    File createFile(File targetDirectory, String photoFileName) {
        return new File(targetDirectory, photoFileName);
    }

    static class ImageFileFilter implements FileFilter {

        private static final String PNG_FILE_EXTENSION = "PNG";
        private static final String JPG_FILE_EXTENSION = "JPG";

        @Override
        public boolean accept(File file) {

            String extension = FilenameUtils.getExtension(file.getName());

            return file.isFile()
                    && (extension.toUpperCase().equals(JPG_FILE_EXTENSION)
                        || extension.toUpperCase().equals(PNG_FILE_EXTENSION));
        }
    }
}
