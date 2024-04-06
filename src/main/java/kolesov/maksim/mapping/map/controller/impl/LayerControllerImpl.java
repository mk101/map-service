package kolesov.maksim.mapping.map.controller.impl;

import kolesov.maksim.mapping.map.controller.LayerController;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.ResponseDto;
import kolesov.maksim.mapping.map.mapper.UserMapper;
import kolesov.maksim.mapping.map.model.UserEntity;
import kolesov.maksim.mapping.map.service.request_processing.AddLayerService;
import kolesov.maksim.mapping.map.service.request_processing.UpdateLayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LayerControllerImpl implements LayerController {

    private final AddLayerService addLayerService;
    private final UpdateLayerService updateLayerService;
    private final UserMapper userMapper;

    @Override
    public ResponseDto<LayerDto> create(LayerDto dto, UserEntity user) {
        return ResponseDto.<LayerDto>builder()
                .success(true)
                .data(addLayerService.add(dto, userMapper.toDto(user)))
        .build();
    }

    @Override
    public ResponseDto<LayerDto> update(LayerDto dto, UserEntity user) {
        return ResponseDto.<LayerDto>builder()
                .success(true)
                .data(updateLayerService.update(dto, userMapper.toDto(user)))
        .build();
    }

}
