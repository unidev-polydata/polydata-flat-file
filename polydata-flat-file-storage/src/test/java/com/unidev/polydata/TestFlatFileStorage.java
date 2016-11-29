package com.unidev.polydata;

import com.unidev.polydata.domain.BasicPoly;
import com.unidev.polydata.domain.Poly;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Test flat file operations storage
 */
public class TestFlatFileStorage {

    @Test
    public void testStorageSave() throws IOException {

        FlatFileStorage flatFileStorage = new FlatFileStorage();
        flatFileStorage.metadata().put("tomato", "potato");

        BasicPoly basicPoly = new BasicPoly()._id("1").link("tomato");
        flatFileStorage.persist(basicPoly);

        File tempFile = File.createTempFile("storage", ".json");
        tempFile.deleteOnExit();

        FlatFileStorageMapper.storageMapper().saveSource(tempFile).save(flatFileStorage);
    }

    @Test
    public void testStorageLoading() throws MalformedURLException, URISyntaxException {
        InputStream inputStream = TestFlatFileStorage.class.getResourceAsStream("/test.json");
        FlatFileStorage storage = FlatFileStorageMapper.storageMapper().loadSource(inputStream).load();

        assertThat(storage, is(not(nullValue())));
        assertThat(storage.metadata(), is(not(nullValue())));
        assertThat(storage.metadata()._id(), is ("metadata_id"));

        assertThat(storage.list().size(), is(2));
        assertThat(storage.getList().get(1).link(), is("tomato"));
        assertThat((String)storage.getList().get(1).fetch("custom_key"), is("custom_value"));
    }

    @Test
    public void testPolyRemoval() {
        FlatFileStorage flatFileStorage = new FlatFileStorage();

        BasicPoly basicPoly = new BasicPoly()._id("1").link("tomato");

        assertThat(flatFileStorage.hasPoly("1"), is(false));

        flatFileStorage.persist(basicPoly);
        assertThat(flatFileStorage.hasPoly("1"), is(true));

        flatFileStorage.remove("1");
        assertThat(flatFileStorage.hasPoly("1"), is(false));
    }

    @Test
    public void testFetchById() {
        FlatFileStorage flatFileStorage = new FlatFileStorage();
        BasicPoly basicPoly = new BasicPoly()._id("potato_id").link("tomato");

        Poly poly = flatFileStorage.fetchById("potato_id");
        assertThat(poly, is(nullValue()));

        flatFileStorage.persist(basicPoly);


        Poly persistedPoly = flatFileStorage.fetchById("potato_id");
        assertThat(persistedPoly, is(not(nullValue())));
        assertThat(persistedPoly._id(), is("potato_id"));
    }

}
