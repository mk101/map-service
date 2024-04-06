package kolesov.maksim.mapping.map.repository;

import kolesov.maksim.mapping.map.model.LayerTagEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LayerTagRepository extends CrudRepository<LayerTagEntity, UUID> {

    @Query(value = "DELETE FROM layer_tag WHERE layer_id = :layer", nativeQuery = true)
    @Modifying(flushAutomatically = true)
    void deleteAllByLayer(UUID layer);

}
