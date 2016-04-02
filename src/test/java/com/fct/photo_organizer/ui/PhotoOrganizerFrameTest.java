package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;
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

        testedFrame = spy(new PhotoOrganizerFrame(fileServiceMock));

        doReturn(internalFrameMock).when(testedFrame).createJFrame();
        doReturn(dimensionMock).when(testedFrame).createDimension(PhotoOrganizerFrame.WIDTH, PhotoOrganizerFrame.HEIGHT);
        doReturn(photoViewerFormMock).when(testedFrame).createPhotoViewerForm(fileServiceMock);

        when(photoViewerFormMock.getPhotoViewerFormPanel()).thenReturn(photoViewerPanelMock);

        testedFrame.init();
    }

    @Test
    public void shouldHaveItsFileService() {
        assertEquals(fileServiceMock, testedFrame.getFileService());
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

        InOrder setContentPaneOrder = Mockito.inOrder(photoViewerFormMock, internalFrameMock);

        setContentPaneOrder.verify(photoViewerFormMock).init();
        setContentPaneOrder.verify(internalFrameMock).setContentPane(photoViewerPanelMock);
    }

    @Test
    public void shouldSetItsInternalFrameVisible() {

        testedFrame.setVisible();

        verify(internalFrameMock).setVisible(true);
    }
}
