package com.unidev.polydata;

import com.unidev.polydata.domain.BasicPoly;
import com.unidev.polydata.domain.BasicPolyList;
import com.unidev.polydata.domain.bucket.BasicPolyBucket;

/**
 * Flat file poly storage, storage is separated in metadata poly and list of records
 */
public class FlatFileStorage  {

    private BasicPolyBucket polyBucket;

    public FlatFileStorage() {
        polyBucket = BasicPolyBucket.newBucket();
        polyBucket.setMetadata(BasicPoly.newPoly());
        polyBucket.setPolys(BasicPolyList.newList());
    }

    public FlatFileStorage(BasicPolyBucket polyBucket) {
        this.polyBucket = polyBucket;
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

    public boolean hasPoly(String polyId) {
        BasicPoly poly = fetchPoly(polyId);
        if (poly == null) {
            return false;
        }
        return true;
    }

    public BasicPoly fetchPoly(String polyId) {
        for(BasicPoly poly : polys()) {
            if (polyId.equalsIgnoreCase(poly._id())) {
                return poly;
            }
        }
        return null;
    }

    public boolean removeById(String polyId) {
        BasicPoly poly = fetchPoly(polyId);
        if (poly == null) {
            return false;
        }
        polyBucket.getPolys().remove(poly);
        return true;
    }

    public void persist(BasicPoly basicPoly) {
        polys().add(basicPoly);
    }

    public void addFirst(BasicPoly basicPoly) {
        polys().add(0, basicPoly);
    }
}
