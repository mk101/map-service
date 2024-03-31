package kolesov.maksim.mapping.map.filter;

import jakarta.servlet.http.HttpServletRequest;
import kolesov.maksim.mapping.map.model.UserEntity;
import kolesov.maksim.mapping.map.service.JwtService;
import kolesov.maksim.mapping.map.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        String token = header.split(" ")[1].trim();
        UUID id;
        try {
            id = UUID.fromString(jwtService.getSub(token));
        } catch (NullPointerException npe) {
            log.error("Failed to load sub from token");
            return null;
        }

        try {
            if (!jwtService.isAccess(token)) {
                return null;
            }
        } catch (Exception e) {
            log.error("Failed to load type from token");
            return null;
        }

        Optional<UserEntity> user = userService.findById(id);
        return user.orElse(null);

    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "";
    }

}
