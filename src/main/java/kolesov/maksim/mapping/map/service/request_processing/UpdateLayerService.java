package kolesov.maksim.mapping.map.service.request_processing;

import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.UserDto;

public interface UpdateLayerService {

    LayerDto update(LayerDto layerDto, UserDto user);

}
