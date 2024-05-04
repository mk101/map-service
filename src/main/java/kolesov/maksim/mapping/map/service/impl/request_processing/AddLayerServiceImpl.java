package kolesov.maksim.mapping.map.service.impl.request_processing;

import io.hypersistence.utils.hibernate.type.range.Range;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.UserDto;
import kolesov.maksim.mapping.map.mapper.LayerMapper;
import kolesov.maksim.mapping.map.model.LayerEntity;
import kolesov.maksim.mapping.map.repository.LayerRepository;
import kolesov.maksim.mapping.map.repository.LayerTagRepository;
import kolesov.maksim.mapping.map.service.ElasticsearchService;
import kolesov.maksim.mapping.map.service.request_processing.AbstractLayerService;
import kolesov.maksim.mapping.map.service.request_processing.AddLayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddLayerServiceImpl extends AbstractLayerService implements AddLayerService {

    private final LayerRepository layerRepository;
    private final LayerTagRepository layerTagRepository;
    private final ElasticsearchService elasticsearchService;

    private final LayerMapper mapper;

    @Override
    public LayerDto add(LayerDto layerDto, UserDto user) {
        UUID userId = user.getId();
        List<Range<BigDecimal>> ranges = evaluateRanges(layerDto.getData());

        LayerEntity entity = mapper.toEntity(layerDto);
        entity.setHorizontalArea(ranges.get(0));
        entity.setVerticalArea(ranges.get(1));

        OffsetDateTime time = OffsetDateTime.now(Clock.systemUTC());
        entity.setCreatedBy(userId);
        entity.setCreatedAt(mapper.offsetToTimestamp(time));

        UUID id = UUID.randomUUID();
        entity.setId(id);
        entity.getTags().forEach(t -> {
            t.setId(UUID.randomUUID());
            t.setLayerId(id);
        });

        layerRepository.save(entity);
        layerTagRepository.saveAll(entity.getTags());

        elasticsearchService.addLayer(entity);

        return mapper.toDto(entity);
    }

}
