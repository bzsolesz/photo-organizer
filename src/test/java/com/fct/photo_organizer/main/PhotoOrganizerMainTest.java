package com.fct.photo_organizer.main;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.ui.PhotoOrganizerFrame;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.fct.photo_organizer.main.PhotoOrganizerMain.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class PhotoOrganizerMainTest {

    private PhotoOrganizerFrameRunnable testedRunnable;

    @Mock
    private PhotoOrganizerFrame photoOrganizerFrameMock;
    @Mock
    private FileService fileServiceMock;

    @Before
    public void setup() {

        initMocks(this);

        testedRunnable = spy(new PhotoOrganizerFrameRunnable(fileServiceMock));

        doReturn(photoOrganizerFrameMock).when(testedRunnable).createPhotoOrganizerFrame(fileServiceMock);
    }

    @Test
    public void shouldInitAndMakeThePhotoOrganizerFrameVisible() {

        InOrder photoOrganizerSetupOrder = Mockito.inOrder(photoOrganizerFrameMock);

        testedRunnable.run();

        photoOrganizerSetupOrder.verify(photoOrganizerFrameMock).init();
        photoOrganizerSetupOrder.verify(photoOrganizerFrameMock).setVisible();
    }
}
