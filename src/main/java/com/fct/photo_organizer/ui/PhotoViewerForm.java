package com.fct.photo_organizer.ui;

import com.fct.photo_organizer.service.file.FileService;
import com.fct.photo_organizer.service.image.ImageService;
import com.fct.photo_organizer.service.photo_targeting.PhotoTargetingService;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class PhotoViewerForm {

    JPanel photoViewerFormPanel;
    JButton selectSourceDirectoryButton;
    JList<File> sourcePhotoList;
    private JScrollPane sourceImageScrollPanel;
    JLabel showImageLabel;
    JPanel showImagePanel;
    JButton nextPhotoButton;
    JButton previousPhotoButton;
    private JPanel imageNavigationPanel;
    private JPanel assignmentPanel;
    private JPanel addTargetDirectoryPanel;
    JButton addTargetDirectoryButton;
    private JPanel selectSourceDirectoryPanel;
    JScrollPane photoTargetingScrollPanel;
    JPanel photoTargetingInnerPanel;
    private JPanel sourceDirectoryTitlePanel;
    private JLabel sourceDirectoryLabel;
    private JPanel targetDirectoriesPanel;
    private JLabel targetDirectoriesLabel;
    DirectoryFileChooser targetDirectoryFileChooser;
    PhotoDirectoryFileChooser sourceDirectoryFileChooser;

    private FileService fileService;
    private ImageService imageService;
    private PhotoTargetingService photoTargetingService;
    HashMap<File, JCheckBox> targetDirectoryCheckBoxes;

    static final int ADD_TARGET_DIRECTORY_TO_PHOTO_PANEL_WIDTH = 200;
    static final int ADD_TARGET_DIRECTORY_TO_PHOTO_PANEL_HEIGHT = 25;

    PhotoViewerForm(FileService fileService, ImageService imageService, PhotoTargetingService photoTargetingService) {

        this.fileService = fileService;
        this.imageService = imageService;
        this.photoTargetingService = photoTargetingService;
        this.targetDirectoryCheckBoxes = new HashMap<>();
    }

    void init() {

        initSourceDirectoryFileChooser();
        initTargetDirectoryFileChooser();
        initSourceImageList();
        initSelectSourceDirectoryButton();
        initImageNavigationButtons();
        initAddChildButton();
        initAssignToChildrenPanel();
    }

    private void initSourceDirectoryFileChooser() {

        sourceDirectoryFileChooser = createSourceDirectoryFileChooser(fileService);
        sourceDirectoryFileChooser.init();
    }

    private void initTargetDirectoryFileChooser() {

        targetDirectoryFileChooser = createTargetDirectoryFileChooser();
        targetDirectoryFileChooser.init();
    }

    private void initSourceImageList() {

        sourcePhotoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sourcePhotoList.setCellRenderer(createSourceImageListCellRenderer());
        sourcePhotoList.addListSelectionListener(createSourceImageListSelectionListener());
    }

    private void initSelectSourceDirectoryButton() {

        selectSourceDirectoryButton.addActionListener(createSelectSourceDirectoryButtonActionListener());
    }

    private void initImageNavigationButtons() {

        previousPhotoButton.addActionListener(createPreviousImageButtonActionListener());
        nextPhotoButton.addActionListener(createNextImageButtonActionListener());
    }

    private void initAddChildButton() {

        addTargetDirectoryButton.addActionListener(createAddChildButtonActionListener());
        addTargetDirectoryButton.setEnabled(false);
    }

    private void initAssignToChildrenPanel() {

        photoTargetingInnerPanel.setLayout(createBoxLayout(photoTargetingInnerPanel, BoxLayout.Y_AXIS));
    }

    PhotoDirectoryFileChooser createSourceDirectoryFileChooser(FileService fileService) {
        return new PhotoDirectoryFileChooser(fileService);
    }

    DirectoryFileChooser createTargetDirectoryFileChooser() {
        return new DirectoryFileChooser();
    }

    SourceImageListCellRenderer createSourceImageListCellRenderer() {
        return new SourceImageListCellRenderer();
    }

    SourcePhotosListSelectionListener createSourceImageListSelectionListener() {
        return new SourcePhotosListSelectionListener();
    }

    SelectSourceDirectoryButtonActionListener createSelectSourceDirectoryButtonActionListener() {
        return new SelectSourceDirectoryButtonActionListener();
    }

    NextImageButtonActionListener createNextImageButtonActionListener() {
        return new NextImageButtonActionListener();
    }

    PreviousImageButtonActionListener createPreviousImageButtonActionListener() {
        return new PreviousImageButtonActionListener();
    }

    AddTargetDirectoryButtonActionListener createAddChildButtonActionListener() {
        return new AddTargetDirectoryButtonActionListener();
    }

    TargetDirectoryCheckBoxItemListener createTargetDirectoryCheckBoxItemListener() {
        return new TargetDirectoryCheckBoxItemListener();
    }

    BoxLayout createBoxLayout(Container target, int axis) {
        return new BoxLayout(target, axis);
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
        photoViewerFormPanel.setLayout(new GridLayoutManager(3, 5, new Insets(10, 10, 10, 10), -1, -1));
        sourceImageScrollPanel = new JScrollPane();
        photoViewerFormPanel.add(sourceImageScrollPanel, new GridConstraints(1, 0, 2, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(175, 600), new Dimension(175, 600), new Dimension(175, 600), 0, false));
        sourcePhotoList = new JList();
        sourcePhotoList.setSelectionMode(2);
        sourceImageScrollPanel.setViewportView(sourcePhotoList);
        showImagePanel = new JPanel();
        showImagePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        photoViewerFormPanel.add(showImagePanel, new GridConstraints(1, 3, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(800, 600), new Dimension(800, 600), new Dimension(800, 600), 0, false));
        showImageLabel = new JLabel();
        showImageLabel.setText("");
        showImagePanel.add(showImageLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(146, 0), null, 0, false));
        imageNavigationPanel = new JPanel();
        imageNavigationPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        photoViewerFormPanel.add(imageNavigationPanel, new GridConstraints(1, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        previousPhotoButton = new JButton();
        previousPhotoButton.setEnabled(false);
        previousPhotoButton.setText("Prev");
        imageNavigationPanel.add(previousPhotoButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextPhotoButton = new JButton();
        nextPhotoButton.setEnabled(false);
        nextPhotoButton.setText("Next");
        imageNavigationPanel.add(nextPhotoButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        assignmentPanel = new JPanel();
        assignmentPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        photoViewerFormPanel.add(assignmentPanel, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(175, 600), new Dimension(175, 600), new Dimension(175, 600), 0, false));
        photoTargetingScrollPanel = new JScrollPane();
        photoTargetingScrollPanel.setEnabled(false);
        photoTargetingScrollPanel.setHorizontalScrollBarPolicy(30);
        photoTargetingScrollPanel.setVerticalScrollBarPolicy(20);
        assignmentPanel.add(photoTargetingScrollPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        photoTargetingInnerPanel = new JPanel();
        photoTargetingInnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        photoTargetingScrollPanel.setViewportView(photoTargetingInnerPanel);
        selectSourceDirectoryPanel = new JPanel();
        selectSourceDirectoryPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        photoViewerFormPanel.add(selectSourceDirectoryPanel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(175, -1), new Dimension(175, -1), new Dimension(175, -1), 0, false));
        selectSourceDirectoryButton = new JButton();
        selectSourceDirectoryButton.setHorizontalAlignment(2);
        selectSourceDirectoryButton.setHorizontalTextPosition(0);
        selectSourceDirectoryButton.setText("Select...");
        selectSourceDirectoryPanel.add(selectSourceDirectoryButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sourceDirectoryTitlePanel = new JPanel();
        sourceDirectoryTitlePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        selectSourceDirectoryPanel.add(sourceDirectoryTitlePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(175, -1), new Dimension(175, -1), new Dimension(175, -1), 0, false));
        sourceDirectoryLabel = new JLabel();
        sourceDirectoryLabel.setText("Source Directory");
        sourceDirectoryTitlePanel.add(sourceDirectoryLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addTargetDirectoryPanel = new JPanel();
        addTargetDirectoryPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        photoViewerFormPanel.add(addTargetDirectoryPanel, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(175, -1), new Dimension(175, -1), new Dimension(175, -1), 0, false));
        addTargetDirectoryButton = new JButton();
        addTargetDirectoryButton.setEnabled(true);
        addTargetDirectoryButton.setHorizontalTextPosition(11);
        addTargetDirectoryButton.setText("Add...");
        addTargetDirectoryPanel.add(addTargetDirectoryButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        targetDirectoriesPanel = new JPanel();
        targetDirectoriesPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        addTargetDirectoryPanel.add(targetDirectoriesPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(175, -1), new Dimension(175, -1), new Dimension(175, -1), 0, false));
        targetDirectoriesLabel = new JLabel();
        targetDirectoriesLabel.setText("Target Directories");
        targetDirectoriesPanel.add(targetDirectoriesLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return photoViewerFormPanel;
    }

    class SelectSourceDirectoryButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

            if (sourceDirectoryFileChooser.showOpenDialog(photoViewerFormPanel) == JFileChooser.APPROVE_OPTION) {

                File sourceDirectory = sourceDirectoryFileChooser.getSelectedFile();

                File[] images = fileService.getImageFilesInDirectory(sourceDirectory);

                photoTargetingService.initPhotoTargeting(Arrays.asList(images));

                sourcePhotoList.setListData(images);
                sourcePhotoList.setSelectedIndex(0);

                addTargetDirectoryButton.setEnabled(true);
                deselectTargetDirectoryCheckboxes();
            }
        }

        private void deselectTargetDirectoryCheckboxes() {

            targetDirectoryCheckBoxes.forEach((targetDirectory, targetDirectoryCheckbox) -> {
                targetDirectoryCheckbox.setSelected(false);
            });
        }
    }

    static class SourceImageListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object file, int fileIndex, boolean isSelected, boolean cellHasFocus) {

            JLabel cellRendererLabel = getSuperListCellRendererComponent(list, file, fileIndex, isSelected, cellHasFocus);

            cellRendererLabel.setText(((File) file).getName());

            return cellRendererLabel;
        }

        JLabel getSuperListCellRendererComponent(JList list, Object file, int fileIndex, boolean isSelected, boolean cellHasFocus) {
            return (JLabel) super.getListCellRendererComponent(list, file, fileIndex, isSelected, cellHasFocus);
        }
    }

    class SourcePhotosListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent event) {

            JList<File> imageList = (JList<File>) event.getSource();

            File selectedPhotoFile = imageList.getSelectedValue();

            if (selectedPhotoFile != null) {

                ImageIcon imageIcon = loadImageIcon(selectedPhotoFile);
                showImageLabel.setIcon(imageIcon);

                loadPhotoTargeting(selectedPhotoFile);

                enableDisablePreviousImageButton(imageList);
                enableDisableNextImageButton(imageList);
            }
        }

        private void enableDisablePreviousImageButton(JList<File> imageList) {

            int selectedIndex = imageList.getSelectedIndex();

            previousPhotoButton.setEnabled(selectedIndex != 0);
        }

        private void enableDisableNextImageButton(JList<File> imageList) {

            int selectedIndex = imageList.getSelectedIndex();

            int listSize = imageList.getModel().getSize();

            nextPhotoButton.setEnabled(selectedIndex != listSize - 1);
        }

        private ImageIcon loadImageIcon(File selectedImageFile) {

            ImageIcon imageIcon = null;

            try {

                int showImagePanelWidth = showImagePanel.getWidth();
                int showImagePanelHeight = showImagePanel.getHeight();

                imageIcon = imageService.loadImageIcon(selectedImageFile, showImagePanelWidth, showImagePanelHeight);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(photoViewerFormPanel, "An error happened during loading the image!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            return imageIcon;
        }

        private void loadPhotoTargeting(File selectedPhotoFile) {

            Set<File> photoTargetDirectories = photoTargetingService.getPhotoTargetDirectories(selectedPhotoFile);

            targetDirectoryCheckBoxes.forEach((targetDirectory, targetDirectoryCheckbox) -> {

                targetDirectoryCheckbox.setSelected(photoTargetDirectories.contains(targetDirectory));
            });
        }
    }

    class PreviousImageButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

            int currentSelectedIndex = sourcePhotoList.getSelectedIndex();

            sourcePhotoList.setSelectedIndex(currentSelectedIndex - 1);
        }
    }

    class NextImageButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

            int currentSelectedIndex = sourcePhotoList.getSelectedIndex();

            sourcePhotoList.setSelectedIndex(currentSelectedIndex + 1);
        }
    }

    class AddTargetDirectoryButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

            if (targetDirectoryFileChooser.showOpenDialog(photoViewerFormPanel) == JFileChooser.APPROVE_OPTION) {

                File selectedDirectory = targetDirectoryFileChooser.getSelectedFile();

                try {
                    JPanel addTargetDirectoryToPhotoPanel = createPanel();
                    addTargetDirectoryToPhotoPanel.setLayout(createFlowLayout(FlowLayout.LEFT));
                    addTargetDirectoryToPhotoPanel.setMaximumSize(
                            createDimension(ADD_TARGET_DIRECTORY_TO_PHOTO_PANEL_WIDTH, ADD_TARGET_DIRECTORY_TO_PHOTO_PANEL_HEIGHT));

                    JCheckBox targetDirectoryCheckbox = createCheckbox(selectedDirectory.getName());
                    targetDirectoryCheckbox.setName(selectedDirectory.getAbsolutePath());
                    targetDirectoryCheckbox.addItemListener(createTargetDirectoryCheckBoxItemListener());

                    targetDirectoryCheckBoxes.put(new File(selectedDirectory.getAbsolutePath()), targetDirectoryCheckbox);

                    addTargetDirectoryToPhotoPanel.add(targetDirectoryCheckbox);

                    ((JPanel) photoTargetingScrollPanel.getViewport().getView()).add(addTargetDirectoryToPhotoPanel);
                    photoTargetingInnerPanel.revalidate();
                    photoTargetingInnerPanel.repaint();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(photoViewerFormPanel, "An error happened!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        JPanel createPanel() {
            return new JPanel();
        }

        FlowLayout createFlowLayout(int align) {
            return new FlowLayout(align);
        }

        Dimension createDimension(int width, int height) {
            return new Dimension(width, height);
        }

        JCheckBox createCheckbox(String label) {
            return new JCheckBox(label);
        }
    }

    class TargetDirectoryCheckBoxItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {

            JCheckBox targetDirectoryCheckbox = (JCheckBox) event.getSource();

            File targetDirectory = new File(targetDirectoryCheckbox.getName());

            File selectedPhoto = sourcePhotoList.getModel().getElementAt(sourcePhotoList.getSelectedIndex());

            if (targetDirectoryCheckbox.isSelected()) {

                photoTargetingService.addTargetDirectoryToPhoto(selectedPhoto, targetDirectory);

            } else {

                photoTargetingService.removeTargetDirectoryFromPhoto(selectedPhoto, targetDirectory);
            }
        }
    }
}
