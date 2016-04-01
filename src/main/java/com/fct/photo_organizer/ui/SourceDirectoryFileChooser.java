package com.fct.photo_organizer.ui;

import javax.swing.*;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;

public class SourceDirectoryFileChooser {

    static final String ROOT_DIRECTORY = "/";
    static final String TITLE = "Select image directory";

    JFileChooser fileChooser;

    void init() {
        fileChooser = createJFileChooser();
        fileChooser.setCurrentDirectory(new File(ROOT_DIRECTORY));
        fileChooser.setDialogTitle(TITLE);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    int showOpenDialog(Component parent) throws HeadlessException {
        return fileChooser.showOpenDialog(parent);
    }

    public File getSelectedFile() {
        return fileChooser.getSelectedFile();
    }

    JFileChooser createJFileChooser() {
        return new JFileChooser();
    }
}
