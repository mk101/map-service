package kolesov.maksim.mapping.map.controller;

import kolesov.maksim.mapping.map.dto.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@RequestMapping("/search")
public interface SearchController {

    @GetMapping
    ResponseDto<List<UUID>> searchByQuery(@RequestParam String query);

}
