package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.service.image.ImageService;
import com.fct.photo_organizer.ui.PhotoViewerForm.SourceImageListCellRenderer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
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
    private PhotoViewerForm.AddTargetDirectoryButtonActionListener testedAddTargetDirectoryButtonActionListener;

    @Mock
    private FileService fileServiceMock;
    @Mock
    private ImageService imageServiceMock;
    @Mock
    private File selectedDirectoryMock;
    @Mock
    private DirectoryFileChooser directoryFileChooserMock;
    @Mock
    private JPanel photoViewerPanelMock;
    @Mock
    private JList<File> sourceImageListMock;
    @Mock
    private JButton selectSourceDirectoryButtonMock;
    @Mock
    private JLabel showImageLabelMock;
    @Mock
    private JButton nextPhotoButtonMock;
    @Mock
    private JButton previousPhotoButtonMock;
    @Mock
    private ListSelectionEvent listSelectionEventMock;
    @Mock
    private ListModel<File> sourceImageListModelMock;
    @Mock
    private JPanel showImagePanelMock;
    @Mock
    private JButton addTargetDirectoryButtonMock;
    @Mock
    private JPanel photoTargetingInnerPanelMock;
    @Mock
    private BoxLayout boxLayoutMock;
    @Mock
    private JScrollPane assignToChildrenScrollPanelMock;
    @Mock
    private JViewport assignToChildrenScrollPanelViewportMock;

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
        testedAddTargetDirectoryButtonActionListener = spy(testedForm.new AddTargetDirectoryButtonActionListener());

        initImages();

        initTestedFormToCreateMocks();
        initTestedFormToHaveUIElementMocks();

        when(directoryFileChooserMock.getSelectedFile()).thenReturn(selectedDirectoryMock);
        when(directoryFileChooserMock.showOpenDialog(photoViewerPanelMock)).thenReturn(JFileChooser.APPROVE_OPTION);

        when(fileServiceMock.getImageFilesInDirectory(selectedDirectoryMock)).thenReturn(images);

        when(listSelectionEventMock.getSource()).thenReturn(sourceImageListMock);

        when(sourceImageListMock.getModel()).thenReturn(sourceImageListModelMock);

        when(showImagePanelMock.getWidth()).thenReturn(SHOW_IMAGE_PANEL_WIDTH);
        when(showImagePanelMock.getHeight()).thenReturn(SHOW_IMAGE_PANEL_HEIGHT);

        when(assignToChildrenScrollPanelViewportMock.getView()).thenReturn(photoTargetingInnerPanelMock);

        when(assignToChildrenScrollPanelMock.getViewport()).thenReturn(assignToChildrenScrollPanelViewportMock);
    }

    @Test
    public void shouldInitItsDirectoryFileChooser() {

        testedForm.init();

        assertEquals(directoryFileChooserMock, testedForm.directoryFileChooser);
        verify(directoryFileChooserMock).init();
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

        verify(previousPhotoButtonMock).addActionListener(testedPreviousImageButtonActionListener);
        verify(nextPhotoButtonMock).addActionListener(testedNextImageButtonActionListener);
    }

    @Test
    public void shouldInitItsAddChildButtonWithAppropriateActionListener() {

        testedForm.init();

        verify(addTargetDirectoryButtonMock).addActionListener(testedAddTargetDirectoryButtonActionListener);
    }

    @Test
    public void shouldInitItsAssignToChildrenPanelWithBoxLayout() {

        testedForm.init();

        verify(photoTargetingInnerPanelMock).setLayout(boxLayoutMock);
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

        when(directoryFileChooserMock.showOpenDialog(photoViewerPanelMock)).thenReturn(JFileChooser.CANCEL_OPTION);

        testedSourceDirectoryActionListener.actionPerformed(null);

        verify(sourceImageListMock, times(0)).setListData(images);
    }

    @Test
    public void shouldClearImageListForSelectedSourceDirectoryWithoutImage() {

        testedForm.init();

        when(fileServiceMock.getImageFilesInDirectory(selectedDirectoryMock)).thenReturn(new File[0]);

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

        verify(previousPhotoButtonMock).setEnabled(false);
    }

    @Test
    public void shouldEnablePreviousImageButtonIfNotFirstImageIsSelected() {

        when(sourceImageListModelMock.getSize()).thenReturn(3);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(1);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(previousPhotoButtonMock).setEnabled(true);
    }

    @Test
    public void shouldDisableNextImageButtonIfLastImageIsSelected() {

        int listSize = 3;

        when(sourceImageListModelMock.getSize()).thenReturn(listSize);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(listSize - 1);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(nextPhotoButtonMock).setEnabled(false);
    }

    @Test
    public void shouldEnableNextImageButtonIfNotLastImageIsSelected() {

        int listSize = 3;

        when(sourceImageListModelMock.getSize()).thenReturn(listSize);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(listSize - 2);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(nextPhotoButtonMock).setEnabled(true);
    }

    @Test
    public void shouldDisablePreviousAndNextImageButtonIfListIsEmpty() {

        when(sourceImageListModelMock.getSize()).thenReturn(0);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(-1);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(previousPhotoButtonMock).setEnabled(false);
        verify(nextPhotoButtonMock).setEnabled(false);
    }

    @Test
    public void shouldDisablePreviousAndNextImageButtonIfListHasOnlyOneImage() {

        when(sourceImageListModelMock.getSize()).thenReturn(1);

        when(sourceImageListMock.getSelectedIndex()).thenReturn(0);

        testedListSelectionListener.valueChanged(listSelectionEventMock);

        verify(previousPhotoButtonMock).setEnabled(false);
        verify(nextPhotoButtonMock).setEnabled(false);
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

    @Test
    public void shouldSelectANewTargetDirectoryAndAddAPanelForIt() throws BadLocationException {

        testedForm.init();

        String selectedDirectoryName = "selectedDirectory";

        when(selectedDirectoryMock.getName()).thenReturn(selectedDirectoryName);

        JPanel addTargetDirectoryToPhotoPanelMock = mock(JPanel.class);
        doReturn(addTargetDirectoryToPhotoPanelMock).when(testedAddTargetDirectoryButtonActionListener).createPanel();

        FlowLayout panelFlowLayoutMock = mock(FlowLayout.class);
        doReturn(panelFlowLayoutMock).when(testedAddTargetDirectoryButtonActionListener).createFlowLayout(FlowLayout.LEFT);

        Dimension panelDimensionMock = mock(Dimension.class);
        doReturn(panelDimensionMock).when(testedAddTargetDirectoryButtonActionListener).
                createDimension(PhotoViewerForm.ADD_TARGET_DIRECTORY_TO_PHOTO_PANEL_WIDTH, PhotoViewerForm.ADD_TARGET_DIRECTORY_TO_PHOTO_PANEL_HEIGHT);

        JCheckBox targetDirectoryCheckboxMock = mock(JCheckBox.class);
        doReturn(targetDirectoryCheckboxMock).when(testedAddTargetDirectoryButtonActionListener).createCheckbox();

        JLabel targetDirectoryNameLabelMock = mock(JLabel.class);
        doReturn(targetDirectoryNameLabelMock).when(testedAddTargetDirectoryButtonActionListener).createLabel(selectedDirectoryName);

        InOrder inTheProperOrder = inOrder(photoTargetingInnerPanelMock, addTargetDirectoryToPhotoPanelMock);

        testedAddTargetDirectoryButtonActionListener.actionPerformed(null);

        inTheProperOrder.verify(addTargetDirectoryToPhotoPanelMock).setLayout(panelFlowLayoutMock);
        inTheProperOrder.verify(addTargetDirectoryToPhotoPanelMock).setMaximumSize(panelDimensionMock);
        inTheProperOrder.verify(addTargetDirectoryToPhotoPanelMock).add(targetDirectoryCheckboxMock);
        inTheProperOrder.verify(addTargetDirectoryToPhotoPanelMock).add(targetDirectoryNameLabelMock);
        inTheProperOrder.verify(photoTargetingInnerPanelMock).add(addTargetDirectoryToPhotoPanelMock);
        inTheProperOrder.verify(photoTargetingInnerPanelMock).revalidate();
        inTheProperOrder.verify(photoTargetingInnerPanelMock).repaint();
    }

    private void initImages() {

        images = new File[2];

        images[0] = new File("test.png");
        images[1] = new File("test.jpg");
    }

    private void initTestedFormToCreateMocks() {

        doReturn(directoryFileChooserMock).when(testedForm).createDirectoryFileChooser();
        doReturn(testedCellRenderer).when(testedForm).createSourceImageListCellRenderer();
        doReturn(testedSourceDirectoryActionListener).when(testedForm).createSelectSourceDirectoryButtonActionListener();
        doReturn(testedListSelectionListener).when(testedForm).createSourceImageListSelectionListener();
        doReturn(testedNextImageButtonActionListener).when(testedForm).createNextImageButtonActionListener();
        doReturn(testedPreviousImageButtonActionListener).when(testedForm).createPreviousImageButtonActionListener();
        doReturn(testedAddTargetDirectoryButtonActionListener).when(testedForm).createAddChildButtonActionListener();
        doReturn(boxLayoutMock).when(testedForm).createBoxLayout(photoTargetingInnerPanelMock, BoxLayout.Y_AXIS);
    }

    private void initTestedFormToHaveUIElementMocks() {

        testedForm.photoViewerFormPanel = photoViewerPanelMock;
        testedForm.sourceImageList = sourceImageListMock;
        testedForm.selectSourceDirectoryButton = selectSourceDirectoryButtonMock;
        testedForm.showImageLabel = showImageLabelMock;
        testedForm.nextPhotoButton = nextPhotoButtonMock;
        testedForm.previousPhotoButton = previousPhotoButtonMock;
        testedForm.showImagePanel = showImagePanelMock;
        testedForm.addTargetDirectoryButton = addTargetDirectoryButtonMock;
        testedForm.photoTargetingInnerPanel = photoTargetingInnerPanelMock;
        testedForm.photoTargetingScrollPanel = assignToChildrenScrollPanelMock;
    }
}
