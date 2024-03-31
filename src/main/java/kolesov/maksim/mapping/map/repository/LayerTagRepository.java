package kolesov.maksim.mapping.map.repository;

import kolesov.maksim.mapping.map.model.LayerEntity;
import kolesov.maksim.mapping.map.model.LayerTagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LayerTagRepository extends CrudRepository<LayerTagEntity, UUID> {

    @Query(value = "SELECT * FROM layer_tag WHERE layer_id = :layer", nativeQuery = true)
    List<LayerEntity> findAllByLayer(UUID layer);

}
