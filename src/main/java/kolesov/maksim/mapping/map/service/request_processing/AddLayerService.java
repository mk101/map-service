package kolesov.maksim.mapping.map.service.request_processing;

import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.UserDto;

public interface AddLayerService {

    LayerDto add(LayerDto layerDto, UserDto user);

}
