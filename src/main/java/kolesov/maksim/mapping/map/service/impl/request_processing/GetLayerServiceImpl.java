package kolesov.maksim.mapping.map.service.impl.request_processing;

import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.mapper.LayerMapper;
import kolesov.maksim.mapping.map.model.LayerEntity;
import kolesov.maksim.mapping.map.repository.LayerRepository;
import kolesov.maksim.mapping.map.repository.LayerTagRepository;
import kolesov.maksim.mapping.map.service.request_processing.AbstractLayerService;
import kolesov.maksim.mapping.map.service.request_processing.GetLayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetLayerServiceImpl extends AbstractLayerService implements GetLayerService {

    private final LayerRepository layerRepository;
    private final LayerTagRepository layerTagRepository;
    private final LayerMapper mapper;

    @Override
    public List<LayerDto> byUser(UUID id) {
        List<LayerEntity> layers = layerRepository.findAllByUser(id);

        layers.forEach(l -> l.setTags(layerTagRepository.findAllByLayer(l.getId())));

        return mapper.toDto(layers);
    }

}
