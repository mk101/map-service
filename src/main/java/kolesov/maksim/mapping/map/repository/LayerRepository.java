package kolesov.maksim.mapping.map.repository;

import kolesov.maksim.mapping.map.model.LayerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LayerRepository extends CrudRepository<LayerEntity, UUID> {



}
