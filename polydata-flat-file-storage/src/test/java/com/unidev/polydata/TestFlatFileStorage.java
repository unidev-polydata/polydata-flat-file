package com.unidev.polydata;

import com.unidev.polydata.domain.BasicPoly;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Test flat file operations storage
 */
public class TestFlatFileStorage {

    @Test
    public void testStorageSave() throws IOException {

        FlatFileStorage flatFileStorage = new FlatFileStorage();
        flatFileStorage.metadata().put("tomato", "potato");

        BasicPoly basicPoly = new BasicPoly()._id("1").link("tomato");
        flatFileStorage.getList().add(basicPoly);

        File tempFile = File.createTempFile("storage", ".json");
        //tempFile.deleteOnExit();

        FlatFileStorageMapper.storageMapper().saveSource(tempFile).save(flatFileStorage);
    }

}
