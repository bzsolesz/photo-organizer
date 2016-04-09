package com.fct.photo_organizer.service.image;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public interface ImageService {

    ImageIcon loadImageIcon(File imageFile, int maxWidth, int maxHeight) throws IOException;
}
