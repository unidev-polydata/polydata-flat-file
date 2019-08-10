package com.unidev.polydata;

import com.unidev.polydata.domain.*;
import com.unidev.polydata.domain.bucket.BasicPolyBucket;
import com.unidev.polydata.storage.PolyStorage;

import java.util.Map;
import java.util.Optional;

/**
 * Flat file poly storage, storage is separated in metadata poly and list of records
 */
public class FlatFileStorage implements PolyStorage {


    @Override
    public <P extends Poly> Optional<P> metadata(String container) {
        return Optional.empty();
    }

    @Override
    public <P extends Poly> Optional<P> fetchById(String container, String id) {
        return Optional.empty();
    }

    @Override
    public <P extends Poly> P persist(String container, P poly) {
        return null;
    }

    @Override
    public <P extends Poly> P persistIndex(String container, Map<String, Object> keys, P poly) {
        return null;
    }

    @Override
    public <P extends Poly> P persistMetadata(String container, P metadata) {
        return null;
    }

    @Override
    public <P extends PolyList> P query(String container, PolyQuery polyQuery) {
        return null;
    }

    @Override
    public <P extends PolyList> P queryIndex(String container, PolyQuery polyQuery) {
        return null;
    }

    @Override
    public boolean removePoly(String container, String id) {
        return false;
    }
}
