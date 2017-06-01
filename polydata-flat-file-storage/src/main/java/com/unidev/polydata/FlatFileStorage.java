package com.unidev.polydata;

import com.unidev.polydata.domain.bucket.BasicPolyBucket;

/**
 * Flat file poly storage, storage is separated in metadata poly and list of records
 */
public class FlatFileStorage  {

    private BasicPolyBucket polyBucket;
    private String file;

    public FlatFileStorage() {

    }

    public BasicPolyBucket getPolyBucket() {
        return polyBucket;
    }

    public void setPolyBucket(BasicPolyBucket polyBucket) {
        this.polyBucket = polyBucket;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean hasPoly(String indexName) {
        return false;
    }
}
