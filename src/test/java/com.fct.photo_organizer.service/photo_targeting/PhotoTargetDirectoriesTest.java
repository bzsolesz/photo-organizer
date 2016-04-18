package com.fct.photo_organizer.service.photo_targeting;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhotoTargetDirectoriesTest {

    private PhotoTargetDirectories testedTargeting;

    @Mock
    private File photoFileMock;
    @Mock
    private File targetDirectoryMock1;
    @Mock
    private File targetDirectoryMock2;

    @Before
    public void setup() {

        initMocks(this);

        testedTargeting = new PhotoTargetDirectories(photoFileMock);
    }

    @Test
    public void shouldReturnItsPhotoFile() {

        assertEquals(photoFileMock, testedTargeting.getPhoto());
    }

    @Test
    public void shouldAddTargetDirectoriesAndReturnThem() throws Exception {

        testedTargeting.addTargetDirectory(targetDirectoryMock1);
        testedTargeting.addTargetDirectory(targetDirectoryMock2);

        assertEquals(2, testedTargeting.getTargetDirectories().size());
        assertTrue(testedTargeting.getTargetDirectories().containsAll(Arrays.asList(targetDirectoryMock1, targetDirectoryMock2)));
    }

    @Test
    public void shouldRemoveTargetDirectory() {

        testedTargeting.addTargetDirectory(targetDirectoryMock1);
        testedTargeting.addTargetDirectory(targetDirectoryMock2);

        testedTargeting.removeTargetDirectory(targetDirectoryMock1);

        assertFalse(testedTargeting.getTargetDirectories().contains(targetDirectoryMock1));
        assertTrue(testedTargeting.getTargetDirectories().contains(targetDirectoryMock2));
    }
}
