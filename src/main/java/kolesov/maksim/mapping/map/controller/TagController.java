package kolesov.maksim.mapping.map.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import kolesov.maksim.mapping.map.annotation.HasRole;
import kolesov.maksim.mapping.map.dto.ResponseDto;
import kolesov.maksim.mapping.map.model.Role;
import kolesov.maksim.mapping.map.model.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequestMapping("/tags")
public interface TagController {

    @PostMapping
    @HasRole({Role.EDIT_ANY_MAP, Role.EDIT_OWN_MAP})
    @Tags(value = {@Tag(name = "MapService"), @Tag(name = "Tag")})
    ResponseDto<Void> add(@RequestParam UUID layerId, @RequestParam String tag, @AuthenticationPrincipal UserEntity user);

    @DeleteMapping
    @HasRole({Role.EDIT_ANY_MAP, Role.EDIT_OWN_MAP})
    @Tags(value = {@Tag(name = "MapService"), @Tag(name = "Tag")})
    ResponseDto<Void> delete(@RequestParam UUID layerId, @RequestParam String tag, @AuthenticationPrincipal UserEntity user);

}
