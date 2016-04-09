package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.service.image.ImageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by zsolt_balogh on 27/03/2016.
 */
public class PhotoOrganizerFrameTest {

    private PhotoOrganizerFrame testedFrame;

    @Mock
    private FileService fileServiceMock;
    @Mock
    private ImageService imageServiceMock;
    @Mock
    private JFrame internalFrameMock;
    @Mock
    private PhotoViewerForm photoViewerFormMock;
    @Mock
    private JPanel photoViewerPanelMock;
    @Mock
    private Dimension dimensionMock;

    @Before
    public void setup() {

        initMocks(this);

        testedFrame = spy(new PhotoOrganizerFrame(fileServiceMock, imageServiceMock));

        doReturn(internalFrameMock).when(testedFrame).createJFrame();
        doReturn(dimensionMock).when(testedFrame).createDimension(PhotoOrganizerFrame.WIDTH, PhotoOrganizerFrame.HEIGHT);
        doReturn(photoViewerFormMock).when(testedFrame).createPhotoViewerForm(fileServiceMock, imageServiceMock);

        when(photoViewerFormMock.getPhotoViewerFormPanel()).thenReturn(photoViewerPanelMock);

        testedFrame.init();
    }

    @Test
    public void shouldHaveItsFileService() {
        assertEquals(fileServiceMock, testedFrame.getFileService());
    }

    @Test
    public void shouldHaveItsImageService() {
        assertEquals(imageServiceMock, testedFrame.getImageService());
    }

    @Test
    public void shouldTheTitleOfTheFrameSet() {
        verify(internalFrameMock).setTitle(PhotoOrganizerFrame.TITLE);
    }

    @Test
    public void shouldTheCloseOperationSetToCloseOnExit() {
        verify(internalFrameMock).setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Test
    public void shouldTheDimensionOfTheFrameSet() {
        verify(internalFrameMock).setMinimumSize(dimensionMock);
    }

    @Test
    public void shouldSetContentPanelToPhotoViewerFormPanel() {

        InOrder setContentPaneOrder = inOrder(photoViewerFormMock, internalFrameMock);

        setContentPaneOrder.verify(photoViewerFormMock).init();
        setContentPaneOrder.verify(internalFrameMock).setContentPane(photoViewerPanelMock);
    }

    @Test
    public void shouldSetItsInternalFrameVisible() {

        testedFrame.setVisible();

        verify(internalFrameMock).setVisible(true);
    }
}
