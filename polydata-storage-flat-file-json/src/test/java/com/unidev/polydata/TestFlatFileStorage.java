package com.unidev.polydata;

import com.unidev.polydata.domain.BasicPoly;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test flat file operations storage
 */
public class TestFlatFileStorage {

    @Test
    public void testStorageSave() throws IOException {

        FlatFileStorage flatFileStorage = new FlatFileStorage();
        flatFileStorage.persistMetadata("main", BasicPoly.newPoly("tomato"));

        BasicPoly basicPoly = new BasicPoly()._id("1").link("tomato");
        flatFileStorage.persist("main", basicPoly);

        File tempFile = File.createTempFile("storage", ".json");
        tempFile.deleteOnExit();

        FlatFileStorageMapper.storageMapper().saveSource(tempFile).save(flatFileStorage);
    }

    @Test
    public void testStorageLoading() throws MalformedURLException, URISyntaxException {
        InputStream inputStream = TestFlatFileStorage.class.getResourceAsStream("/test.json");
        FlatFileStorage storage = FlatFileStorageMapper.storageMapper().loadSource(inputStream).load();

        assertThat(storage, is(not(nullValue())));
        assertThat(storage.metadata("main"), is(not(nullValue())));
        assertThat(storage.metadata("main").get()._id(), is("metadata_id"));

        assertThat(storage.fetchPolyMap("main").get().size(), is(2));
        assertThat(storage.fetchById("main", "tomato").get().link(), is("tomato"));
        assertThat(storage.fetchById("main", "tomato").get().fetch("custom_key"), is("custom_value"));
    }

    @Test
    public void testPolyRemoval() {
        FlatFileStorage flatFileStorage = new FlatFileStorage();

        BasicPoly basicPoly = new BasicPoly()._id("1").link("tomato");

        assertThat(flatFileStorage.fetchById("main", "1").isPresent(), is(false));
        flatFileStorage.persist("main", basicPoly);
        assertThat(flatFileStorage.fetchById("main", "1").isPresent(), is(true));

        boolean removeResult = flatFileStorage.removePoly("main", "1");
        assertThat(removeResult, is(true));

        assertThat(flatFileStorage.fetchById("main", "1").isPresent(), is(false));

        removeResult = flatFileStorage.removePoly("main", "1");
        assertThat(removeResult, is(false));

        removeResult = flatFileStorage.removePoly("main", "1");
        assertThat(removeResult, is(false));
    }

    @Test
    public void testFetchById() {
        FlatFileStorage flatFileStorage = new FlatFileStorage();
        BasicPoly basicPoly = new BasicPoly()._id("potato_id").link("tomato");

        assertThat(flatFileStorage.fetchById("main", "potato_id").isPresent(), is(false));

        flatFileStorage.persist("main", basicPoly);

        Optional<BasicPoly> persistedPoly = flatFileStorage.fetchById("main", "potato_id");
        assertThat(persistedPoly.isPresent(), is(true));
        assertThat(persistedPoly.get()._id(), is("potato_id"));
    }

    @Test
    public void testPolyAdding() {
        FlatFileStorage flatFileStorage = new FlatFileStorage();

        assertThat(flatFileStorage.fetchPolyMap("main").isPresent(), is(false));

        BasicPoly potatoPoly = new BasicPoly()._id("potato_id");
        BasicPoly tomatoPoly = new BasicPoly()._id("tomato_id");

        flatFileStorage.persist("main", potatoPoly);

        assertThat(flatFileStorage.fetchPolyMap("main").get().isEmpty(), is(false));
        assertThat(flatFileStorage.fetchPolyMap("main").get().size(), is(1));

        flatFileStorage.persist("main", tomatoPoly);

        assertThat(flatFileStorage.fetchPolyMap("main").get().size(), is(2));

        BasicPoly poly = (BasicPoly) flatFileStorage.fetchById("main", "tomato_id").get();
        assertThat(poly._id(), is("tomato_id"));
    }

}
