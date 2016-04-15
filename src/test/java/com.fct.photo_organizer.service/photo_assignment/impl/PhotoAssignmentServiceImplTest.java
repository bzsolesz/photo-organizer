package com.fct.photo_organizer.service.photo_assignment.impl;

import com.fct.photo_organizer.service.photo_assignment.PhotoAssignmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhotoAssignmentServiceImplTest {

    private PhotoAssignmentService testedService;

    @Mock
    private File photoFileMock1;
    @Mock
    private File photoFileMock2;
    @Mock
    private File childDirectoryMock1;
    @Mock
    private File childDirectoryMock2;

    private List<File> photoFiles;

    private String childName1 = "child1";
    private String childName2 = "child2";

    @Before
    public void setup() {

        initMocks(this);

        testedService = new PhotoAssignmentServiceImpl();

        initPhotoFiles();
    }

    @Test
    public void shouldCreateAPhotoAssignmentForEachPhotoDuringInit() {

        testedService.initPhotoAssignments(photoFiles);

        assertEquals(photoFileMock1, testedService.getPhotoAssignment(photoFileMock1).getPhoto());
        assertEquals(photoFileMock2, testedService.getPhotoAssignment(photoFileMock2).getPhoto());
    }

    @Test
    public void shouldClearPhotoAssignments() {

        testedService.initPhotoAssignments(photoFiles);

        testedService.clearPhotoAssignments();

        assertNull(testedService.getPhotoAssignment(photoFileMock1));
        assertNull(testedService.getPhotoAssignment(photoFileMock2));
    }

    @Test
    public void shouldAssignPhoto() {

        testedService.initPhotoAssignments(photoFiles);

        testedService.assignPhoto(photoFileMock1, childName1, childDirectoryMock1);
        testedService.assignPhoto(photoFileMock1, childName2, childDirectoryMock2);

        assertTrue(testedService.getPhotoAssignment(photoFileMock1).
                getAssigneeNames().containsAll(Arrays.asList(childName1, childName2)));

        assertEquals(childDirectoryMock1, testedService.getPhotoAssignment(photoFileMock1).getAssigneeDirectory(childName1));
        assertEquals(childDirectoryMock2, testedService.getPhotoAssignment(photoFileMock1).getAssigneeDirectory(childName2));
    }

    private void initPhotoFiles() {

        photoFiles = new ArrayList<>();

        photoFiles.add(photoFileMock1);
        photoFiles.add(photoFileMock2);
    }
}
