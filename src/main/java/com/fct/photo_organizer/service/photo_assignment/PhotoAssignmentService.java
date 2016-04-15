package com.fct.photo_organizer.service.photo_assignment;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public interface PhotoAssignmentService {

    void initPhotoAssignments(List<File> photos);

    PhotoAssignment getPhotoAssignment(File photo);

    void assignPhoto(File photo, String childName, File childDirectory);

    void clearPhotoAssignments();
}
