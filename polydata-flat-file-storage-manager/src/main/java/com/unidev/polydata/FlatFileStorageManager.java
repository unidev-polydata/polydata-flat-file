package com.unidev.polydata;

import com.unidev.polydata.domain.BasicPoly;
import com.unidev.polydata.domain.Poly;

import java.io.File;
import java.util.Optional;

/**
 * Flat file hierarchic storage for poly records
 * Manager operate a index file which link other storages
 *
 */
public class FlatFileStorageManager {

    public static final String INDEX_FILE = "index.json";

    private File storageRoot;
    private FlatFileStorage index;


    public FlatFileStorageManager(File storageRoot) {
        this.storageRoot = storageRoot;
        loadIndex();
    }

    public FlatFileStorageManager(String storageRoot) {
        this(new File(storageRoot));
    }


    /**
     * Load index file
     */
    public void loadIndex() {
        File indexFile = new File(storageRoot, INDEX_FILE);
        if (!indexFile.exists()) {
            index = new FlatFileStorage();
        } else {
            index = FlatFileStorageMapper.storageMapper().loadSource(indexFile).load();
        }
    }

    /**
     * Save index file
     */
    public void saveIndex() {
        FlatFileStorageMapper.storageMapper().saveSource(new File(storageRoot, INDEX_FILE)).save(index);
    }

    /**
     * Fetch index file
     * @return
     */
    public FlatFileStorage index() {
        return index;
    }

    /**
     * Add poly to index
     * @param indexName
     * @param poly
     */
    public void save(String indexName, BasicPoly poly) {
        if (!index.hasPoly(indexName)) {
            BasicPoly indexPoly = new BasicPoly()._id(indexName).link(indexName + ".json");
            index.add(indexPoly);
        }
        Poly indexPoly = index.fetchById(indexName);
        File storageFile = new File(storageRoot, indexPoly.link());
        FlatFileStorage storage;
        if (storageFile.exists()) {
            storage = FlatFileStorageMapper.storageMapper().loadSource(storageFile).load();
        } else {
            storage = new FlatFileStorage();
        }
        storage.add(poly);


        FlatFileStorageMapper.storageMapper().saveSource(storageFile).save(storage);
        saveIndex();
    }

    /**
     * Fetch storage by index name
     * @param indexName
     * @return
     */
    public FlatFileStorage fetchStorage(String indexName) {
        if (!index.hasPoly(indexName)) {
            return null;
        }
        Poly indexPoly = index.fetchById(indexName);
        File storageFile = new File(storageRoot, indexPoly.link());
        if (!storageFile.exists()) {
            return null;
        }
        return FlatFileStorageMapper.storageMapper().loadSource(storageFile).load();
    }


}
