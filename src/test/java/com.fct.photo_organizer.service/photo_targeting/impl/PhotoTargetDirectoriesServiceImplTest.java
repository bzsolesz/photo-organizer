package com.fct.photo_organizer.service.photo_targeting.impl;

import com.fct.photo_organizer.service.photo_targeting.PhotoTargetingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhotoTargetDirectoriesServiceImplTest {

    private PhotoTargetingService testedService;

    @Mock
    private File photoFileMock1;
    @Mock
    private File photoFileMock2;
    @Mock
    private File targetDirectoryMock1;
    @Mock
    private File targetDirectoryMock2;

    private List<File> photoFiles;

    @Before
    public void setup() {

        initMocks(this);

        testedService = new PhotoTargetingServiceImpl();

        initPhotoFiles();
    }

    @Test
    public void shouldCreateAPhotoTargetingWithEachPhotoDuringInit() {

        testedService.initPhotoTargeting(photoFiles);

        assertEquals(Collections.emptySet(), testedService.getPhotoTargetDirectories(photoFileMock1));
        assertEquals(Collections.emptySet(), testedService.getPhotoTargetDirectories(photoFileMock2));
    }

    @Test
    public void shouldClearPhotoTargeting() {

        testedService.initPhotoTargeting(photoFiles);

        testedService.clearPhotoTargeting();

        assertEquals(Collections.emptySet(), testedService.getPhotos());
        assertEquals(Collections.emptySet(), testedService.getPhotos());
    }

    @Test
    public void shouldAddTargetDirectoryToPhoto() {

        testedService.initPhotoTargeting(photoFiles);

        testedService.addTargetDirectoryToPhoto(photoFileMock1, targetDirectoryMock1);
        testedService.addTargetDirectoryToPhoto(photoFileMock1, targetDirectoryMock2);

        assertTrue(testedService.getPhotoTargetDirectories(photoFileMock1).
                containsAll(Arrays.asList(targetDirectoryMock1, targetDirectoryMock2)));
    }

    @Test
    public void shouldRemoveTargetDirectoryFromPhoto() {

        testedService.initPhotoTargeting(photoFiles);

        testedService.addTargetDirectoryToPhoto(photoFileMock1, targetDirectoryMock1);
        testedService.addTargetDirectoryToPhoto(photoFileMock1, targetDirectoryMock2);

        testedService.removeTargetDirectoryFromPhoto(photoFileMock1, targetDirectoryMock1);

        assertFalse(testedService.getPhotoTargetDirectories(photoFileMock1).contains(targetDirectoryMock1));
        assertTrue(testedService.getPhotoTargetDirectories(photoFileMock1).contains(targetDirectoryMock2));
    }

    @Test
    public void shouldRemoveTargetDirectoryFromPhotoTargeting() {

        testedService.initPhotoTargeting(photoFiles);

        testedService.addTargetDirectoryToPhoto(photoFileMock1, targetDirectoryMock1);
        testedService.addTargetDirectoryToPhoto(photoFileMock1, targetDirectoryMock2);

        testedService.addTargetDirectoryToPhoto(photoFileMock2, targetDirectoryMock1);
        testedService.addTargetDirectoryToPhoto(photoFileMock2, targetDirectoryMock2);

        testedService.removeTargetDirectoryFromPhotoTargeting(targetDirectoryMock1);

        assertFalse(testedService.getPhotoTargetDirectories(photoFileMock1).contains(targetDirectoryMock1));
        assertFalse(testedService.getPhotoTargetDirectories(photoFileMock2).contains(targetDirectoryMock1));

        assertTrue(testedService.getPhotoTargetDirectories(photoFileMock1).contains(targetDirectoryMock2));
        assertTrue(testedService.getPhotoTargetDirectories(photoFileMock2).contains(targetDirectoryMock2));
    }

    private void initPhotoFiles() {

        photoFiles = new ArrayList<>();

        photoFiles.add(photoFileMock1);
        photoFiles.add(photoFileMock2);
    }
}
