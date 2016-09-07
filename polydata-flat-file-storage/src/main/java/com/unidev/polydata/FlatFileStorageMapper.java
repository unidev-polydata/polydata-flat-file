package com.unidev.polydata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;

/**
 * Flat file storage load/save operations
 */
public class FlatFileStorageMapper {

    public static ObjectMapper STORAGE_OBJECT_MAPPER = new ObjectMapper() {{
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }};

    private InputStream loadSource;
    private OutputStream saveSource;

    public FlatFileStorageMapper() {}

    public static FlatFileStorageMapper storageMapper() {return new FlatFileStorageMapper();}

    public FlatFileStorageMapper loadSource(InputStream inputStream) {
        this.loadSource = inputStream;
        return this;
    }

    public FlatFileStorageMapper saveSource(OutputStream saveSource) {
        this.saveSource = saveSource;
        return this;
    }

    public FlatFileStorageMapper saveSource(File file) {
        try {
            this.saveSource = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new FlatFileStorageException(e);
        }
        return this;
    }

    public FlatFileStorageMapper loadSource(File file) {
        try {
            this.loadSource = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FlatFileStorageException(e);
        }
        return this;
    }


    public FlatFileStorage load() {
        try {
            return STORAGE_OBJECT_MAPPER.readValue(loadSource, FlatFileStorage.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FlatFileStorageException(e);
        }
    }

    public void save(FlatFileStorage storage) {
        try {
            STORAGE_OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(saveSource, storage);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FlatFileStorageException(e);
        }
    }

}
