package kolesov.maksim.mapping.map.mapper;

import kolesov.maksim.mapping.map.dto.UserDto;
import kolesov.maksim.mapping.map.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {UserRoleMapper.class}
)
public interface UserMapper extends AbstractMapper<UserEntity, UserDto> {

    @Override
    @Mapping(target = "password", ignore = true)
    UserDto toDto(UserEntity entity);
}
