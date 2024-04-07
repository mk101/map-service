package kolesov.maksim.mapping.map.mapper;

import java.util.List;

public interface AbstractMapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

    List<D> toDto(List<E> entities);

}
