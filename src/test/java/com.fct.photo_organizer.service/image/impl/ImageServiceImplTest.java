package com.fct.photo_organizer.service.image.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;

public class ImageServiceImplTest {

    private ImageServiceImpl testedService;

    @Mock
    private File imageFileMock;
    @Mock
    private Image imageMock;
    @Mock
    private ImageIcon imageIconMock;

    @Before
    public void setup() throws IOException {

        initMocks(this);

        testedService = spy(new ImageServiceImpl());

        doReturn(imageMock).when(testedService).readImage(imageFileMock);
        doReturn(imageIconMock).when(testedService).createImageIcon(imageMock);
    }

    @Test
    public void shouldLoadTheFileAsImageIcon() throws IOException {

        ImageIcon resultImageIcon = testedService.loadImageIcon(imageFileMock);

        assertEquals(imageIconMock, resultImageIcon);
    }
}
