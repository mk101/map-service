package kolesov.maksim.mapping.map.controller.impl;

import kolesov.maksim.mapping.map.controller.TagController;
import kolesov.maksim.mapping.map.dto.ResponseDto;
import kolesov.maksim.mapping.map.mapper.UserMapper;
import kolesov.maksim.mapping.map.model.UserEntity;
import kolesov.maksim.mapping.map.service.request_processing.AddTagService;
import kolesov.maksim.mapping.map.service.request_processing.DeleteTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TagControllerImpl implements TagController {

    private final AddTagService addTagService;
    private final DeleteTagService deleteTagService;
    private final UserMapper userMapper;

    @Override
    public ResponseDto<Void> add(UUID layerId, String tag, UserEntity user) {
        addTagService.addTag(layerId, tag, userMapper.toDto(user));
        return ResponseDto.<Void>builder()
                .success(true)
        .build();
    }

    @Override
    public ResponseDto<Void> delete(UUID layerId, String tag, UserEntity user) {
        deleteTagService.delete(layerId, tag, userMapper.toDto(user));
        return ResponseDto.<Void>builder()
                .success(true)
        .build();
    }

}
