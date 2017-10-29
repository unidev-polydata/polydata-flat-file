package com.unidev.polydata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unidev.polydata.domain.bucket.BasicPolyBucket;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public FlatFileStorageMapper() {
    }

    public static FlatFileStorageMapper storageMapper() {
        return new FlatFileStorageMapper();
    }

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
            BasicPolyBucket basicPolyBucket = STORAGE_OBJECT_MAPPER
                .readValue(loadSource, BasicPolyBucket.class);
            return new FlatFileStorage(basicPolyBucket);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FlatFileStorageException(e);
        }
    }

    void save(FlatFileStorage storage) {
        try {
            STORAGE_OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                .writeValue(saveSource, storage.getPolyBucket());
        } catch (IOException e) {
            e.printStackTrace();
            throw new FlatFileStorageException(e);
        }
    }

}
