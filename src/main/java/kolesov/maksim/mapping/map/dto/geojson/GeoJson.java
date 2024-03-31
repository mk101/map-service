package kolesov.maksim.mapping.map.dto.geojson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class GeoJson implements Serializable {

    private Type type;

    @JsonProperty("features")
    private List<Element> elements;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Element implements Serializable {

        @JsonProperty(required = true)
        private Type type;
        private Element geometry;
        private Map<String, Object> properties;

        // Double or List<BigDecimal>
        private List<Object> coordinates;

    }

}
