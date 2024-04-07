package kolesov.maksim.mapping.map.service.request_processing;

import kolesov.maksim.mapping.map.dto.UserDto;

import java.util.UUID;

public interface DeleteTagService {

    void delete(UUID layerId, String tag, UserDto user);

}
