package kolesov.maksim.mapping.map.service.impl.request_processing;

import kolesov.maksim.mapping.map.dto.UserDto;
import kolesov.maksim.mapping.map.exception.ForbiddenException;
import kolesov.maksim.mapping.map.exception.NotFoundException;
import kolesov.maksim.mapping.map.mapper.LayerMapper;
import kolesov.maksim.mapping.map.model.LayerEntity;
import kolesov.maksim.mapping.map.model.LayerTagEntity;
import kolesov.maksim.mapping.map.model.Role;
import kolesov.maksim.mapping.map.repository.LayerRepository;
import kolesov.maksim.mapping.map.repository.LayerTagRepository;
import kolesov.maksim.mapping.map.service.request_processing.AddTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddTagServiceImpl implements AddTagService {

    private final LayerRepository layerRepository;
    private final LayerTagRepository layerTagRepository;
    private final LayerMapper mapper;

    @Override
    @Transactional
    public void addTag(UUID layerId, String tag, UserDto user) {
        LayerEntity layer = layerRepository.findById(layerId).orElseThrow(() -> new NotFoundException("Layer not found"));

        if (!user.getId().equals(layer.getCreatedBy()) && user.getRoles().stream().noneMatch(r -> r.getRole().equals(Role.EDIT_ANY_MAP))) {
            throw new ForbiddenException("Access deny");
        }

        List<LayerTagEntity> tags = layerTagRepository.findAllByLayer(layerId);

        if (tags.stream().anyMatch(t -> t.getValue().equals(tag))) {
            return;
        }

        LayerTagEntity tagEntity = LayerTagEntity.builder()
                .id(UUID.randomUUID())
                .layerId(layerId)
                .value(tag)
        .build();

        layerTagRepository.save(tagEntity);

        layer.setEditBy(user.getId());
        layer.setEditAt(mapper.offsetToTimestamp(OffsetDateTime.now(Clock.systemUTC())));

        layerRepository.save(layer);
    }

}
