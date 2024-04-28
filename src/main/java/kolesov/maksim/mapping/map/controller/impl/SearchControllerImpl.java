package kolesov.maksim.mapping.map.controller.impl;

import kolesov.maksim.mapping.map.controller.SearchController;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.ResponseDto;
import kolesov.maksim.mapping.map.service.request_processing.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SearchControllerImpl implements SearchController {

    private final SearchService service;

    @Override
    public ResponseDto<List<LayerDto>> searchByQuery(String query) {
        return ResponseDto.<List<LayerDto>>builder()
                .success(true)
                .data(service.searchByQuery(query))
        .build();
    }

}
