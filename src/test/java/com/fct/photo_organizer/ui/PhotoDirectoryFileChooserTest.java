package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.swing.*;

import java.io.File;

import static com.fct.photo_organizer.ui.PhotoDirectoryFileChooser.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhotoDirectoryFileChooserTest {

    @Mock
    FileService fileServiceMock;

    private PhotoDirectoryFileChooser testedFileChooser;
    private InternalPhotoDirectoryFileChooser testedInternalFileChooser;

    File selectedDirectory;

    @Before
    public void setup() {

        initMocks(this);

        testedFileChooser = new PhotoDirectoryFileChooser(fileServiceMock);
        testedInternalFileChooser = spy(new InternalPhotoDirectoryFileChooser(fileServiceMock));

        doAnswer(invocation -> {return null;}).when(testedInternalFileChooser).showErrorMessageForNoPhotoDirectory();

        selectedDirectory = new File("");
    }

    @Test
    public void shouldCreateAnInternalFileChooserForPhotoDirectoriesOnly() {

        JFileChooser createdInternalFileChooser = testedFileChooser.createJFileChooser();

        assertTrue(createdInternalFileChooser instanceof InternalPhotoDirectoryFileChooser);
    }

    @Test
    public void shouldCallSuperApproveSelection() {

        when(fileServiceMock.getImageFilesInDirectory(selectedDirectory)).thenReturn(new File[] {new File("")});

        testedInternalFileChooser.setSelectedFile(selectedDirectory);

        testedInternalFileChooser.approveSelection();

        verify(testedInternalFileChooser).callSuperApproveSelection();
        verify(testedInternalFileChooser, times(0)).showErrorMessageForNoPhotoDirectory();
    }

    @Test
    public void shouldDisplayErrorMessageAndRejectDirectoryIfThereIsNoPhotoInIt() {

        when(fileServiceMock.getImageFilesInDirectory(selectedDirectory)).thenReturn(new File[0]);

        testedInternalFileChooser.setSelectedFile(selectedDirectory);

        testedInternalFileChooser.approveSelection();

        verify(testedInternalFileChooser, times(0)).callSuperApproveSelection();
        verify(testedInternalFileChooser).showErrorMessageForNoPhotoDirectory();
    }
}
