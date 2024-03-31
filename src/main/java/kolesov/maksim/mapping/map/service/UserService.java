package kolesov.maksim.mapping.map.service;

import kolesov.maksim.mapping.map.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    Optional<UserEntity> findById(UUID id);

}
