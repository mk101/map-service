package kolesov.maksim.mapping.map.service.impl;

import kolesov.maksim.mapping.map.model.UserEntity;
import kolesov.maksim.mapping.map.repository.UserRepository;
import kolesov.maksim.mapping.map.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public Optional<UserEntity> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
