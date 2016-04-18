package com.fct.photo_organizer.service.photo_targeting;

import java.io.File;
import java.util.*;

public class PhotoTargetDirectories {

    private File photo;
    private Set<File> targetDirectories = new HashSet<>();

    public PhotoTargetDirectories(File photo) {
        this.photo = photo;
    }

    public File getPhoto() {
        return photo;
    }

    public void addTargetDirectory(File targetDirectory) {

        targetDirectories.add(targetDirectory);
    }

    public void removeTargetDirectory(File targetDirectory) {

        targetDirectories.remove(targetDirectory);
    }

    public Set<File> getTargetDirectories() {

        return targetDirectories;
    }
}
