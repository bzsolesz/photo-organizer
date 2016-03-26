package com.fct.photo_organizer.service.file.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class FileServiceImplTest {

    private FileServiceImpl testedService;
    private FileServiceImpl.ImageFileFilter testedFilter;

    @Mock
    private File targetDirectoryMock;
    @Mock
    private File subDirectoryMock;
    @Mock
    private File pngImageFileMock1;
    @Mock
    private File pngImageFileMock2;
    @Mock
    private File jpgImageFileMock1;
    @Mock
    private File jpgImageFileMock2;
    @Mock
    private File otherFileMock;

    @Before
    public void setUp() {

        initMocks(this);

        testedService = new FileServiceImpl();
        testedFilter = new FileServiceImpl.ImageFileFilter();

        initSubDirectoryMocks();
        initImageFileMocks();
        initOtherFileMocks();
    }


    @Test
    public void shouldReturnAnEmptyImageListIfTargetDirectoryIsEmpty() {

        when(targetDirectoryMock.listFiles(any(FileFilter.class))).thenReturn(new File[0]);

        File[] imageFilesInDirectory = testedService.getImageFilesInDirectory(targetDirectoryMock);

        assertNotNull(imageFilesInDirectory);
        assertEquals(0, imageFilesInDirectory.length);
    }

    @Test
    public void shouldUseItsFileFilterToFilterDirectoryContent() {

        File[] sourceFileArrays = new File[] {new File("test.txt"), new File("testSubDir")};

        when(targetDirectoryMock.listFiles(any(FileFilter.class))).thenReturn(sourceFileArrays);

        File[] imageFilesInDirectory = testedService.getImageFilesInDirectory(targetDirectoryMock);

        assertArrayEquals(sourceFileArrays, imageFilesInDirectory);
    }

    @Test
    public void shouldFilterOutSubDirectories() {

        assertFalse(testedFilter.accept(subDirectoryMock));
    }

    @Test
    public void shouldFilterOutOtherFiles() {

        assertFalse(testedFilter.accept(otherFileMock));
    }

    @Test
    public void shouldReturnJpgImageFiles() {

        assertTrue(testedFilter.accept(jpgImageFileMock1));
        assertTrue(testedFilter.accept(jpgImageFileMock2));
    }

    @Test
    public void shouldReturnPngImageFiles() {

        assertTrue(testedFilter.accept(pngImageFileMock1));
        assertTrue(testedFilter.accept(pngImageFileMock2));
    }

    private void initSubDirectoryMocks() {

        when(subDirectoryMock.isFile()).thenReturn(false);

    }

    private void initImageFileMocks() {

        when(jpgImageFileMock1.isFile()).thenReturn(true);
        when(jpgImageFileMock1.getName()).thenReturn("jpgImageFileMock1.jpg");

        when(jpgImageFileMock2.isFile()).thenReturn(true);
        when(jpgImageFileMock2.getName()).thenReturn("jpgImageFileMock2.JPG");

        when(pngImageFileMock1.isFile()).thenReturn(true);
        when(pngImageFileMock1.getName()).thenReturn("pngImageFileMock1.png");

        when(pngImageFileMock2.isFile()).thenReturn(true);
        when(pngImageFileMock2.getName()).thenReturn("pngImageFileMock2.PNG");
    }

    private void initOtherFileMocks() {

        when(otherFileMock.isFile()).thenReturn(true);
        when(otherFileMock.getName()).thenReturn("otherFileMock.pdf");
    }
}
