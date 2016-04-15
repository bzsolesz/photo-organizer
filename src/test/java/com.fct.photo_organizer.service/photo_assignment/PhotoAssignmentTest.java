package com.fct.photo_organizer.service.photo_assignment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhotoAssignmentTest {

    private PhotoAssignment testedAssignment;

    @Mock
    private File photoFileMock;
    @Mock
    private File childDirectoryMock1;
    @Mock
    private File childDirectoryMock2;

    private String childName1 = "child1";
    private String childName2 = "child2";

    @Before
    public void setup() {

        initMocks(this);

        testedAssignment = new PhotoAssignment(photoFileMock);
    }

    @Test
    public void shouldReturnItsPhotoFile() {

        assertEquals(photoFileMock, testedAssignment.getPhoto());
    }

    @Test
    public void shouldAddAssigneeAndReturnThem() throws Exception {

        testedAssignment.addAssignee(childName1, childDirectoryMock1);
        testedAssignment.addAssignee(childName2, childDirectoryMock2);

        assertEquals(2, testedAssignment.getAssigneeNames().size());
        assertTrue(testedAssignment.getAssigneeNames().containsAll(Arrays.asList(childName1, childName2)));

        assertEquals(childDirectoryMock1, testedAssignment.getAssigneeDirectory(childName1));
        assertEquals(childDirectoryMock2, testedAssignment.getAssigneeDirectory(childName2));
    }

    @Test
    public void shouldRemoveAssignee() {

        testedAssignment.addAssignee(childName1, childDirectoryMock1);
        testedAssignment.addAssignee(childName2, childDirectoryMock2);

        testedAssignment.removeAssignee(childName1);

        assertFalse(testedAssignment.getAssigneeNames().contains(childName1));
        assertTrue(testedAssignment.getAssigneeNames().contains(childName2));
    }
}
