package com.fct.photo_organizer.service.photo_targeting.impl;

import com.fct.photo_organizer.service.photo_targeting.PhotoTargetDirectories;
import com.fct.photo_organizer.service.photo_targeting.PhotoTargetingService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PhotoTargetingServiceImpl implements PhotoTargetingService {

    private Map<File, PhotoTargetDirectories> photoTargeting;

    @Override
    public void initPhotoTargeting(List<File> photos) {

        photoTargeting = new HashMap<>();

        photos.stream().forEach(photo -> photoTargeting.put(photo, new PhotoTargetDirectories(photo)));
    }

    @Override
    public void clearPhotoTargeting() {
        photoTargeting.clear();
    }

    @Override
    public Set<File> getPhotos() {
        return photoTargeting.keySet();
    }

    @Override
    public Set<File> getPhotoTargetDirectories(File photo) {
        return photoTargeting.get(photo).getTargetDirectories();
    }

    @Override
    public void addTargetDirectoryToPhoto(File photo, File targetDirectory) {

        photoTargeting.get(photo).addTargetDirectory(targetDirectory);
    }

    @Override
    public void removeTargetDirectoryFromPhoto(File photo, File targetDirectory) {

        photoTargeting.get(photo).removeTargetDirectory(targetDirectory);
    }

    @Override
    public void removeTargetDirectoryFromPhotoTargeting(File targetDirectory) {

        photoTargeting.keySet().forEach(photo -> photoTargeting.get(photo).removeTargetDirectory(targetDirectory));
    }
}
