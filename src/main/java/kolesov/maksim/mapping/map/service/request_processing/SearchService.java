package kolesov.maksim.mapping.map.service.request_processing;

import kolesov.maksim.mapping.map.dto.LayerDto;

import java.util.List;

public interface SearchService {

    List<LayerDto> searchByQuery(String query);

}
