package com.fct.photo_organizer.service.photo_targeting;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface PhotoTargetingService {

    void initPhotoTargeting(List<File> photos);

    void clearPhotoTargeting();

    Set<File> getPhotos();

    Set<File> getPhotoTargetDirectories(File photo);

    void addTargetDirectoryToPhoto(File photo, File targetDirectory);

    void removeTargetDirectoryFromPhoto(File photo, File targetDirectory);

    void removeTargetDirectoryFromPhotoTargeting(File targetDirectory);
}
