package kolesov.maksim.mapping.map.controller.impl;

import kolesov.maksim.mapping.map.controller.LayerController;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.ResponseDto;
import kolesov.maksim.mapping.map.mapper.UserMapper;
import kolesov.maksim.mapping.map.model.UserEntity;
import kolesov.maksim.mapping.map.service.request_processing.AddLayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LayerControllerImpl implements LayerController {

    private final AddLayerService addLayerService;
    private final UserMapper userMapper;

    @Override
    public ResponseDto<LayerDto> create(LayerDto dto, UserEntity user) {
        return ResponseDto.<LayerDto>builder()
                .success(true)
                .data(addLayerService.add(dto, userMapper.toDto(user)))
        .build();
    }

}
