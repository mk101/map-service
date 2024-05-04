package kolesov.maksim.mapping.map.service.impl;

import kolesov.maksim.mapping.map.model.LayerEntity;
import kolesov.maksim.mapping.map.model.LayerTagEntity;
import kolesov.maksim.mapping.map.model.elastic.LayerSearch;
import kolesov.maksim.mapping.map.service.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public void addLayer(LayerEntity layerEntity) {
        elasticsearchOperations.save(toElastic(layerEntity));
    }

    @Override
    public void updateLayer(LayerEntity layerEntity) {
        elasticsearchOperations.update(toElastic(layerEntity));
    }

    @Override
    public void deleteLayer(UUID id) {
        elasticsearchOperations.delete(id.toString(), LayerSearch.class);
    }

    private LayerSearch toElastic(LayerEntity layerEntity) {
        return LayerSearch.builder()
                .id(layerEntity.getId().toString())
                .horizontalAreaLower(layerEntity.getHorizontalArea().lower().doubleValue())
                .horizontalAreaUpper(layerEntity.getHorizontalArea().upper().doubleValue())
                .verticalAreaLower(layerEntity.getVerticalArea().lower().doubleValue())
                .verticalAreaUpper(layerEntity.getVerticalArea().upper().doubleValue())
                .name(layerEntity.getName())
                .description(layerEntity.getDescription())
                .tags(layerEntity.getTags().stream().map(LayerTagEntity::getValue).toList())
        .build();
    }

}
