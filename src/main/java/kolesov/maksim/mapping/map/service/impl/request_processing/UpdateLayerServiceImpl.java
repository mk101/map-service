package kolesov.maksim.mapping.map.service.impl.request_processing;

import io.hypersistence.utils.hibernate.type.range.Range;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.UserDto;
import kolesov.maksim.mapping.map.dto.UserRoleDto;
import kolesov.maksim.mapping.map.exception.ForbiddenException;
import kolesov.maksim.mapping.map.exception.NotFoundException;
import kolesov.maksim.mapping.map.mapper.LayerMapper;
import kolesov.maksim.mapping.map.model.LayerEntity;
import kolesov.maksim.mapping.map.model.Role;
import kolesov.maksim.mapping.map.repository.LayerRepository;
import kolesov.maksim.mapping.map.repository.LayerTagRepository;
import kolesov.maksim.mapping.map.service.request_processing.AbstractLayerService;
import kolesov.maksim.mapping.map.service.request_processing.UpdateLayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final LayerTagRepository layerTagRepository;
    private final LayerMapper mapper;

    @Override
    @Transactional
    public LayerDto update(LayerDto layerDto, UserDto user) {
        UUID layerId = layerDto.getId();
        if (layerId == null) {
            throw new IllegalArgumentException("Layer id is null");
        }

        LayerEntity layerEntity = layerRepository.findById(layerId)
                .orElseThrow(() -> new NotFoundException("Layer not found"));

        if (!user.getId().equals(layerEntity.getCreatedBy())
                && user.getRoles().stream().map(UserRoleDto::getRole).noneMatch(r -> r == Role.EDIT_ANY_MAP)) {
            throw new ForbiddenException("Access deny");
        }

        layerEntity.setName(layerDto.getName());
        layerEntity.setDescription(layerDto.getDescription());
        layerEntity.setData(layerDto.getData());
        layerEntity.setStatus(layerDto.getStatus());

        List<Range<BigDecimal>> ranges = evaluateRanges(layerDto.getData());

        layerEntity.setHorizontalArea(ranges.get(0));
        layerEntity.setVerticalArea(ranges.get(1));

        layerEntity.setEditAt(mapper.offsetToTimestamp(OffsetDateTime.now(Clock.systemUTC())));
        layerEntity.setEditBy(user.getId());

        layerEntity = layerRepository.save(layerEntity);

        layerTagRepository.deleteAllByLayer(layerEntity.getId());
        layerTagRepository.saveAll(layerDto.getTags().stream().map(mapper::tagValue).map(v -> {
            v.setLayerId(layerId);
            v.setId(UUID.randomUUID());
            return v;
        }).toList());

        return mapper.toDto(layerEntity);
    }

}
