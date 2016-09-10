package com.unidev.polydata;


import com.unidev.polydata.domain.BasicPoly;
import com.unidev.polydata.domain.Poly;
import com.unidev.polydata.storage.PolyStorageWithMetadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Flat file poly storage
 */
public class FlatFileStorage implements PolyStorageWithMetadata, Serializable {

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
    public Poly fetchMetadata() {
        return metadata;
    }

    @Override
    public <T extends Poly> T fetchById(String id) {

        for(BasicPoly poly : list) {
            if (id.equalsIgnoreCase(poly._id())) {
                return (T) poly;
            }
        }

        return null;
    }

    @Override
    public Collection<? extends Poly> list() {
        return list;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FlatFileStorage{");
        sb.append("metadata=").append(metadata);
        sb.append(", polylist=").append(list);
        sb.append('}');
        return sb.toString();
    }

    public FlatFileStorage add(BasicPoly poly) {
        list.add(poly);
        return this;
    }

    public FlatFileStorage addFirst(BasicPoly poly) {
        list.add(0, poly);
        return this;
    }

    public FlatFileStorage remove(String id) {
        Poly poly = fetchById(id);
        if (poly != null) {
            list.remove(poly);
        }
        return this;
    }

    public boolean hasPoly(String id) {
        if (fetchById(id) != null) {
            return true;
        }
        return false;
    }


    public BasicPoly metadata() {
        return metadata;
    }

    public BasicPoly getMetadata() {
        return metadata;
    }

    public void setMetadata(BasicPoly metadata) {
        this.metadata = metadata;
    }

    public List<BasicPoly> getList() {
        return list;
    }

    public void setList(List<BasicPoly> list) {
        this.list = list;
    }
}
