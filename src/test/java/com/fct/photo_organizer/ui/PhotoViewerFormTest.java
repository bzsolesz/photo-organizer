package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.service.image.ImageService;
import com.fct.photo_organizer.service.photo_targeting.PhotoTargetingService;
import com.fct.photo_organizer.ui.PhotoViewerForm.SourceImageListCellRenderer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.fct.photo_organizer.ui.PhotoViewerForm.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhotoViewerFormTest {

    private static final Integer SHOW_IMAGE_PANEL_WIDTH = 400;
    private static final Integer SHOW_IMAGE_PANEL_HEIGHT = 300;

    private PhotoViewerForm testedForm;
    private PhotoViewerForm.SelectSourceDirectoryButtonActionListener testedSourceDirectoryActionListener;
    private SourceImageListCellRenderer testedCellRenderer;
    private PhotoViewerForm.SourcePhotosListSelectionListener testedListSelectionListener;
    private PhotoViewerForm.NextImageButtonActionListener testedNextImageButtonActionListener;
    private PhotoViewerForm.PreviousImageButtonActionListener testedPreviousImageButtonActionListener;
    private PhotoViewerForm.AddTargetDirectoryButtonActionListener testedAddTargetDirectoryButtonActionListener;
    private PhotoViewerForm.TargetDirectoryCheckBoxItemListener testedTargetDirectoryCheckboxItemListenerMock;
    private PhotoViewerForm.CopyPhotosButtonActionListener testedCopyPhotosButtonActionListener;

    @Mock
    private FileService fileServiceMock;
    @Mock
    private PhotoTargetingService photoTargetingServiceMock;
    @Mock
    private ImageService imageServiceMock;
    @Mock
    private File selectedSourceDirectoryMock;
    @Mock
    private File selectedTargetDirectoryMock;
    @Mock
    private PhotoDirectoryFileChooser sourceDirectoryFileChooserMock;
    @Mock
    private DirectoryFileChooser targetDirectoryFileChooserMock;
    @Mock
    private JPanel photoViewerPanelMock;
    @Mock
    private JList<File> sourcePhotoListMock;
    @Mock
    private JButton selectSourceDirectoryButtonMock;
    @Mock
    private JLabel showImageLabelMock;
    @Mock
    private JButton nextPhotoButtonMock;
    @Mock
    private JButton previousPhotoButtonMock;
    @Mock
    private ListSelectionEvent sourcePhotoListSelectionEventMock;
    @Mock
    private ListModel<File> sourcePhotoListModelMock;
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
    @Mock
    private JCheckBox targetDirectoryCheckboxMock1;
    @Mock
    private JCheckBox targetDirectoryCheckboxMock2;
    @Mock
    private JCheckBox targetDirectoryCheckboxMock3;
    @Mock
    private ItemEvent checkBoxChangedMock;
    @Mock
    private File selectedPhotoFileMock;
    @Mock
    private File selectedPhotoFileMock2;
    @Mock
    private JButton copyPhotosButtonMock;

    private File[] images;
    private Set<File> selectedPhotoTargetDirectories;
    private Set<File> selectedPhotoTargetDirectories2;
    private HashMap<File, JCheckBox> targetDirectoryCheckboxes;
    private File targetDirectory1;
    private File targetDirectory2;
    private File targetDirectory3;

    @Before
    public void setup() {

        initMocks(this);

        testedForm = spy(new PhotoViewerForm(fileServiceMock, imageServiceMock, photoTargetingServiceMock));
        testedSourceDirectoryActionListener = testedForm.new SelectSourceDirectoryButtonActionListener();
        testedCellRenderer = spy(new SourceImageListCellRenderer());
        testedListSelectionListener = testedForm.new SourcePhotosListSelectionListener();
        testedPreviousImageButtonActionListener = testedForm.new PreviousImageButtonActionListener();
        testedNextImageButtonActionListener = testedForm.new NextImageButtonActionListener();
        testedAddTargetDirectoryButtonActionListener = spy(testedForm.new AddTargetDirectoryButtonActionListener());
        testedTargetDirectoryCheckboxItemListenerMock = testedForm.new TargetDirectoryCheckBoxItemListener();
        testedCopyPhotosButtonActionListener = testedForm.new CopyPhotosButtonActionListener();

        initImages();
        initSelectedPhotoTargetDirectoriesAndCheckboxes();

        initTestedFormToCreateMocks();
        initTestedFormToHaveUIElementMocks();

        when(sourceDirectoryFileChooserMock.getSelectedFile()).thenReturn(selectedSourceDirectoryMock);
        when(sourceDirectoryFileChooserMock.showOpenDialog(photoViewerPanelMock)).thenReturn(JFileChooser.APPROVE_OPTION);

        when(targetDirectoryFileChooserMock.getSelectedFile()).thenReturn(selectedTargetDirectoryMock);
        when(targetDirectoryFileChooserMock.showOpenDialog(photoViewerPanelMock)).thenReturn(JFileChooser.APPROVE_OPTION);

        when(fileServiceMock.getImageFilesInDirectory(selectedSourceDirectoryMock)).thenReturn(images);

        when(sourcePhotoListSelectionEventMock.getSource()).thenReturn(sourcePhotoListMock);

        when(sourcePhotoListMock.getModel()).thenReturn(sourcePhotoListModelMock);
        when(sourcePhotoListMock.getSelectedValue()).thenReturn(selectedPhotoFileMock);

        when(showImagePanelMock.getWidth()).thenReturn(SHOW_IMAGE_PANEL_WIDTH);
        when(showImagePanelMock.getHeight()).thenReturn(SHOW_IMAGE_PANEL_HEIGHT);

        when(assignToChildrenScrollPanelViewportMock.getView()).thenReturn(photoTargetingInnerPanelMock);

        when(assignToChildrenScrollPanelMock.getViewport()).thenReturn(assignToChildrenScrollPanelViewportMock);
    }

    @Test
    public void shouldInitItsSourceDirectoryFileChooser() {

        testedForm.init();

        assertEquals(sourceDirectoryFileChooserMock, testedForm.sourceDirectoryFileChooser);
        verify(sourceDirectoryFileChooserMock).init();
    }

    @Test
    public void shouldInitItsTargetDirectoryFileChooser() {

        testedForm.init();

        assertEquals(targetDirectoryFileChooserMock, testedForm.targetDirectoryFileChooser);
        verify(targetDirectoryFileChooserMock).init();
    }

    @Test
    public void shouldInitItsSourceImageListWithAppropriateCellRendered() {

        testedForm.init();

        verify(sourcePhotoListMock).setCellRenderer(testedCellRenderer);
    }

    @Test
    public void shouldInitItsSourceImageListToSingleSelection() {

        testedForm.init();

        verify(sourcePhotoListMock).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Test
    public void shouldInitItsSourceImageListWithAppropriateSelectionListener() {

        testedForm.init();

        verify(sourcePhotoListMock).addListSelectionListener(testedListSelectionListener);
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
        verify(addTargetDirectoryButtonMock).setEnabled(false);
    }

    @Test
    public void shouldInitItsAssignToChildrenPanelWithBoxLayout() {

        testedForm.init();

        verify(photoTargetingInnerPanelMock).setLayout(boxLayoutMock);
    }

    @Test
    public void shouldInitItsCopyPhotosButtonWithListener() {

        testedForm.init();

        verify(copyPhotosButtonMock).addActionListener(testedCopyPhotosButtonActionListener);
    }

    @Test
    public void shouldPopulateTheImageListWithTheImagesInTheChosenSourceDirectory() {

        testedForm.init();

        testedSourceDirectoryActionListener.actionPerformed(null);

        verify(sourcePhotoListMock).setListData(images);
    }

    @Test
    public void shouldLoadTheFirstImageOfTheSelectedSourceDirectory() {

        testedForm.init();

        testedSourceDirectoryActionListener.actionPerformed(null);

        verify(sourcePhotoListMock).setSelectedIndex(0);
    }

    @Test
    public void shouldInitPhotoTargetingWithPhotosFromSelectedSourceDirectory() {

        testedForm.init();

        testedSourceDirectoryActionListener.actionPerformed(null);

        verify(photoTargetingServiceMock).initPhotoTargeting(Arrays.asList(images));
        verify(targetDirectoryCheckboxMock1).setSelected(false);
        verify(targetDirectoryCheckboxMock2).setSelected(false);
        verify(targetDirectoryCheckboxMock3).setSelected(false);
    }

    @Test
    public void shouldEnableAddTargetDirectoryButtonAfterSourceDirectoryIsSelected() {

        testedForm.init();

        testedSourceDirectoryActionListener.actionPerformed(null);

        verify(addTargetDirectoryButtonMock).setEnabled(true);
    }

    @Test
    public void shouldNotChangeTheImageListOnCancelInFileChooser() {

        testedForm.init();

        when(sourceDirectoryFileChooserMock.showOpenDialog(photoViewerPanelMock)).thenReturn(JFileChooser.CANCEL_OPTION);

        testedSourceDirectoryActionListener.actionPerformed(null);

        verify(sourcePhotoListMock, times(0)).setListData(images);
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

        ImageIcon imageIconMock = mock(ImageIcon.class);
        when(imageServiceMock.loadImageIcon(selectedPhotoFileMock,
                SHOW_IMAGE_PANEL_WIDTH, SHOW_IMAGE_PANEL_HEIGHT)).thenReturn(imageIconMock);

        testedListSelectionListener.valueChanged(sourcePhotoListSelectionEventMock);

        verify(showImageLabelMock).setIcon(imageIconMock);
    }

    @Test
    public void shouldDisablePreviousImageButtonIfFirstImageIsSelected() {

        when(sourcePhotoListModelMock.getSize()).thenReturn(3);

        when(sourcePhotoListMock.getSelectedIndex()).thenReturn(0);

        testedListSelectionListener.valueChanged(sourcePhotoListSelectionEventMock);

        verify(previousPhotoButtonMock).setEnabled(false);
    }

    @Test
    public void shouldEnablePreviousImageButtonIfNotFirstImageIsSelected() {

        when(sourcePhotoListModelMock.getSize()).thenReturn(3);

        when(sourcePhotoListMock.getSelectedIndex()).thenReturn(1);

        testedListSelectionListener.valueChanged(sourcePhotoListSelectionEventMock);

        verify(previousPhotoButtonMock).setEnabled(true);
    }

    @Test
    public void shouldDisableNextImageButtonIfLastImageIsSelected() {

        int listSize = 3;

        when(sourcePhotoListModelMock.getSize()).thenReturn(listSize);

        when(sourcePhotoListMock.getSelectedIndex()).thenReturn(listSize - 1);

        testedListSelectionListener.valueChanged(sourcePhotoListSelectionEventMock);

        verify(nextPhotoButtonMock).setEnabled(false);
    }

    @Test
    public void shouldEnableNextImageButtonIfNotLastImageIsSelected() {

        int listSize = 3;

        when(sourcePhotoListModelMock.getSize()).thenReturn(listSize);

        when(sourcePhotoListMock.getSelectedIndex()).thenReturn(listSize - 2);

        testedListSelectionListener.valueChanged(sourcePhotoListSelectionEventMock);

        verify(nextPhotoButtonMock).setEnabled(true);
    }

    @Test
    public void shouldDisablePreviousAndNextImageButtonIfListHasOnlyOneImage() {

        when(sourcePhotoListModelMock.getSize()).thenReturn(1);

        when(sourcePhotoListMock.getSelectedIndex()).thenReturn(0);

        testedListSelectionListener.valueChanged(sourcePhotoListSelectionEventMock);

        verify(previousPhotoButtonMock).setEnabled(false);
        verify(nextPhotoButtonMock).setEnabled(false);
    }

    @Test
    public void shouldReloadTheTargetingOfTheSelectedPhoto() {

        when(photoTargetingServiceMock.getPhotoTargetDirectories(selectedPhotoFileMock)).thenReturn(selectedPhotoTargetDirectories);

        testedListSelectionListener.valueChanged(sourcePhotoListSelectionEventMock);

        verify(targetDirectoryCheckboxMock1).setSelected(true);
        verify(targetDirectoryCheckboxMock2).setSelected(false);
        verify(targetDirectoryCheckboxMock3).setSelected(true);
    }

    @Test
    public void shouldNotActionSourcePhotoSelectionIfSelectedPhotoIsNull() throws Exception {

        when(sourcePhotoListMock.getSelectedValue()).thenReturn(null);

        testedListSelectionListener.valueChanged(sourcePhotoListSelectionEventMock);

        verify(imageServiceMock, times(0)).loadImageIcon(any(File.class), anyInt(), anyInt());
        verify(showImageLabelMock, times(0)).setIcon(any(ImageIcon.class));
        verify(photoTargetingServiceMock, times(0)).getPhotoTargetDirectories(any(File.class));
        verify(targetDirectoryCheckboxMock1, times(0)).setSelected(anyBoolean());
        verify(targetDirectoryCheckboxMock2, times(0)).setSelected(anyBoolean());
        verify(targetDirectoryCheckboxMock3, times(0)).setSelected(anyBoolean());
        verify(nextPhotoButtonMock, times(0)).setEnabled(anyBoolean());
        verify(previousPhotoButtonMock, times(0)).setEnabled(anyBoolean());
    }

    @Test
    public void shouldDisplayThePreviousImage() {

        int currentSelectedIndex = 5;

        when(sourcePhotoListMock.getSelectedIndex()).thenReturn(currentSelectedIndex);

        testedPreviousImageButtonActionListener.actionPerformed(null);

        verify(sourcePhotoListMock).setSelectedIndex(currentSelectedIndex - 1);
    }

    @Test
    public void shouldDisplayTheNextImage() {

        int currentSelectedIndex = 5;

        when(sourcePhotoListMock.getSelectedIndex()).thenReturn(currentSelectedIndex);

        testedNextImageButtonActionListener.actionPerformed(null);

        verify(sourcePhotoListMock).setSelectedIndex(currentSelectedIndex + 1);
    }

    @Test
    public void shouldSelectANewTargetDirectoryAndAddAPanelForIt() throws BadLocationException {

        testedForm.init();

        testedForm.targetDirectoryCheckBoxes = new HashMap<>();

        String selectedDirectoryName = "selectedDirectory";
        String selectedDirectoryAbsolutePath = "/parent1/parent2/" + selectedDirectoryName;
        when(selectedTargetDirectoryMock.getName()).thenReturn(selectedDirectoryName);
        when(selectedTargetDirectoryMock.getAbsolutePath()).thenReturn(selectedDirectoryAbsolutePath);

        JPanel addTargetDirectoryToPhotoPanelMock = mock(JPanel.class);
        doReturn(addTargetDirectoryToPhotoPanelMock).when(testedAddTargetDirectoryButtonActionListener).createPanel();

        FlowLayout panelFlowLayoutMock = mock(FlowLayout.class);
        doReturn(panelFlowLayoutMock).when(testedAddTargetDirectoryButtonActionListener).createFlowLayout(FlowLayout.LEFT);

        Dimension panelDimensionMock = mock(Dimension.class);
        doReturn(panelDimensionMock).when(testedAddTargetDirectoryButtonActionListener).
                createDimension(ADD_TARGET_DIRECTORY_TO_PHOTO_PANEL_WIDTH, ADD_TARGET_DIRECTORY_TO_PHOTO_PANEL_HEIGHT);

        doReturn(targetDirectoryCheckboxMock1).when(testedAddTargetDirectoryButtonActionListener).createCheckbox(selectedDirectoryName);

        InOrder inTheProperOrder = inOrder(photoTargetingInnerPanelMock, addTargetDirectoryToPhotoPanelMock, targetDirectoryCheckboxMock1);

        testedAddTargetDirectoryButtonActionListener.actionPerformed(null);

        inTheProperOrder.verify(addTargetDirectoryToPhotoPanelMock).setLayout(panelFlowLayoutMock);
        inTheProperOrder.verify(addTargetDirectoryToPhotoPanelMock).setMaximumSize(panelDimensionMock);
        inTheProperOrder.verify(targetDirectoryCheckboxMock1).setName(selectedDirectoryAbsolutePath);
        inTheProperOrder.verify(targetDirectoryCheckboxMock1).addItemListener(testedTargetDirectoryCheckboxItemListenerMock);
        inTheProperOrder.verify(addTargetDirectoryToPhotoPanelMock).add(targetDirectoryCheckboxMock1);
        inTheProperOrder.verify(photoTargetingInnerPanelMock).add(addTargetDirectoryToPhotoPanelMock);
        inTheProperOrder.verify(photoTargetingInnerPanelMock).revalidate();
        inTheProperOrder.verify(photoTargetingInnerPanelMock).repaint();

        assertEquals(1, testedForm.targetDirectoryCheckBoxes.size());
        assertEquals(targetDirectoryCheckboxMock1, testedForm.targetDirectoryCheckBoxes.get(new File(selectedDirectoryAbsolutePath)));
    }

    @Test
    public void shouldNotAddTargetDirectoryPanelIfDirectoryChooserWasCancelled() {

        testedForm.init();

        String selectedDirectoryName = "selectedDirectory";
        String selectedDirectoryAbsolutePath = "/parent1/parent2/" + selectedDirectoryName;
        when(selectedTargetDirectoryMock.getName()).thenReturn(selectedDirectoryName);
        when(selectedTargetDirectoryMock.getAbsolutePath()).thenReturn(selectedDirectoryAbsolutePath);

        when(targetDirectoryFileChooserMock.showOpenDialog(photoViewerPanelMock)).thenReturn(JFileChooser.CANCEL_OPTION);

        testedAddTargetDirectoryButtonActionListener.actionPerformed(null);

        verify(targetDirectoryFileChooserMock, times(0)).getSelectedFile();
    }

    @Test
    public void shouldAddTargetDirectoryToPhotoOnSelection() {

        String targetDirectoryAbsolutePath = "/parent1/parent2/selectedDirectory";

        when(checkBoxChangedMock.getSource()).thenReturn(targetDirectoryCheckboxMock1);

        when(targetDirectoryCheckboxMock1.isSelected()).thenReturn(true);
        when(targetDirectoryCheckboxMock1.getName()).thenReturn(targetDirectoryAbsolutePath);

        Integer photoIndex = 5;
        when(sourcePhotoListMock.getSelectedIndex()).thenReturn(photoIndex);

        File photoFile = new File("photo5");
        when(sourcePhotoListModelMock.getElementAt(photoIndex)).thenReturn(photoFile);
        when(sourcePhotoListModelMock.getSize()).thenReturn(photoIndex + 1);

        testedTargetDirectoryCheckboxItemListenerMock.itemStateChanged(checkBoxChangedMock);

        verify(photoTargetingServiceMock).addTargetDirectoryToPhoto(photoFile, new File(targetDirectoryAbsolutePath));
    }

    @Test
    public void shouldRemoveTargetDirectoryFromPhotoOnDeSelection() {

        String targetDirectoryAbsolutePath = "/parent1/parent2/selectedDirectory";

        when(checkBoxChangedMock.getSource()).thenReturn(targetDirectoryCheckboxMock1);

        when(targetDirectoryCheckboxMock1.isSelected()).thenReturn(false);
        when(targetDirectoryCheckboxMock1.getName()).thenReturn(targetDirectoryAbsolutePath);

        Integer photoIndex = 5;
        when(sourcePhotoListMock.getSelectedIndex()).thenReturn(photoIndex);

        File photoFile = new File("photo5");
        when(sourcePhotoListModelMock.getElementAt(photoIndex)).thenReturn(photoFile);
        when(sourcePhotoListModelMock.getSize()).thenReturn(photoIndex + 1);

        when(sourcePhotoListMock.getSelectedValue()).thenReturn(photoFile);

        testedTargetDirectoryCheckboxItemListenerMock.itemStateChanged(checkBoxChangedMock);

        verify(photoTargetingServiceMock).removeTargetDirectoryFromPhoto(photoFile, new File(targetDirectoryAbsolutePath));
    }

    @Test
    public void shouldCopyPhotosToTheirTargetDirectories() throws IOException {

        Set<File> photos = new HashSet<>();
        photos.add(selectedPhotoFileMock);
        photos.add(selectedPhotoFileMock2);

        when(photoTargetingServiceMock.getPhotos()).thenReturn(photos);

        when(photoTargetingServiceMock.getPhotoTargetDirectories(selectedPhotoFileMock)).thenReturn(selectedPhotoTargetDirectories);
        when(photoTargetingServiceMock.getPhotoTargetDirectories(selectedPhotoFileMock2)).thenReturn(selectedPhotoTargetDirectories2);

        testedCopyPhotosButtonActionListener.actionPerformed(null);

        verify(fileServiceMock).copyPhotoToTargetDirectory(selectedPhotoFileMock, targetDirectory1);
        verify(fileServiceMock).copyPhotoToTargetDirectory(selectedPhotoFileMock, targetDirectory3);

        verify(fileServiceMock).copyPhotoToTargetDirectory(selectedPhotoFileMock2, targetDirectory2);
        verify(fileServiceMock).copyPhotoToTargetDirectory(selectedPhotoFileMock2, targetDirectory3);

        verify(fileServiceMock, times(0)).copyPhotoToTargetDirectory(selectedPhotoFileMock, targetDirectory2);

        verify(fileServiceMock, times(0)).copyPhotoToTargetDirectory(selectedPhotoFileMock2, targetDirectory1);
    }

    private void initImages() {

        images = new File[2];

        images[0] = new File("test.png");
        images[1] = new File("test.jpg");
    }

    private void initSelectedPhotoTargetDirectoriesAndCheckboxes() {

        targetDirectory1 = new File("targetDirectory1");
        targetDirectory2 = new File("targetDirectory2");
        targetDirectory3 = new File("targetDirectory3");

        selectedPhotoTargetDirectories = new HashSet<>();
        selectedPhotoTargetDirectories2 = new HashSet<>();

        selectedPhotoTargetDirectories.add(targetDirectory1);
        selectedPhotoTargetDirectories.add(targetDirectory3);

        selectedPhotoTargetDirectories2.add(targetDirectory2);
        selectedPhotoTargetDirectories2.add(targetDirectory3);

        targetDirectoryCheckboxes = new HashMap<>();

        targetDirectoryCheckboxes.put(targetDirectory1, targetDirectoryCheckboxMock1);
        targetDirectoryCheckboxes.put(targetDirectory2, targetDirectoryCheckboxMock2);
        targetDirectoryCheckboxes.put(targetDirectory3, targetDirectoryCheckboxMock3);
    }

    private void initTestedFormToCreateMocks() {

        doReturn(sourceDirectoryFileChooserMock).when(testedForm).createSourceDirectoryFileChooser(fileServiceMock);
        doReturn(targetDirectoryFileChooserMock).when(testedForm).createTargetDirectoryFileChooser();
        doReturn(testedCellRenderer).when(testedForm).createSourceImageListCellRenderer();
        doReturn(testedSourceDirectoryActionListener).when(testedForm).createSelectSourceDirectoryButtonActionListener();
        doReturn(testedListSelectionListener).when(testedForm).createSourceImageListSelectionListener();
        doReturn(testedNextImageButtonActionListener).when(testedForm).createNextImageButtonActionListener();
        doReturn(testedPreviousImageButtonActionListener).when(testedForm).createPreviousImageButtonActionListener();
        doReturn(testedAddTargetDirectoryButtonActionListener).when(testedForm).createAddChildButtonActionListener();
        doReturn(boxLayoutMock).when(testedForm).createBoxLayout(photoTargetingInnerPanelMock, BoxLayout.Y_AXIS);
        doReturn(testedTargetDirectoryCheckboxItemListenerMock).when(testedForm).createTargetDirectoryCheckBoxItemListener();
        doReturn(testedCopyPhotosButtonActionListener).when(testedForm).createCopyPhotosButtonActionListener();
    }

    private void initTestedFormToHaveUIElementMocks() {

        testedForm.photoViewerFormPanel = photoViewerPanelMock;
        testedForm.sourcePhotoList = sourcePhotoListMock;
        testedForm.selectSourceDirectoryButton = selectSourceDirectoryButtonMock;
        testedForm.showImageLabel = showImageLabelMock;
        testedForm.nextPhotoButton = nextPhotoButtonMock;
        testedForm.previousPhotoButton = previousPhotoButtonMock;
        testedForm.showImagePanel = showImagePanelMock;
        testedForm.addTargetDirectoryButton = addTargetDirectoryButtonMock;
        testedForm.photoTargetingInnerPanel = photoTargetingInnerPanelMock;
        testedForm.photoTargetingScrollPanel = assignToChildrenScrollPanelMock;
        testedForm.targetDirectoryCheckBoxes = targetDirectoryCheckboxes;
        testedForm.copyPhotosButton = copyPhotosButtonMock;
    }
}
