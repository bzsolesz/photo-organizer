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
import javax.swing.text.Document;
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
    private PhotoViewerForm.AddChildTextFieldDocumentListener testedAddChildTextFieldDocumentListenerMock;
    private PhotoViewerForm.AddChildButtonActionListener testedAddChildButtonActionListener;

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
    @Mock
    private JTextField addChildTextFieldMock;
    @Mock
    private Document addChildTextFieldDocumentMock;
    @Mock
    private JButton addChildButtonMock;
    @Mock
    private JPanel assignToChildrenInnerPanelMock;
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
        testedAddChildTextFieldDocumentListenerMock = testedForm.new AddChildTextFieldDocumentListener();
        testedAddChildButtonActionListener = spy(testedForm.new AddChildButtonActionListener());

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

        when(addChildTextFieldMock.getDocument()).thenReturn(addChildTextFieldDocumentMock);

        when(assignToChildrenScrollPanelViewportMock.getView()).thenReturn(assignToChildrenInnerPanelMock);

        when(assignToChildrenScrollPanelMock.getViewport()).thenReturn(assignToChildrenScrollPanelViewportMock);
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
    public void shouldInitItsAddChildTextFieldWithAppropriateChangeListener() {

        when(addChildTextFieldMock.getDocument()).thenReturn(addChildTextFieldDocumentMock);

        testedForm.init();

        verify(addChildTextFieldMock.getDocument()).addDocumentListener(testedAddChildTextFieldDocumentListenerMock);
    }

    @Test
    public void shouldInitItsAddChildButtonWithAppropriateActionListener() {

        testedForm.init();

        verify(addChildButtonMock).addActionListener(testedAddChildButtonActionListener);
    }

    @Test
    public void shouldInitItsAssignToChildrenPanelWithBoxLayout() {

        testedForm.init();

        verify(assignToChildrenInnerPanelMock).setLayout(boxLayoutMock);
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

    @Test
    public void shouldDisableAddChildButtonIfNoNameIsSpecified() {

        when(addChildTextFieldDocumentMock.getLength()).thenReturn(0);

        testedAddChildTextFieldDocumentListenerMock.removeUpdate(null);

        verify(addChildButtonMock).setEnabled(false);
    }

    @Test
    public void shouldNotDisableAddChildButtonIfNameIsSpecified() {

        when(addChildTextFieldDocumentMock.getLength()).thenReturn(1);

        testedAddChildTextFieldDocumentListenerMock.removeUpdate(null);

        verify(addChildButtonMock, times(0)).setEnabled(false);
    }

    @Test
    public void shouldEnableAddChildButtonIfNameIsSpecified() {

        when(addChildTextFieldDocumentMock.getLength()).thenReturn(1);

        testedAddChildTextFieldDocumentListenerMock.insertUpdate(null);

        verify(addChildButtonMock).setEnabled(true);
    }

    @Test
    public void shouldAddAnAssignToChildPanel() throws BadLocationException {

        String childName = "testName";
        when(addChildTextFieldDocumentMock.getText(0, childName.length())).thenReturn(childName);
        when(addChildTextFieldDocumentMock.getLength()).thenReturn(childName.length());

        JPanel assignToChildPanelMock = mock(JPanel.class);
        doReturn(assignToChildPanelMock).when(testedAddChildButtonActionListener).createPanel();

        FlowLayout panelFlowLayoutMock = mock(FlowLayout.class);
        doReturn(panelFlowLayoutMock).when(testedAddChildButtonActionListener).createFlowLayout(FlowLayout.LEFT);

        Dimension panelDimensionMock = mock(Dimension.class);
        doReturn(panelDimensionMock).when(testedAddChildButtonActionListener).
                createDimension(PhotoViewerForm.ASSIGN_TO_CHILD_PANEL_WIDTH, PhotoViewerForm.ASSIGN_TO_CHILD_PANEL_HEIGHT);

        JCheckBox childCheckboxMock = mock(JCheckBox.class);
        doReturn(childCheckboxMock).when(testedAddChildButtonActionListener).createCheckbox();

        JLabel childNameLabelMock = mock(JLabel.class);
        doReturn(childNameLabelMock).when(testedAddChildButtonActionListener).createLabel(childName);

        InOrder inTheProperOrder = inOrder(assignToChildrenInnerPanelMock, assignToChildPanelMock);

        testedAddChildButtonActionListener.actionPerformed(null);

        inTheProperOrder.verify(assignToChildPanelMock).setLayout(panelFlowLayoutMock);
        inTheProperOrder.verify(assignToChildPanelMock).setMaximumSize(panelDimensionMock);
        inTheProperOrder.verify(assignToChildPanelMock).add(childCheckboxMock);
        inTheProperOrder.verify(assignToChildPanelMock).add(childNameLabelMock);
        inTheProperOrder.verify(assignToChildrenInnerPanelMock).add(assignToChildPanelMock);
        inTheProperOrder.verify(assignToChildrenInnerPanelMock).revalidate();
        inTheProperOrder.verify(assignToChildrenInnerPanelMock).repaint();
    }

    @Test
    public void shouldClearAndRefocusTheAddChildTextFieldAfterAddingAssignToChildPanel() throws BadLocationException {

        int childNameLenght = 8;

        when(addChildTextFieldDocumentMock.getLength()).thenReturn(8);

        testedAddChildButtonActionListener.actionPerformed(null);

        verify(addChildTextFieldDocumentMock).remove(0, childNameLenght);
        verify(addChildTextFieldMock).requestFocus();
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
        doReturn(testedAddChildTextFieldDocumentListenerMock).when(testedForm).createAddChildTextFieldDocumentListener();
        doReturn(testedAddChildButtonActionListener).when(testedForm).createAddChildButtonActionListener();
        doReturn(boxLayoutMock).when(testedForm).createBoxLayout(assignToChildrenInnerPanelMock, BoxLayout.Y_AXIS);
    }

    private void initTestedFormToHaveUIElementMocks() {

        testedForm.photoViewerFormPanel = photoViewerPanelMock;
        testedForm.sourceImageList = sourceImageListMock;
        testedForm.selectSourceDirectoryButton = selectSourceDirectoryButtonMock;
        testedForm.showImageLabel = showImageLabelMock;
        testedForm.nextPhotoButton = nextImageButtonMock;
        testedForm.previousPhotoButton = previousImageButtonMock;
        testedForm.showImagePanel = showImagePanelMock;
        testedForm.addChildTextField = addChildTextFieldMock;
        testedForm.addTargetDirectoryButton = addChildButtonMock;
        testedForm.assignToChildrenInnerPanel = assignToChildrenInnerPanelMock;
        testedForm.assignToChildrenScrollPanel = assignToChildrenScrollPanelMock;
    }
}
