package com.unidev.polydata;


import com.unidev.polydata.domain.BasicPoly;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;


public class TestFlatFileStorageManager {

    @Rule
    public TemporaryFolder root = new TemporaryFolder();



    @Test
    public void testStorageManagerSaveLoad() {

        FlatFileStorageManager flatFileStorageManager = new FlatFileStorageManager(root.getRoot()) {
            @Override
            public String genPolyLink(String indexName, BasicPoly poly) {
                return indexName.hashCode() + ".json";
            }
        };

        BasicPoly basicPoly = new BasicPoly();
        basicPoly._id("test1");
        basicPoly.put("text", " Random text");

        flatFileStorageManager.save("index1", basicPoly);

        basicPoly = BasicPoly.newPoly("tomato");
        basicPoly.put("qwe", "1111");

        flatFileStorageManager.save("index2", basicPoly);

        basicPoly = BasicPoly.newPoly("tomato2");
        basicPoly.put("qwe", "123");

        flatFileStorageManager.save("index2", basicPoly);


        FlatFileStorageManager anotherManager = new FlatFileStorageManager(root.getRoot());

        assertThat(anotherManager.index(), is(not(nullValue())));

        FlatFileStorage flatFileStorage = anotherManager.fetchStorage("index1");
        assertThat(flatFileStorage, is(not(nullValue())));
        assertThat(flatFileStorage.getPolyBucket().getPolys().size(), is(1));

        FlatFileStorage flatFileStorageIndex2 = anotherManager.fetchStorage("index2");
        assertThat(flatFileStorageIndex2, is(not(nullValue())));
        assertThat(flatFileStorageIndex2.getPolyBucket().getPolys().size(), is(2));

    }

}