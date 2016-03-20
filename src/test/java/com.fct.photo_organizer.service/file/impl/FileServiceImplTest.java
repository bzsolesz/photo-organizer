package com.fct.photo_organizer.service.file.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class FileServiceImplTest {

    private FileServiceImpl testedService;

    @Before
    public void setUp() {
        testedService = new FileServiceImpl();
    }

    @Test
    public void shouldReturnAnEmptyFileListInTargetDirectory() {

        assertEquals(Collections.emptyList(), testedService.getAllFilesIn());
    }
}