package com.fct.photo_organizer.ui;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static com.fct.photo_organizer.ui.DirectoryFileChooser.ROOT_DIRECTORY;
import static com.fct.photo_organizer.ui.DirectoryFileChooser.TITLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DirectoryFileChooserTest {

    private DirectoryFileChooser testedFileChooser;

    @Mock
    private JFileChooser fileChooserMock;

    @Before
    public void setup() {

        initMocks(this);

        testedFileChooser = spy(new DirectoryFileChooser());

        doReturn(fileChooserMock).when(testedFileChooser).createJFileChooser();
    }

    @Test
    public void shouldSetItsCurrentDirectoryToRootDuringInit() {

        testedFileChooser.init();

        verify(fileChooserMock).setCurrentDirectory(new File(ROOT_DIRECTORY));
    }

    @Test
    public void shouldSetItsTitleDuringInit() {

        testedFileChooser.init();

        verify(fileChooserMock).setDialogTitle(TITLE);
    }

    @Test
    public void shouldSetItsFileSelectionModeToDirectoriesOnlyDuringInit() {

        testedFileChooser.init();

        verify(fileChooserMock).setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    @Test
    public void shouldSetItsAllFileFilterUsedFlagToFalseDuringInit() {

        testedFileChooser.init();

        verify(fileChooserMock).setAcceptAllFileFilterUsed(false);
    }

    @Test
    public void shouldDelegateShowDialogCallToItsInternalFileChooser() {

        Component parentComponentMock = mock(Component.class);
        when(fileChooserMock.showOpenDialog(parentComponentMock)).thenReturn(4);

        testedFileChooser.init();
        int showOpenDialogResult = testedFileChooser.showOpenDialog(parentComponentMock);

        assertEquals(4, showOpenDialogResult);
    }

    @Test
    public void shouldDelegateGetSelectedFileCallToItsInternalFileChooser() {

        File file = mock(File.class);

        when(fileChooserMock.getSelectedFile()).thenReturn(file);

        testedFileChooser.init();
        File selectedFile = testedFileChooser.getSelectedFile();

        assertEquals(file, selectedFile);
    }
}
