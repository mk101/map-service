package kolesov.maksim.mapping.map.filter;

import jakarta.servlet.http.HttpServletRequest;
import kolesov.maksim.mapping.map.annotation.HasRole;
import kolesov.maksim.mapping.map.model.Role;
import kolesov.maksim.mapping.map.model.UserEntity;
import kolesov.maksim.mapping.map.model.UserRoleEntity;
import kolesov.maksim.mapping.map.service.JwtService;
import kolesov.maksim.mapping.map.service.UserService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends AbstractPreAuthenticatedProcessingFilter {

    private Map<MethodDescriptor, Set<Role>> rolesByPath;

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
        if (user.isEmpty()) {
            log.error("Cannot find user with id {}", id);
            return null;
        }

        List<Role> roles = user.get().getRoles().stream().map(UserRoleEntity::getRole).toList();
        Set<Role> correctRoles = rolesByPath.get(new MethodDescriptor(request.getMethod(), request.getRequestURI()));
        if (correctRoles != null) {
            for (Role role : correctRoles) {
                if (roles.contains(role)) {
                    return user.get();
                }
            }
        }

        return null;

    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "";
    }

    public void initPaths(Collection<Class<?>> controllers) {
        rolesByPath = new HashMap<>();
        for (Class<?> controller : controllers) {
            String prefix = Optional.ofNullable(controller.getAnnotation(RequestMapping.class)).map(a -> a.value()[0]).orElse("");
            Method[] methods = controller.getMethods();

            for (Method method : methods) {
                HasRole hasRole = method.getAnnotation(HasRole.class);
                if (hasRole == null) {
                    continue;
                }

                for (Annotation annotation : method.getAnnotations()) {
                    Optional<MethodDescriptor> methodDescriptor = proceedMethodAnnotation(annotation, prefix);
                    if (methodDescriptor.isEmpty()) {
                        continue;
                    }

                    rolesByPath.put(methodDescriptor.get(), new HashSet<>(Arrays.asList(hasRole.value())));
                    break;
                }
            }
        }

        log.info("Register method secure: {}", rolesByPath);
    }

    private Optional<MethodDescriptor> proceedMethodAnnotation(Annotation annotation, String prefix) {
        if (annotation instanceof PostMapping postMapping) {
            if (postMapping.value().length > 0) {
                return Optional.of(new MethodDescriptor("POST", prefix + postMapping.value()[0]));
            }
            return Optional.of(new MethodDescriptor("POST", prefix));
        }

        if (annotation instanceof GetMapping getMapping) {
            if (getMapping.value().length > 0) {
                return Optional.of(new MethodDescriptor("GET", prefix + getMapping.value()[0]));
            }
            return Optional.of(new MethodDescriptor("GET", prefix));
        }

        if (annotation instanceof PutMapping putMapping) {
            if (putMapping.value().length > 0) {
                return Optional.of(new MethodDescriptor("PUT", prefix + putMapping.value()[0]));
            }
            return Optional.of(new MethodDescriptor("PUT", prefix));
        }

        if (annotation instanceof DeleteMapping deleteMapping) {
            if (deleteMapping.value().length > 0) {
                return Optional.of(new MethodDescriptor("DELETE", prefix + deleteMapping.value()[0]));
            }
            return Optional.of(new MethodDescriptor("DELETE", prefix));
        }

        return Optional.empty();
    }

    @Getter
    @EqualsAndHashCode
    @AllArgsConstructor
    @Builder
    private static class MethodDescriptor {

        private String method;
        private String path;

        @Override
        public String toString() {
            return method + " " + path;
        }
    }

}
