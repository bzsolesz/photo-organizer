package com.fct.photo_organizer.service.file.impl;

import com.fct.photo_organizer.service.file.FileService;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsolt_balogh on 20/03/2016.
 */
public class FileServiceImpl implements FileService {

    @Override
    public File[] getImageFilesInDirectory(File targetDirectory) {

        return targetDirectory.listFiles(new ImageFileFilter());
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
