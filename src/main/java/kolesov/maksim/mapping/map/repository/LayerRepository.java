package kolesov.maksim.mapping.map.repository;

import kolesov.maksim.mapping.map.model.LayerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LayerRepository extends CrudRepository<LayerEntity, UUID> {

    @Query(value = "SELECT * FROM layer WHERE created_by = :userId", nativeQuery = true)
    List<LayerEntity> findAllByUser(@Param("userId") UUID userId);

}
