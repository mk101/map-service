package kolesov.maksim.mapping.map.controller;

import jakarta.validation.Valid;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.ResponseDto;
import kolesov.maksim.mapping.map.model.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/layer")
public interface LayerController {

    @PostMapping
    ResponseDto<LayerDto> create(@RequestBody @Valid LayerDto dto, @AuthenticationPrincipal UserEntity user);

}
