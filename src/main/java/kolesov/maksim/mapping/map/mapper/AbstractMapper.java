package kolesov.maksim.mapping.map.mapper;

public interface AbstractMapper<E, D> {

    public abstract E toEntity(D dto);

    public abstract D toDto(E entity);

}
