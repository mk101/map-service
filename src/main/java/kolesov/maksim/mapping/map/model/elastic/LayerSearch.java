package kolesov.maksim.mapping.map.model.elastic;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@Data
@Document(indexName = "layers")
public class LayerSearch {

    @Id
    private String id;

    @Field(name = "horizontal_area_lower")
    private Double horizontalAreaLower;

    @Field(name = "horizontal_area_upper")
    private Double horizontalAreaUpper;

    @Field(name = "vertical_area_lower")
    private Double verticalAreaLower;

    @Field(name = "vertical_area_upper")
    private Double verticalAreaUpper;

    @Field(name = "name")
    private String name;

    @Field(name = "description")
    private String description;

    @Field(name = "tag_value")
    private List<String> tags;

}
