package kolesov.maksim.mapping.map.service.impl.request_processing;

import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.mapper.LayerMapper;
import kolesov.maksim.mapping.map.model.elastic.LayerSearch;
import kolesov.maksim.mapping.map.repository.LayerRepository;
import kolesov.maksim.mapping.map.service.request_processing.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private static final String LAYER_INDEX = "layers";

    private final ElasticsearchOperations elasticsearchOperations;
    private final LayerRepository layerRepository;
    private final LayerMapper layerMapper;

    @Override
    public List<LayerDto> searchByQuery(String stringQuery) {
        Query query = new StringQuery(stringQuery);
        SearchHits<LayerSearch> hits = elasticsearchOperations.search(query, LayerSearch.class, IndexCoordinates.of(LAYER_INDEX));

        List<UUID> ids = hits.stream().map(SearchHit::getContent).map(LayerSearch::getId).map(UUID::fromString).toList();
        return layerMapper.toDto(layerRepository.findAllByIds(ids));
    }

}
