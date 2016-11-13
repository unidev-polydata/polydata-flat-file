package com.unidev.polydata;

import com.unidev.polydata.domain.BasicPoly;
import com.unidev.polydata.domain.Poly;

import java.io.File;

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
     * Generate link where should be stored poly
     * @param indexName
     * @param poly
     * @return
     */
    public String genPolyLink(String indexName, BasicPoly poly) {
        return indexName + ".json";
    }

    /**
     * Add poly to index
     * @param indexName
     * @param poly
     */
    public void save(String indexName, BasicPoly poly) {

        if (!index.hasPoly(indexName)) {
            BasicPoly indexPoly = new BasicPoly()._id(indexName).link(genPolyLink(indexName, poly));
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
        indexPoly.put("count", storage.list().size()); // maintain item count in each index
        indexPoly.put("lastUpdate", System.currentTimeMillis()); // last update of index, should help with caching

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
