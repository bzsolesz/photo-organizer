package com.fct.photo_organizer.service.photo_assignment;

import java.io.File;
import java.util.*;

public class PhotoAssignment {

    private File photo;
    private Map<String, File> assignees = new HashMap<>();

    public PhotoAssignment(File photo) {
        this.photo = photo;
    }

    public File getPhoto() {
        return photo;
    }

    public void addAssignee(String childName, File childDirectory) {

        assignees.put(childName, childDirectory);
    }

    public void removeAssignee(String childName) {

        assignees.remove(childName);
    }

    public Set<String> getAssigneeNames() {

        return assignees.keySet();
    }

    public File getAssigneeDirectory(String childName) {

        return assignees.get(childName);
    }
}
