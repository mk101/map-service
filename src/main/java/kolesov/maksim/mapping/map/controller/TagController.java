package kolesov.maksim.mapping.map.controller;

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
    ResponseDto<Void> add(@RequestParam UUID layerId, @RequestParam String tag, @AuthenticationPrincipal UserEntity user);

    @DeleteMapping
    @HasRole({Role.EDIT_ANY_MAP, Role.EDIT_OWN_MAP})
    ResponseDto<Void> delete(@RequestParam UUID layerId, @RequestParam String tag, @AuthenticationPrincipal UserEntity user);

}
