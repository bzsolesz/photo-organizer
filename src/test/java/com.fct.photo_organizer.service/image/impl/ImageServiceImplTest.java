package com.fct.photo_organizer.service.image.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ImageServiceImplTest {

    private ImageServiceImpl testedService;

    private static final int TEST_MAX_WIDTH = 400;
    private static final int TEST_MAX_HEIGHT = 300;

    @Mock
    private File imageFileMock;
    @Mock
    private BufferedImage imageMock;
    @Mock
    private ImageIcon imageIconMock;
    @Mock
    private Image scaledImageMock;

    @Before
    public void setup() throws IOException {

        initMocks(this);

        testedService = spy(new ImageServiceImpl());

        doReturn(imageMock).when(testedService).readImage(imageFileMock);
        doReturn(imageIconMock).when(testedService).createImageIcon(scaledImageMock);
    }

    @Test
    public void shouldScaleImageHeightDownIfItsRatioBigger() throws IOException {

        initImageMockWithDimensions(600, 600);

        when(imageMock.getScaledInstance(-1, TEST_MAX_HEIGHT, Image.SCALE_SMOOTH)).thenReturn(scaledImageMock);

        ImageIcon resultImageIcon = testedService.loadImageIcon(imageFileMock, TEST_MAX_WIDTH, TEST_MAX_HEIGHT);

        assertEquals(imageIconMock, resultImageIcon);
    }

    @Test
    public void shouldScaleImageWidthDownIfItsRatioBigger() throws IOException {

        initImageMockWithDimensions(800, 500);

        when(imageMock.getScaledInstance(TEST_MAX_WIDTH, -1, Image.SCALE_SMOOTH)).thenReturn(scaledImageMock);

        ImageIcon resultImageIcon = testedService.loadImageIcon(imageFileMock, TEST_MAX_WIDTH, TEST_MAX_HEIGHT);

        assertEquals(imageIconMock, resultImageIcon);
    }

    @Test
    public void shouldNotScaleSmallerImageUp() throws IOException {

        doReturn(imageIconMock).when(testedService).createImageIcon(imageMock);

        initImageMockWithDimensions(300, 200);

        ImageIcon resultImageIcon = testedService.loadImageIcon(imageFileMock, TEST_MAX_WIDTH, TEST_MAX_HEIGHT);

        verify(imageMock, times(0)).getScaledInstance(anyInt(), anyInt(), anyInt());
        assertEquals(imageIconMock, resultImageIcon);
    }

    private void initImageMockWithDimensions(int width, int height) {
        when(imageMock.getWidth()).thenReturn(width);
        when(imageMock.getHeight()).thenReturn(height);
    }
}
