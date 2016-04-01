package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by zsolt_balogh on 21/03/2016.
 */
public class PhotoViewerForm {
    private JPanel photoViewerFormPanel;
    private JButton selectSourceDirectoryButton;
    private JList<File> sourceImageList;
    private JScrollPane sourceImageScrollPanel;
    private SourceDirectoryFileChooser sourceDirectoryFileChooser;

    PhotoViewerForm(FileService fileService) {

        initSourceDirectoryFileChooser();
        initSourceImageList();

        selectSourceDirectoryButton.addActionListener((ActionEvent e) ->
            {
                if (sourceDirectoryFileChooser.showOpenDialog(photoViewerFormPanel) == JFileChooser.APPROVE_OPTION) {

                    File sourceDirectory = sourceDirectoryFileChooser.getSelectedFile();

                    File[] imageFiles = fileService.getImageFilesInDirectory(sourceDirectory);

                    sourceImageList.setListData(imageFiles);
                }
            });
    }

    private void initSourceDirectoryFileChooser() {

        sourceDirectoryFileChooser = new SourceDirectoryFileChooser();
        sourceDirectoryFileChooser.init();
    }

    private void initSourceImageList() {

        sourceImageList.setCellRenderer(new SourceImageListCellRenderer());
    }

    JPanel getPhotoViewerFormPanel() {

        return photoViewerFormPanel;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        photoViewerFormPanel = new JPanel();
        photoViewerFormPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        sourceImageScrollPanel = new JScrollPane();
        photoViewerFormPanel.add(sourceImageScrollPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        sourceImageList = new JList();
        sourceImageScrollPanel.setViewportView(sourceImageList);
        selectSourceDirectoryButton = new JButton();
        selectSourceDirectoryButton.setText("Select...");
        photoViewerFormPanel.add(selectSourceDirectoryButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return photoViewerFormPanel;
    }

    private static class SourceImageListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            JLabel cellRendererLabel = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            cellRendererLabel.setText(((File) value).getName());

            return cellRendererLabel;
        }
    }
}
