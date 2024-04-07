package kolesov.maksim.mapping.map.service.request_processing;

import java.util.List;
import java.util.UUID;

public interface SearchService {

    List<UUID> searchByQuery(String query);

}
