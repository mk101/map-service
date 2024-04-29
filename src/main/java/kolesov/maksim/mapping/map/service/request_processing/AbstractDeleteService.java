package kolesov.maksim.mapping.map.service.request_processing;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

@RequiredArgsConstructor
public abstract class AbstractDeleteService {

    private final ElasticsearchOperations elasticsearchTemplate;
    private final Logger log;

    protected void invalidateElastic(String index) {
        elasticsearchTemplate.indexOps(IndexCoordinates.of(index)).delete();
        log.info("Invalidate {} index", index);
    }

}
