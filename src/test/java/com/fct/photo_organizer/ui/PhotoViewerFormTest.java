package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.service.image.ImageService;
import com.fct.photo_organizer.ui.PhotoViewerForm.SourceImageListCellRenderer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhotoViewerFormTest {

    private PhotoViewerForm testedForm;
    private PhotoViewerForm.SelectSourceDirectoryButtonActionListener testedActionListener;
    private SourceImageListCellRenderer testedCellRenderer;
    private PhotoViewerForm.SourceImageListSelectionListener testedListSelectionListener;

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

    private File[] images;

    @Before
    public void setup() {

        initMocks(this);

        testedForm = spy(new PhotoViewerForm(fileServiceMock, imageServiceMock));
        testedActionListener = testedForm.new SelectSourceDirectoryButtonActionListener();
        testedCellRenderer = spy(new SourceImageListCellRenderer());
        testedListSelectionListener = testedForm.new SourceImageListSelectionListener();

        initImages();

        initTestedFormToCreateMocks();
        initTestedFormToHaveUIElementMocks();

        when(sourceDirectoryFileChooserMock.getSelectedFile()).thenReturn(sourceDirectoryMock);

        when(fileServiceMock.getImageFilesInDirectory(sourceDirectoryMock)).thenReturn(images);
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

        verify(selectSourceDirectoryButtonMock).addActionListener(testedActionListener);
    }

    @Test
    public void shouldPopulateTheImageListWithTheImagesInTheChosenSourceDirectory() {

        testedForm.sourceDirectoryFileChooser = sourceDirectoryFileChooserMock;

        when(sourceDirectoryFileChooserMock.showOpenDialog(photoViewerPanelMock)).thenReturn(JFileChooser.APPROVE_OPTION);

        testedActionListener.actionPerformed(null);

        verify(sourceImageListMock).setListData(images);
    }

    @Test
    public void shouldNotChangeTheImageListOnCancelInFileChooser() {

        testedForm.sourceDirectoryFileChooser = sourceDirectoryFileChooserMock;

        when(sourceDirectoryFileChooserMock.showOpenDialog(photoViewerPanelMock)).thenReturn(JFileChooser.CANCEL_OPTION);

        testedActionListener.actionPerformed(null);

        verify(sourceImageListMock, times(0)).setListData(images);
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

        ListSelectionEvent eventMock = mock(ListSelectionEvent.class);
        when(eventMock.getSource()).thenReturn(sourceImageListMock);

        File selectedImageFileMock = mock(File.class);
        when(sourceImageListMock.getSelectedValue()).thenReturn(selectedImageFileMock);

        ImageIcon imageIconMock = mock(ImageIcon.class);
        when(imageServiceMock.loadImageIcon(selectedImageFileMock)).thenReturn(imageIconMock);

        testedListSelectionListener.valueChanged(eventMock);

        verify(showImageLabelMock).setIcon(imageIconMock);
    }

    private void initImages() {

        images = new File[2];

        images[0] = new File("test.png");
        images[1] = new File("test.jpg");
    }

    private void initTestedFormToCreateMocks() {

        doReturn(sourceDirectoryFileChooserMock).when(testedForm).createSourceDirectoryFileChooser();
        doReturn(testedCellRenderer).when(testedForm).createSourceImageListCellRenderer();
        doReturn(testedActionListener).when(testedForm).createSelectSourceDirectoryButtonActionListener();
        doReturn(testedListSelectionListener).when(testedForm).createSourceImageListSelectionListener();
    }

    private void initTestedFormToHaveUIElementMocks() {

        testedForm.photoViewerFormPanel = photoViewerPanelMock;
        testedForm.sourceImageList = sourceImageListMock;
        testedForm.selectSourceDirectoryButton = selectSourceDirectoryButtonMock;
        testedForm.showImageLabel = showImageLabelMock;
    }
}
