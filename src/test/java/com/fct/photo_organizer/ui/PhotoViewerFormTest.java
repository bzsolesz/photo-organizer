package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.service.image.ImageService;
import com.fct.photo_organizer.ui.PhotoViewerForm.SourceImageListCellRenderer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhotoViewerFormTest {

    private static final Integer SHOW_IMAGE_PANEL_WIDTH = 400;
    private static final Integer SHOW_IMAGE_PANEL_HEIGHT = 300;

    private PhotoViewerForm testedForm;
    private PhotoViewerForm.SelectSourceDirectoryButtonActionListener testedSourceDirectoryActionListener;
    private SourceImageListCellRenderer testedCellRenderer;
    private PhotoViewerForm.SourceImageListSelectionListener testedListSelectionListener;
    private PhotoViewerForm.NextImageButtonActionListener testedNextImageButtonActionListener;
    private PhotoViewerForm.PreviousImageButtonActionListener testedPreviousImageButtonActionListener;

    @Mock
    private FileService fileServiceMock;
    @Mock
    private ImageService imageServiceMock;
    @Mock
    private File sourceDirectoryMock;
    @Mock
    private SourceDirectoryFileChooser sourceDirectoryFileChooserMock;
    @Mock
    private JPanel photoViewerPanelMock;
    @Mock
    private JList<File> sourceImageListMock;
    @Mock
    private JButton selectSourceDirectoryButtonMock;
    @Mock
    private JLabel showImageLabelMock;
    @Mock
    private JButton nextImageButtonMock;
    @Mock
    private JButton previousImageButtonMock;
    @Mock
    private ListSelectionEvent listSelectionEventMock;
    @Mock
    private ListModel<File> sourceImageListModelMock;
    @Mock
    private JPanel showImagePanelMock;

    private File[] images;

    @Before
    public void setup() {

        initMocks(this);

        testedForm = spy(new PhotoViewerForm(fileServiceMock, imageServiceMock));
        testedSourceDirectoryActionListener = testedForm.new SelectSourceDirectoryButtonActionListener();
        testedCellRenderer = spy(new SourceImageListCellRenderer());
        testedListSelectionListener = testedForm.new SourceImageListSelectionListener();
        testedPreviousImageButtonActionListener = testedForm.new PreviousImageButtonActionListener();
        testedNextImageButtonActionListener = testedForm.new NextImageButtonActionListener();

        initImages();

        initTestedFormToCreateMocks();
        initTestedFormToHaveUIElementMocks();

        when(sourceDirectoryFileChooserMock.getSelectedFile()).thenReturn(sourceDirectoryMock);
        when(sourceDirectoryFileChooserMock.showOpenDialog(photoViewerPanelMock)).thenReturn(JFileChooser.APPROVE_OPTION);

        when(fileServiceMock.getImageFilesInDirectory(sourceDirectoryMock)).thenReturn(images);

        when(listSelectionEventMock.getSource()).thenReturn(sourceImageListMock);

        when(sourceImageListMock.getModel()).thenReturn(sourceImageListModelMock);

        when(showImagePanelMock.getWidth()).thenReturn(SHOW_IMAGE_PANEL_WIDTH);
        when(showImagePanelMock.getHeight()).thenReturn(SHOW_IMAGE_PANEL_HEIGHT);
    }

    @Test
    public void shouldInitItsSourceDirectoryFileChooser() {

        testedForm.init();

        assertEquals(sourceDirectoryFileChooserMock, testedForm.sourceDirectoryFileChooser);
        verify(sourceDirectoryFileChooserMock).init();
    }

    @Test
    public void shouldInitItsSourceImageListWithAppropriateCellRendered() {

        testedForm.init();

        verify(sourceImageListMock).setCellRenderer(testedCellRenderer);
    }

    @Test
    public void shouldInitItsSourceImageListToSingleSelection() {

        testedForm.init();

        verify(sourceImageListMock).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Test
    public void shouldInitItsSourceImageListWithAppropriateSelectionListener() {

        testedForm.init();

        verify(sourceImageListMock).addListSelectionListener(testedListSelectionListener);
    }

    @Test
    public void shouldInitItsSelectSourceDirectoryButtonWithAppropriateActionListener() {

        testedForm.init();

        verify(selectSourceDirectoryButtonMock).addActionListener(testedSourceDirectoryActionListener);
    }

    @Test
    public void shouldInitItsImageNavigationButtonsWithListeners() {

        testedForm.init();

        verify(previousImageButtonMock).addActionListener(testedPreviousImageButtonActionListener);
        verify(nextImageButtonMock).addActionListener(testedNextImageButtonActionListener);
    }

    @Test
    public void shouldCreateItsNextAndPreviousImageButtons() {

        testedForm.createUIComponents();

        assertTrue(testedForm.previousImageButton instanceof BasicArrowButton);
        assertEquals(BasicArrowButton.NORTH, ((BasicArrowButton)testedForm.previousImageButton).getDirection());

        assertTrue(testedForm.nextImageButton instanceof BasicArrowButton);
        assertEquals(BasicArrowButton.SOUTH, ((BasicArrowButton)testedForm.nextImageButton).getDirection());
    }

    @Test
    public void shouldPopulateTheImageListWithTheImagesInTheChosenSourceDirectory() {

        testedForm.init();

        testedSourceDirectoryActionListener.actionPerformed(null);

        verify(sourceImageListMock).setListData(images);
    }

    @Test
    public void shouldLoadTheFirstImageOfTheSelectedSourceDirectory() {

        testedForm.init();

        testedSourceDirectoryActionListener.actionPerformed(null);

        verify(sourceImageListMock).setSelectedIndex(0);
    }

    @Test
    public void shouldNotChangeTheImageListOnCancelInFileChooser() {

        testedForm.init();

        when(sourceDirectoryFileChooserMock.showOpenDialog(photoViewerPanelMock)).thenReturn(JFileChooser.CANCEL_OPTION);

        testedSourceDirectoryActionListener.actionPerformed(null);

        verify(sourceImageListMock, times(0)).setListData(images);
    }

    @Test
    public void shouldClearImageListForSelectedSourceDirectoryWithoutImage() {

        testedForm.init();

        when(fileServiceMock.getImageFilesInDirectory(sourceDirectoryMock)).thenReturn(new File[0]);

        testedSourceDirectoryActionListener.actionPerformed(null);

        verify(sourceImageListMock, times(0)).setSelectedIndex(anyInt());
        verify(sourceImageListMock).clearSelection();
    }

    @Test
    public void shouldRenderImageCellsUsingOnlyTheNameOfTheFile() {

        String fileName = "test.png";

        File fileMock = mock(File.class);
        when(fileMock.getName()).thenReturn(fileName);

        JList listMock = mock(JList.class);
        int fileIndex = 5;
        boolean isSelected = true;
        boolean cellHasFocus = true;

        JLabel cellRendererLabelMock = mock(JLabel.class);
        doReturn(cellRendererLabelMock).when(testedCellRenderer).getSuperListCellRendererComponent(
                listMock, fileMock, fileIndex, isSelected, cellHasFocus);

        Component resultCellRendererLabel =
                testedCellRenderer.getListCellRendererComponent(listMock, fileMock, fileIndex, isSelected, cellHasFocus);

        verify(cellRendererLabelMock).setText(fileName);

        assertEquals(cellRendererLabelMock, resultCellRendererLabel);
    }

    @Test
    public void shouldDisplayTheSelectedImage() throws Exception {

        File selectedImageFileMock = mock(File.class);
        when(sourceImageListMock.getSelectedValue()).thenReturn(selectedImageFileMock);

        ImageIcon imageIconMock = mock(ImageIcon.class);
        when(imageServiceMock.loadImageIcon(selectedImageFileMock,
                SHOW_IMAGE_PANEL_WIDTH, SHOW_IMAGE_PANEL_HEIGHT)).thenReturn(imageIconMock);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(showImageLabelMock).setIcon(imageIconMock);
    }

    @Test
    public void shouldClearRemoveDisplayedImageIfSelectionWasRemoved() throws IOException {

        when(sourceImageListMock.getSelectedValue()).thenReturn(null);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(imageServiceMock, times(0)).loadImageIcon(any(), anyInt(), anyInt());
        verify(showImageLabelMock).setIcon(null);
    }

    @Test
    public void shouldDisablePreviousImageButtonIfFirstImageIsSelected() {

        when(sourceImageListModelMock.getSize()).thenReturn(3);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(0);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(previousImageButtonMock).setEnabled(false);
    }

    @Test
    public void shouldEnablePreviousImageButtonIfNotFirstImageIsSelected() {

        when(sourceImageListModelMock.getSize()).thenReturn(3);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(1);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(previousImageButtonMock).setEnabled(true);
    }

    @Test
    public void shouldDisableNextImageButtonIfLastImageIsSelected() {

        int listSize = 3;

        when(sourceImageListModelMock.getSize()).thenReturn(listSize);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(listSize - 1);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(nextImageButtonMock).setEnabled(false);
    }

    @Test
    public void shouldEnableNextImageButtonIfNotLastImageIsSelected() {

        int listSize = 3;

        when(sourceImageListModelMock.getSize()).thenReturn(listSize);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(listSize - 2);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(nextImageButtonMock).setEnabled(true);
    }

    @Test
    public void shouldDisablePreviousAndNextImageButtonIfListIsEmpty() {

        when(sourceImageListModelMock.getSize()).thenReturn(0);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(-1);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(previousImageButtonMock).setEnabled(false);
        verify(nextImageButtonMock).setEnabled(false);
    }

    @Test
    public void shouldDisablePreviousAndNextImageButtonIfListHasOnlyOneImage() {

        when(sourceImageListModelMock.getSize()).thenReturn(1);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(0);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(previousImageButtonMock).setEnabled(false);
        verify(nextImageButtonMock).setEnabled(false);
    }

    @Test
    public void shouldDisplayThePreviousImage() {

        int currentSelectedIndex = 5;

        when(sourceImageListMock.getSelectedIndex()).thenReturn(currentSelectedIndex);

        testedPreviousImageButtonActionListener.actionPerformed(null);

        verify(sourceImageListMock).setSelectedIndex(currentSelectedIndex - 1);
    }

    @Test
    public void shouldDisplayTheNextImage() {

        int currentSelectedIndex = 5;

        when(sourceImageListMock.getSelectedIndex()).thenReturn(currentSelectedIndex);

        testedNextImageButtonActionListener.actionPerformed(null);

        verify(sourceImageListMock).setSelectedIndex(currentSelectedIndex + 1);
    }

    private void initImages() {

        images = new File[2];

        images[0] = new File("test.png");
        images[1] = new File("test.jpg");
    }

    private void initTestedFormToCreateMocks() {

        doReturn(sourceDirectoryFileChooserMock).when(testedForm).createSourceDirectoryFileChooser();
        doReturn(testedCellRenderer).when(testedForm).createSourceImageListCellRenderer();
        doReturn(testedSourceDirectoryActionListener).when(testedForm).createSelectSourceDirectoryButtonActionListener();
        doReturn(testedListSelectionListener).when(testedForm).createSourceImageListSelectionListener();
        doReturn(testedNextImageButtonActionListener).when(testedForm).createNextImageButtonActionListener();
        doReturn(testedPreviousImageButtonActionListener).when(testedForm).createPreviousImageButtonActionListener();
    }

    private void initTestedFormToHaveUIElementMocks() {

        testedForm.photoViewerFormPanel = photoViewerPanelMock;
        testedForm.sourceImageList = sourceImageListMock;
        testedForm.selectSourceDirectoryButton = selectSourceDirectoryButtonMock;
        testedForm.showImageLabel = showImageLabelMock;
        testedForm.nextImageButton = nextImageButtonMock;
        testedForm.previousImageButton = previousImageButtonMock;
        testedForm.showImagePanel = showImagePanelMock;
    }
}
