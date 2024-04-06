package kolesov.maksim.mapping.map.controller;

import jakarta.validation.Valid;
import kolesov.maksim.mapping.map.annotation.HasRole;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.ResponseDto;
import kolesov.maksim.mapping.map.model.Role;
import kolesov.maksim.mapping.map.model.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/layer")
public interface LayerController {

    @PostMapping
    @HasRole(Role.CREATE_MAP)
    ResponseDto<LayerDto> create(@RequestBody @Valid LayerDto dto, @AuthenticationPrincipal UserEntity user);

    @PutMapping
    @HasRole(value = {Role.EDIT_OWN_MAP, Role.EDIT_ANY_MAP})
    ResponseDto<LayerDto> update(@RequestBody @Valid LayerDto dto, @AuthenticationPrincipal UserEntity user);

    @DeleteMapping
    @HasRole(value = {Role.DELETE_ANY_MAP, Role.DELETE_OWN_MAP})
    ResponseDto<Void> delete(@RequestParam UUID id, @AuthenticationPrincipal UserEntity user);

}
