package com.fct.photo_organizer.service.file.impl;

import com.fct.photo_organizer.service.file.FileService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsolt_balogh on 20/03/2016.
 */
public class FileServiceImpl implements FileService {

    @Override
    public List<File> getAllFilesIn() {
        return new ArrayList<>();
    }
}
