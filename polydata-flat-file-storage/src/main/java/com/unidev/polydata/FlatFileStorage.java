package com.unidev.polydata;


import com.unidev.polydata.domain.BasicPoly;
import com.unidev.polydata.domain.Poly;
import com.unidev.polydata.storage.ChangablePolyStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Flat file poly storage, storage is separated in metadata poly and list of records
 */
public class FlatFileStorage implements ChangablePolyStorage {

    private BasicPoly metadata;
    private List<BasicPoly> list;

    public FlatFileStorage() {
        metadata = new BasicPoly();
        list = new ArrayList<>();
    }

    public FlatFileStorage(BasicPoly metadata, List<BasicPoly> polyList) {
        this.metadata = metadata;
        this.list = polyList;
    }

    @Override
    public <P extends Poly> P persist(P poly) {
        list.add((BasicPoly) poly);
        return poly;
    }

    @Override
    public boolean remove(String id) {
        Poly poly = fetchById(id);
        if (poly != null) {
            list.remove(poly);
            return true;
        }
        return false;
    }

    @Override
    public <P extends Poly> P metadata() {
        return (P) metadata;
    }

    @Override
    public <P extends Poly> P fetchById(String id) {
        for(Poly poly : list) {
            if (id.equalsIgnoreCase(poly._id())) {
                return (P) poly;
            }
        }
        return null;
    }

    @Override
    public Collection<? extends Poly> list() {
        return list;
    }

    @Override
    public long size() {
        return list.size();
    }

    public boolean hasPoly(String id) {
        if (fetchById(id) != null) {
            return true;
        }
        return false;
    }

    public FlatFileStorage add(BasicPoly poly) {
        list.add(poly);
        return this;
    }

    public FlatFileStorage addFirst(BasicPoly poly) {
        list.add(0, poly);
        return this;
    }

    public void withMetadata(BasicPoly metadata) {
        this.metadata = metadata;
    }

    public List<BasicPoly> getList() {
        return list;
    }

    public void setList(List<BasicPoly> list) {
        this.list = list;
    }

    public BasicPoly getMetadata() {
        return metadata;
    }

    public void setMetadata(BasicPoly metadata) {
        this.metadata = metadata;
    }
}
