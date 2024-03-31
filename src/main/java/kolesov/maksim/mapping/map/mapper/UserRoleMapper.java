package kolesov.maksim.mapping.map.mapper;

import kolesov.maksim.mapping.map.dto.UserRoleDto;
import kolesov.maksim.mapping.map.model.UserRoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper extends AbstractMapper<UserRoleEntity, UserRoleDto> {
}
