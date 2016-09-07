package com.unidev.polydata;


import com.unidev.polydata.domain.BasicPoly;
import com.unidev.polydata.domain.Poly;
import com.unidev.polydata.storage.PolyStorageWithMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Flat file poly storage
 */
public class FlatFileStorage implements PolyStorageWithMetadata {

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
        Optional<BasicPoly> polyOptional = list.stream().filter(poly -> id.equals(poly._id())).findFirst();
        if (polyOptional.isPresent()) {
            return (T) polyOptional.get();
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
