package com.fct.photo_organizer.service.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {

    File[] getImageFilesInDirectory(File targetDirectory);

    boolean doesPhotoExistInTargetDirectory(File photo, File targetDirectory);

    void copyPhotoToTargetDirectory(File photo, File targetDirectory) throws IOException;

    void copyPhotoToTargetDirectory(File photo, File targetDirectory, String newPhotoName) throws IOException;
}
