package com.unidev.polydata;

import com.unidev.polydata.domain.BasicPoly;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

        assertThat(storage.polys().size(), is(2));
        assertThat(storage.polys().get(1).link(), is("tomato"));
        assertThat((String)storage.polys().get(1).fetch("custom_key"), is("custom_value"));
    }

    @Test
    public void testPolyRemoval() {
        FlatFileStorage flatFileStorage = new FlatFileStorage();

        BasicPoly basicPoly = new BasicPoly()._id("1").link("tomato");

        assertThat(flatFileStorage.hasPoly("1"), is(false));

        flatFileStorage.persist(basicPoly);
        assertThat(flatFileStorage.hasPoly("1"), is(true));

        boolean removeResult = flatFileStorage.removeById("1");
        assertThat(removeResult, is(true));

        assertThat(flatFileStorage.hasPoly("1"), is(false));

        removeResult = flatFileStorage.removeById("1");
        assertThat(removeResult, is(false));

        removeResult = flatFileStorage.removeById("2");
        assertThat(removeResult, is(false));
    }

    @Test
    public void testFetchById() {
        FlatFileStorage flatFileStorage = new FlatFileStorage();
        BasicPoly basicPoly = new BasicPoly()._id("potato_id").link("tomato");

        BasicPoly poly = flatFileStorage.fetchPoly("potato_id");
        assertThat(poly, is(nullValue()));

        flatFileStorage.persist(basicPoly);


        BasicPoly persistedPoly = flatFileStorage.fetchPoly("potato_id");
        assertThat(persistedPoly, is(not(nullValue())));
        assertThat(persistedPoly._id(), is("potato_id"));
    }

    @Test
    public void testPolyAdding() {
        FlatFileStorage flatFileStorage = new FlatFileStorage();

        assertThat(flatFileStorage.polys().isEmpty(), is(true));

        BasicPoly potatoPoly = new BasicPoly()._id("potato_id");
        BasicPoly tomatoPoly = new BasicPoly()._id("tomato_id");

        flatFileStorage.persist(potatoPoly);

        assertThat(flatFileStorage.polys().isEmpty(), is(false));
        assertThat(flatFileStorage.polys().size(), is(1));

        flatFileStorage.addFirst(tomatoPoly);

        assertThat(flatFileStorage.polys().size(), is(2));

        BasicPoly poly = flatFileStorage.polys().iterator().next();
        assertThat(poly._id(), is("tomato_id"));
    }

}
