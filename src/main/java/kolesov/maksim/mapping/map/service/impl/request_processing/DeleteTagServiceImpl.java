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
import kolesov.maksim.mapping.map.service.request_processing.AbstractDeleteService;
import kolesov.maksim.mapping.map.service.request_processing.DeleteTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class DeleteTagServiceImpl extends AbstractDeleteService implements DeleteTagService {

    private final LayerRepository layerRepository;
    private final LayerTagRepository layerTagRepository;
    private final LayerMapper mapper;

    @Autowired
    public DeleteTagServiceImpl(
            ElasticsearchOperations elasticsearchTemplate,
            LayerRepository layerRepository,
            LayerTagRepository layerTagRepository,
            LayerMapper mapper
    ) {
        super(elasticsearchTemplate, log);

        this.layerRepository = layerRepository;
        this.layerTagRepository = layerTagRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void delete(UUID layerId, String tag, UserDto user) {
        LayerEntity layer = layerRepository.findById(layerId).orElseThrow(() -> new NotFoundException("Layer not found"));

        if (!user.getId().equals(layer.getCreatedBy()) && user.getRoles().stream().noneMatch(r -> r.getRole().equals(Role.EDIT_ANY_MAP))) {
            throw new ForbiddenException("Access deny");
        }

        List<LayerTagEntity> tags = layerTagRepository.findAllByLayer(layerId);

        Optional<LayerTagEntity> optional = tags.stream().filter(t -> t.getValue().equals(tag)).findAny();
        if (optional.isEmpty()) {
            return;
        }

        layerTagRepository.delete(optional.get());

        layer.setEditBy(user.getId());
        layer.setEditAt(mapper.offsetToTimestamp(OffsetDateTime.now(Clock.systemUTC())));
        layerRepository.save(layer);

        invalidateElastic("layers");
    }

}
