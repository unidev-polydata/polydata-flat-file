package com.unidev.polydata;


import com.unidev.polydata.domain.BasicPoly;
import com.unidev.polydata.domain.Poly;
import com.unidev.polydata.storage.PolyStorageWithMetadata;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FlatFileStorage implements PolyStorageWithMetadata {

    private BasicPoly metadata;
    private List<Poly> polylist;

    @Override
    public Poly fetchMetadata() {
        return metadata;
    }

    @Override
    public <T extends Poly> T fetchById(String id) {
        Optional<Poly> polyOptional = polylist.stream().filter(poly -> id.equals(poly._id())).findFirst();
        if (polyOptional.isPresent()) {
            return (T) polyOptional.get();
        }
        return null;
    }

    @Override
    public Collection<? extends Poly> list() {
        return polylist;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FlatFileStorage{");
        sb.append("metadata=").append(metadata);
        sb.append(", polylist=").append(polylist);
        sb.append('}');
        return sb.toString();
    }
}
