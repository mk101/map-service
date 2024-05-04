package kolesov.maksim.mapping.map.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import kolesov.maksim.mapping.map.annotation.HasRole;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.ResponseDto;
import kolesov.maksim.mapping.map.model.Role;
import kolesov.maksim.mapping.map.model.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/layers")
public interface LayerController {

    @PostMapping
    @HasRole(Role.CREATE_MAP)
    @Tags(value = {@Tag(name = "MapService"), @Tag(name = "Layer")})
    ResponseDto<LayerDto> create(@RequestBody @Valid LayerDto dto, @AuthenticationPrincipal UserEntity user);

    @PutMapping
    @HasRole(value = {Role.EDIT_OWN_MAP, Role.EDIT_ANY_MAP})
    @Tags(value = {@Tag(name = "MapService"), @Tag(name = "Layer")})
    ResponseDto<LayerDto> update(@RequestBody @Valid LayerDto dto, @AuthenticationPrincipal UserEntity user);

    @DeleteMapping
    @HasRole(value = {Role.DELETE_ANY_MAP, Role.DELETE_OWN_MAP})
    @Tags(value = {@Tag(name = "MapService"), @Tag(name = "Layer")})
    ResponseDto<Void> delete(@RequestParam UUID id, @AuthenticationPrincipal UserEntity user);

    @GetMapping("/user")
    @Tags(value = {@Tag(name = "MapService"), @Tag(name = "Layer")})
    ResponseDto<List<LayerDto>> getByUser(@RequestParam UUID id);

    @GetMapping
    @Tags(value = {@Tag(name = "MapService"), @Tag(name = "Layer")})
    ResponseDto<LayerDto> getById(@RequestParam UUID id);

}
