package kolesov.maksim.mapping.map.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/search")
public interface SearchController {

    @GetMapping
    @Tags(value = {@Tag(name = "MapService"), @Tag(name = "Search")})
    ResponseDto<List<LayerDto>> searchByQuery(@RequestParam String query);

}
