package kolesov.maksim.mapping.map.service.impl.request_processing;

import io.hypersistence.utils.hibernate.type.range.Range;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.UserDto;
import kolesov.maksim.mapping.map.exception.NotFoundException;
import kolesov.maksim.mapping.map.mapper.LayerMapper;
import kolesov.maksim.mapping.map.model.LayerEntity;
import kolesov.maksim.mapping.map.repository.LayerRepository;
import kolesov.maksim.mapping.map.service.request_processing.AbstractLayerService;
import kolesov.maksim.mapping.map.service.request_processing.UpdateLayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateLayerServiceImpl extends AbstractLayerService implements UpdateLayerService {

    private final LayerRepository layerRepository;
    private final LayerMapper mapper;

    @Override
    public LayerDto update(LayerDto layerDto, UserDto user) {
        UUID layerId = layerDto.getId();
        if (layerId == null) {
            throw new IllegalArgumentException("Layer id is null");
        }

        LayerEntity layerEntity = layerRepository.findById(layerId)
                .orElseThrow(() -> new NotFoundException("Layer not found"));

        layerEntity.setName(layerDto.getName());
        layerEntity.setDescription(layerDto.getDescription());
        layerEntity.setData(layerDto.getData());

        List<Range<BigDecimal>> ranges = evaluateRanges(layerDto.getData());

        layerEntity.setHorizontalArea(ranges.get(0));
        layerEntity.setVerticalArea(ranges.get(1));

        layerEntity.setEditAt(mapper.offsetToTimestamp(OffsetDateTime.now(Clock.systemUTC())));
        layerEntity.setEditBy(user.getId());

        layerEntity = layerRepository.save(layerEntity);

        return mapper.toDto(layerEntity);
    }

}
