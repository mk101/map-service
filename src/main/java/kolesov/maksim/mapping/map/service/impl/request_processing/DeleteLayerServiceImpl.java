package kolesov.maksim.mapping.map.service.impl.request_processing;

import kolesov.maksim.mapping.map.dto.UserDto;
import kolesov.maksim.mapping.map.dto.UserRoleDto;
import kolesov.maksim.mapping.map.exception.ForbiddenException;
import kolesov.maksim.mapping.map.exception.NotFoundException;
import kolesov.maksim.mapping.map.model.LayerEntity;
import kolesov.maksim.mapping.map.model.Role;
import kolesov.maksim.mapping.map.repository.LayerRepository;
import kolesov.maksim.mapping.map.repository.LayerTagRepository;
import kolesov.maksim.mapping.map.service.ElasticsearchService;
import kolesov.maksim.mapping.map.service.request_processing.DeleteLayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteLayerServiceImpl implements DeleteLayerService {

    private final LayerRepository layerRepository;
    private final LayerTagRepository layerTagRepository;
    private final ElasticsearchService elasticsearchService;

    @Override
    @Transactional
    public void delete(UUID id, UserDto userDto) {
        LayerEntity entity = layerRepository.findById(id).orElseThrow(() -> new NotFoundException("Layer not found"));

        if (!userDto.getId().equals(entity.getCreatedBy())
                && userDto.getRoles().stream().map(UserRoleDto::getRole).noneMatch(r -> r == Role.DELETE_ANY_MAP)) {
            throw new ForbiddenException("Access deny");
        }

        layerTagRepository.deleteAllByLayer(entity.getId());
        layerRepository.delete(entity);

        log.info("Deleted {}", entity);
        elasticsearchService.deleteLayer(id);
    }

}
