package kolesov.maksim.mapping.map.service;

import kolesov.maksim.mapping.map.model.LayerEntity;

import java.util.UUID;

public interface ElasticsearchService {

    void addLayer(LayerEntity layerEntity);
    void updateLayer(LayerEntity layerEntity);
    void deleteLayer(UUID id);

}
