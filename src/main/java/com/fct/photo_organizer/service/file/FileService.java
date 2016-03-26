package com.fct.photo_organizer.service.file;

import java.io.File;
import java.util.List;

/**
 * Created by zsolt_balogh on 20/03/2016.
 */
public interface FileService {

    File[] getImageFilesInDirectory(File targetDirectory);

}
