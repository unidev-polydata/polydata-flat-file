package com.unidev.polydata;

import com.unidev.polydata.domain.BasicPoly;
import com.unidev.polydata.domain.BasicPolyList;
import com.unidev.polydata.domain.bucket.BasicPolyBucket;

/**
 * Flat file poly storage, storage is separated in metadata poly and list of records
 */
public class FlatFileStorage  {

    private BasicPolyBucket polyBucket;
    private String file;

    public FlatFileStorage() {
        polyBucket = BasicPolyBucket.newBucket();
        polyBucket.setMetadata(BasicPoly.newPoly());
        polyBucket.setPolys(BasicPolyList.newList());
    }

    public BasicPoly metadata() {
        return polyBucket.metadata();
    }

    public BasicPolyList polys() {
        return polyBucket.polys();
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

    public void persist(BasicPoly basicPoly) {
        polys().add(basicPoly);
    }
}
