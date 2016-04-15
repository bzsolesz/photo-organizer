package com.fct.photo_organizer.service.photo_assignment.impl;

import com.fct.photo_organizer.service.photo_assignment.PhotoAssignment;
import com.fct.photo_organizer.service.photo_assignment.PhotoAssignmentService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoAssignmentServiceImpl implements PhotoAssignmentService {

    private Map<File, PhotoAssignment> photoAssignments;

    @Override
    public void initPhotoAssignments(List<File> photos) {

        photoAssignments = new HashMap<>();

        photos.stream().forEach(photo -> photoAssignments.put(photo, new PhotoAssignment(photo)));
    }

    @Override
    public PhotoAssignment getPhotoAssignment(File photo) {
        return photoAssignments.get(photo);
    }

    @Override
    public void assignPhoto(File photo, String childName, File childDirectory) {

        photoAssignments.get(photo).addAssignee(childName, childDirectory);
    }

    @Override
    public void clearPhotoAssignments() {
        photoAssignments.clear();
    }
}
