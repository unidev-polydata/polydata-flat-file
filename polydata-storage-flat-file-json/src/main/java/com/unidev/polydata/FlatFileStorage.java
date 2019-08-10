package com.unidev.polydata;

import com.unidev.polydata.domain.*;
import com.unidev.polydata.domain.bucket.BasicPolyBucket;
import com.unidev.polydata.model.FlatFileModel;
import com.unidev.polydata.storage.PolyStorage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Flat file poly storage, storage is separated in metadata poly and list of records
 */
@AllArgsConstructor
@NoArgsConstructor
public class FlatFileStorage implements PolyStorage {

    private FlatFileModel flatFileModel;

    @Override
    public <P extends Poly> Optional<P> metadata(String container) {
        return (Optional<P>) Optional.ofNullable(flatFileModel.getMetadata().get(container));
    }

    @Override
    public <P extends Poly> Optional<P> fetchById(String container, String id) {
        HashMap<String, BasicPoly> mapByContainer = flatFileModel.getData().get(container);
        if (mapByContainer == null) {
            return Optional.empty();
        }
        return (Optional<P>) Optional.ofNullable(mapByContainer.get(id));

    }

    @Override
    public <P extends Poly> P persist(String container, P poly) {
        HashMap<String, BasicPoly> mapByContainer = flatFileModel.getData().get(container);
        mapByContainer.put(poly._id(), (BasicPoly) poly);
        return poly;
    }

    @Override
    public <P extends Poly> P persistIndex(String container, Map<String, Object> keys, P poly) {
        throw new FlatFileStorageException("Index persisting not supported");
    }

    @Override
    public <P extends Poly> P persistMetadata(String container, P metadata) {
        flatFileModel.getMetadata().put(container, (BasicPoly) metadata);
        return metadata;
    }

    @Override
    public <P extends PolyList> P query(String container, PolyQuery polyQuery) {
        throw new FlatFileStorageException("Query not supported");
    }

    @Override
    public <P extends PolyList> P queryIndex(String container, PolyQuery polyQuery) {
        throw new FlatFileStorageException("Query index not supported");
    }

    @Override
    public boolean removePoly(String container, String id) {
        HashMap<String, BasicPoly> mapByContainer = flatFileModel.getData().get(container);
        if (mapByContainer == null) {
            return false;
        }
        if (!mapByContainer.containsKey(id)) {
            return false;
        }
        mapByContainer.remove(id);

        return true;
    }
}
