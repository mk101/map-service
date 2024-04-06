package kolesov.maksim.mapping.map.service.request_processing;

import kolesov.maksim.mapping.map.dto.UserDto;

import java.util.UUID;

public interface DeleteLayerService {

    void delete(UUID id, UserDto userDto);

}
