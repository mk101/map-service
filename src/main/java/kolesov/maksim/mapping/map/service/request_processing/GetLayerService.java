package kolesov.maksim.mapping.map.service.request_processing;

import kolesov.maksim.mapping.map.dto.LayerDto;

import java.util.List;
import java.util.UUID;

public interface GetLayerService {

    List<LayerDto> byUser(UUID id);

}
